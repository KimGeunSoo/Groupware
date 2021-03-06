package kh.gw.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.gw.dto.TnADTO;
import kh.gw.dto.TnA_objectionDTO;
import kh.gw.dto.TnA_statusDTO;
import kh.gw.service.TnAService;

@Controller
@RequestMapping("/tna")
public class TnAController {

	@Autowired
	private TnAService tservice;

	@Autowired
	private HttpSession session;


	@RequestMapping("tnaCheckPage.tna")
	// 출퇴근체크 페이지 접속
	public String tnaCheckPage(HttpServletRequest request, Model model) {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");

		// 출근시간 조회
		Map<String, Object> attendanceValue = tservice.getAttendanceTime(sessionId);
		// 퇴근시간 조회
		Map<String, Object> leaveWorkValue = tservice.getLeaveWorkTime(sessionId);
		
		
		// 출퇴근시간 리스트 조회
		List<Map<String, Object>> tnaCalendarList = tservice.getTnaCalendarList(sessionId);
		//		System.out.println(tnaCalendarList.get(0).get("TNA_SEQ"));
		//		System.out.println(tnaCalendarList.get(0).get("TNA_ID"));
		//		System.out.println(tnaCalendarList.get(0).get("TNA_START_TIME"));
		//		System.out.println(tnaCalendarList.get(0).get("TNA_END_TIME"));
		//		System.out.println(tnaCalendarList.get(0).get("TNA_STATUS_CODE"));
		//		System.out.println(tnaCalendarList.get(0).get("TNA_STATUS_NAME"));
		
		

		model.addAttribute("attendanceValue", attendanceValue);
		model.addAttribute("leaveWorkValue", leaveWorkValue);
		model.addAttribute("tnaCalendarList", tnaCalendarList);

		return "/tna/user/tnaCheck";
	}

	@RequestMapping("attendanceInput.tna")
	// 출근버튼 눌렀을 경우
	public String attendanceInput(HttpServletRequest request) throws ParseException {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");
		// 이전 페이지 주소값
		String referer = request.getHeader("REFERER");

		int result = tservice.attendanceInput(sessionId);
		return "redirect:" + referer;
	}

	@RequestMapping("leaveWorkInput.tna")
	// 퇴근버튼 눌렀을 경우
	public String leaveWorkInput(HttpServletRequest request) throws ParseException {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");
		// 이전 페이지 주소값
		String referer = request.getHeader("REFERER");

		int result = tservice.leaveWorkInput(sessionId);
		return "redirect:" + referer;
	}

	@RequestMapping("tnaMyHistoryPage.tna")
	// 근태조정신청 페이지 접속
	public String tnaMyHistoryPage(HttpServletRequest request, Model model) {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");
		String selectYearValue = request.getParameter("selectYearValue");

		// 년도값 판별
		selectYearValue = tservice.yearVerify(selectYearValue);

		// 근태상태 리스트 값 조회 (지각,조퇴...)
		List<TnA_statusDTO> tnaStatusList = tservice.getTnaStatusList();
		// 나의 근태내역 출근 월별 카운트
		List<Map<String, Object>> tnaStartCountList = tservice.getTnaStartCountList(sessionId,selectYearValue);
		// 나의 근태내역 출근 합계 카운트
		List<Map<String, Object>> tnaStartCountSumList = tservice.getTnaStartCountSumList(sessionId,selectYearValue);
		// 나의 근태내역 퇴근 월별 카운트
		List<Map<String, Object>> tnaEndCountList = tservice.getTnaEndCountList(sessionId,selectYearValue);
		// 나의 근태내역 퇴근 합계 카운트
		List<Map<String, Object>> tnaEndCountSumList = tservice.getTnaEndCountSumList(sessionId,selectYearValue);
		// 출퇴근 select 리스트 조회
		List<Map<String, Object>> tnaSelectYearList = tservice.getTnaSelectYearList(sessionId);


		model.addAttribute("tnaStatusList", tnaStatusList);
		model.addAttribute("tnaStartCountList", tnaStartCountList);
		model.addAttribute("tnaStartCountSumList", tnaStartCountSumList);
		model.addAttribute("tnaEndCountList", tnaEndCountList);
		model.addAttribute("tnaEndCountSumList", tnaEndCountSumList);
		model.addAttribute("tnaSelectYearList", tnaSelectYearList);

		model.addAttribute("selectYearValue", selectYearValue);

		return "/tna/user/tnaMyHistory";
	}

