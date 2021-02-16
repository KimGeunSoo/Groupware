package kh.gw.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.nexacro.uiadapter17.spring.core.annotation.ParamDataSet;
import com.nexacro.uiadapter17.spring.core.annotation.ParamVariable;
import com.nexacro.uiadapter17.spring.core.data.NexacroResult;

import kh.gw.dto.Approval_typeDTO;
import kh.gw.dto.MemberDTO;
import kh.gw.service.ApprovalService;
import kh.gw.dto.WriteDTO;
import kh.gw.service.DepartmentService;
import kh.gw.service.MemberService;
import kh.gw.service.PositionService;
import kh.gw.service.TnAService;
import kh.gw.service.WriteService;

@Controller
@RequestMapping("/nex")
public class AdminController {
	@Autowired
	private MemberService mser;

	@Autowired
	private PositionService pser;
	
	@Autowired
	private DepartmentService dser;
	
	@Autowired
	private ApprovalService aservice;
	
	@Autowired
	private WriteService wser;
	
	@Autowired
	private TnAService tservice;
	
	//페이지 이동
	@RequestMapping("/admin.nexacro")
	public String homeAdmin() {
		return "redirect:/nex/index.html";
	}
	
	//회원목록 로드
	@RequestMapping("/loadMemList.nexacro")
	public NexacroResult loadMem() throws Exception {
		NexacroResult nr = new NexacroResult();
		nr.addDataSet("ds_out",mser.listMem());
		return nr;
	}
	
	//position테이블 로드
	@RequestMapping("loadPoList.nexacro")
	public NexacroResult loadPo() throws Exception {
		NexacroResult nr = new NexacroResult();
		nr.addDataSet("ds_out",pser.listPo());
		return nr;
	}
	
	//dept테이블 로드
	@RequestMapping("loadDeptList.nexacro")
	public NexacroResult loadDept() throws Exception {
		NexacroResult nr = new NexacroResult();
		nr.addDataSet("ds_out",dser.listDept());
		return nr;
	}
	
	//회원등록
	@RequestMapping("insertMem.nexacro")
	public NexacroResult insertMem(@ParamDataSet(name = "ds_in") MemberDTO dto) throws Exception{
		mser.insertMem(dto);
		return new NexacroResult();
	}
	
	//회원수정
	@RequestMapping("updateMemList.nexacro")
	public NexacroResult updateMemList(@ParamDataSet(name = "in_memList") List<MemberDTO> list) throws Exception{
		mser.updateMemList(list);
		return new NexacroResult();
	}
	
	//프로필사진등록
	@RequestMapping("insertProfile.nexacro")
	public String insertProfile(HttpServletRequest request, MultipartFile profilePic) throws Exception {
		String id = request.getParameter("id");
		System.out.println(id);
		mser.insertProfile(profilePic, id);
		return "redirect:/";
	}
	
	//공지사항 글 작성
	@RequestMapping("insertWrtie.nexacro")
	public NexacroResult insertWrite(@ParamDataSet(name = "ds_in") WriteDTO dto) throws Exception{
		wser.insertWrite(dto);
		return new NexacroResult();
	}
	
	//전자결재 종류 관리 페이지로 이동
	@RequestMapping("/nxAppTypeLoad.nexacro")
	public NexacroResult nxAppTypeLoad() throws Exception {
		NexacroResult nr = new NexacroResult();
		nr.addDataSet("ds_out", aservice.nxAllDocsType());
		return nr;
	}
	//전자결재 template 수정용 summertnote.jsp로 감.
	//21.02.15기준으로 해당 webBrowser의 url이 Server::가 아닌 http://localhost/로 되어있기에 나중에 deploy시 수정 요망
	@RequestMapping("/nxToSummernote.nexacro")
	public String nxToSummernote() {
		return "approval/nxTemplateEdit";
	}
	
	//전자결재 문서종류 업데이트
	@RequestMapping("/nxAppTypeUpdate.nexacro")
	public NexacroResult nxAppTypeUpdate(@ParamDataSet(name="ds_in")List<Approval_typeDTO> list) {
		
		NexacroResult nr = new NexacroResult();
		return nr;
	}
	
	//근태조정신청 list
	@RequestMapping("/tnaHistory.nexacro")
	public NexacroResult tnaHistory() throws Exception{
		NexacroResult nr = new NexacroResult();
		List<Map<String, Object>> tnaHistory = tservice.tnaHistory();
		nr.addDataSet("ds_out",tnaHistory);
		return nr;
	}
	
	//근태조정신청 상태 검색버튼
	@RequestMapping("/tnaHistorySearch.nexacro")
	public NexacroResult tnaHistorySearch(@ParamVariable(name="sel")int sel) throws Exception{
		NexacroResult nr = new NexacroResult();
		System.out.println("select값:"+sel);
		List<Map<String, Object>> tnaHistorySearch = tservice.tnaHistorySearch(sel);
		nr.addDataSet("ds_out",tnaHistorySearch);
		return nr;
	}
	
	// error
	@ExceptionHandler
	public String exceptionalHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}

}