	@RequestMapping("tnaFixRequestPage.tna")
	// 근태조정신청 페이지 접속
	public String tnaFixRequestPage(HttpServletRequest request, Model model) {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");
		int tna_seq = Integer.parseInt(request.getParameter("tna_seq"));
		String tna_status = request.getParameter("tna_status");

		// 이미 해당 값에 대해 근태조정신청 중복여부체크
		Map<String, Object> dto = tservice.tnaCheckOverlap(tna_seq,tna_status);


		// 변경할 출퇴근시간 값 조회 (이미 신청된 값이 있는지)
		Map<String, Object> tnaCalendarValue = tservice.getTnaCalendarValue(sessionId,tna_seq);
		// 근태상태 리스트 값 조회  (지각,조퇴...)
		List<TnA_statusDTO> tnaStatusList = tservice.getTnaStatusList();

		model.addAttribute("tnaStatusList", tnaStatusList);
		model.addAttribute("tnaCalendarValue", tnaCalendarValue);
		model.addAttribute("tna_status", tna_status);
		
		// 신청된 값이 이미 있다면 dto가 비어있지 않음
		if (dto != null) {
			model.addAttribute("dto", dto);
			int TNA_OBJ_PROC_STATUS_CODE = ((BigDecimal)dto.get("TNA_OBJ_PROC_STATUS_CODE")).intValue();
				// 승인된 상태라면 변경 불가
				if(TNA_OBJ_PROC_STATUS_CODE == 3) {
					return "/tna/user/tnaFixOverlap";
				// 조정중, 반려라면 수정 가능
				}else {
					return "/tna/user/tnaReFix";
				}
				
		}
		// 신청이 처음인경우
		return "/tna/user/tnaFixRequest";
	}

	@RequestMapping("tnaFixRequestSubmit.tna")
	// 근태조정신청 제출 
	public String tnaFixRequestSubmit(HttpServletRequest request, Model model) throws ParseException {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");

		int tna_obj_changed_code = Integer.parseInt(request.getParameter("tna_obj_changed_code"));
		String tna_obj_reason = request.getParameter("tna_obj_reason");
		int tna_seq = Integer.parseInt(request.getParameter("tna_seq"));
		String tna_obj_status = request.getParameter("tna_obj_status");
		int tna_obj_ori_status_code = Integer.parseInt(request.getParameter("tna_obj_ori_status_code"));
		String tna_obj_time_string = request.getParameter("tna_obj_time");

		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date utilDate = fm.parse(tna_obj_time_string);

		TnA_objectionDTO dto = new TnA_objectionDTO();
		dto.setTna_obj_changed_code(tna_obj_changed_code);
		dto.setTna_obj_reason(tna_obj_reason);
		dto.setTna_seq(tna_seq);
		dto.setTna_obj_status(tna_obj_status);
		dto.setTna_obj_ori_status_code(tna_obj_ori_status_code);
		dto.setTna_obj_time(utilDate);
		//		dto.setTna_obj_time(tna_obj_time);


		//		System.out.println(dto.getTna_obj_ori_status_code());
		//		System.out.println(dto.getTna_obj_time());

		// 정정신청값 DB에 제출
		int result = tservice.tnaFixRequestInput(dto,sessionId);

		return "/tna/user/tnaFixRequestResult";
	}

	@RequestMapping("tnaFixHistoryPage.tna")
	// 근태조정신청 페이지 접속
	public String tnaFixHistoryPage(HttpServletRequest request, Model model) {
		// 세션 id값 가져오기
		String sessionId = (String)session.getAttribute("id");
		// 네비게이션 페이지 값
		String cpage = request.getParameter("cpage");

		// 페이지 값이 없을경우 1로 세팅
		cpage = tservice.cpageVerify(cpage);

		// 근태조정신청 내역 리스트
		List<Map<String, Object>> tnaFixRequestList = tservice.getTnaFixRequestList(sessionId, Integer.parseInt(cpage));
		// 네비게이션 바
		String navi = tservice.getTnaFixRequestNavi(sessionId, Integer.parseInt(cpage));


		model.addAttribute("tnaFixRequestList", tnaFixRequestList);
		model.addAttribute("navi", navi);
		model.addAttribute("cpage", cpage);

		return "/tna/user/tnaFixHistory";
	}

	// 근태조정신청서 수정 할 경우(반려 혹은 조정중일때)
	@RequestMapping("tnaReFixSubmit.tna")
	public String tnaReFixSubmit(HttpServletRequest request, Model model) throws Exception{
		String sessionId = (String)session.getAttribute("id");//아이디값
		int tna_seq = Integer.parseInt(request.getParameter("tna_seq")); //시퀀스값
		String tna_obj_status = (String)request.getParameter("tna_obj_status"); //출/퇴상태값
		int tna_obj_changed_code = Integer.parseInt(request.getParameter("tna_obj_changed_code"));//변경요청상태
		String tna_obj_reason = request.getParameter("tna_obj_reason");
		
		int result = tservice.tnaReFixSubmit(sessionId,tna_seq,tna_obj_status,tna_obj_changed_code,tna_obj_reason);
		System.out.println("====="+result);
		return "/tna/user/tnaReFixResult";
		
	}

	
	// error
	@ExceptionHandler
	public String exceptionalHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}


}
