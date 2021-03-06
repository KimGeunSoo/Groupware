package kh.gw.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kh.gw.dto.WriteDTO;
import kh.gw.dto.Write_commentsDTO;
import kh.gw.service.WriteService;

@Controller
@RequestMapping("/write")
public class WriteController {

	@Autowired
	private WriteService wservice;

	@Autowired
	private HttpSession session;

	//-------------커뮤니티 들어오기
	@RequestMapping("enterCommunity.write")
	public String enterCommunity() throws Exception{
		return "/write/community";
	}

	//-------------공지사항 목록 보여주기
	@RequestMapping("noticeList.write")
	public String noticeList(Model m, HttpServletRequest request) throws Exception{
		String cpage = request.getParameter("cpage");
		List<WriteDTO>list = wservice.noticeByCpage(Integer.parseInt(cpage),"00");
		String navi = wservice.noticeGetNavi(Integer.parseInt(cpage),"00");
		wservice.addDateStr(list);
		
		m.addAttribute("list", list);
		m.addAttribute("navi", navi);

		return "/write/noticelist";
	}

	//--------------제목 눌렀을때 공지사항 상세로 가기
	@RequestMapping("noticeView.write")
	public String noticeView(Model m, HttpServletRequest request, WriteDTO dto, Write_commentsDTO cdto) throws Exception{
		dto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		WriteDTO dtos = wservice.noticeView(dto.getWrite_seq());
		
		wservice.addDateStrOne(dtos);
		int result = wservice.addViewCount(dto.getWrite_seq()); // 조회수+1
		
		cdto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.commentView(cdto.getWrite_seq());
		
		m.addAttribute("dtos", dtos);
		m.addAttribute("list", list);

		System.out.println(dtos.getWrite_reg_date_wr());
		return "/write/noticeview";
	}

	//-------------- 공지사항 검색하기
	@RequestMapping("noticeSearch.write")
	public String noticeSearch(Model m, HttpServletRequest request, WriteDTO dto) throws Exception{
		String condition = request.getParameter("condition");
		String keyword = request.getParameter("keyword");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		List<WriteDTO>list = wservice.noticeSearch(cpage,condition,"00",keyword);
		wservice.addDateStr(list);
		String navi = wservice.noticeSearchNavi(cpage,condition,"00",keyword);
		m.addAttribute("list",list);
		m.addAttribute("navi", navi);
		m.addAttribute("keyword",keyword);

		return "/write/noticesearchlist";
	}

	//------------- 시스템 공지사항 리스트 보여주기
	@RequestMapping("systemNoticeList.write")
	public String systemNoticeList(Model m, HttpServletRequest request) throws Exception{
		String cpage = request.getParameter("cpage");
		List<WriteDTO>list = wservice.noticeByCpage(Integer.parseInt(cpage),"01");
		String navi = wservice.systemNoticeGetNavi(Integer.parseInt(cpage),"01");
		wservice.addDateStr(list);
		
		m.addAttribute("list", list);
		m.addAttribute("navi", navi);

		return "/write/systemnotice";
	}

	//--------------시스템 공지사항 제목 눌렀을때 시스템 공지사항 상세로 가기
	@RequestMapping("systemNoticeView.write")
	public String systemNoticeView(Model m, HttpServletRequest request, WriteDTO dto, Write_commentsDTO cdto) throws Exception{
		dto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		WriteDTO dtos = wservice.noticeView(dto.getWrite_seq());
		wservice.addDateStrOne(dtos);

		int result = wservice.addViewCount(dto.getWrite_seq()); // 조회수+1

		cdto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.commentView(cdto.getWrite_seq());
		
		m.addAttribute("dtos", dtos);
		m.addAttribute("list", list);
		return "/write/systemnoticeview";
	}
	//--------------시스템 공지사항 검색하기
	@RequestMapping("systemNoticeSearch.write")
	public String systemNoticeSearch(Model m, HttpServletRequest request, WriteDTO dto) throws Exception{
		String condition = request.getParameter("condition");
		String keyword = request.getParameter("keyword");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		List<WriteDTO>list = wservice.noticeSearch(cpage,condition,"01",keyword);
		String navi = wservice.systemNoticeSearchNavi(cpage,condition,"01",keyword);
		wservice.addDateStr(list);
		
		m.addAttribute("list",list);
		m.addAttribute("navi", navi);
		m.addAttribute("keyword",keyword);

		return "/write/systemnoticesearchlist";
	}

	//-------------- 회사 게시판 list
	@RequestMapping("boardList.write")
	public String boardList(Model m, HttpServletRequest request) throws Exception{
		String cpage = request.getParameter("cpage");
		List<WriteDTO>list = wservice.noticeByCpage(Integer.parseInt(cpage),"02");
		String navi = wservice.noticeGetNavi(Integer.parseInt(cpage),"02");
		wservice.addDateStr(list);
		
		m.addAttribute("list", list);
		m.addAttribute("navi", navi);
		session.setAttribute("cpage", cpage);

		return "/write/boardlist";
	}

	//------------ 회사 게시판 제목 눌렀을 때 상세 게시판
	@RequestMapping("boardView.write")
	public String boardView(Model m, HttpServletRequest request, WriteDTO dto, Write_commentsDTO cdto) throws Exception{
		dto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		WriteDTO dtos = wservice.noticeView(dto.getWrite_seq());
		
		dtos.setWrite_contents(wservice.getHtmlText(Integer.parseInt(request.getParameter("write_seq"))));
		
		wservice.addDateStrOne(dtos);
		int result = wservice.addViewCount(dto.getWrite_seq()); // 조회수+1
		cdto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.commentView(cdto.getWrite_seq());
		
		m.addAttribute("dtos", dtos);
		m.addAttribute("list", list);
		return "/write/boardview";
	}
	//----------- 회사 게시판 글 찾기
	@RequestMapping("boardSearch.write")
	public String boardSearch(Model m, HttpServletRequest request, WriteDTO dto) throws Exception{
		String condition = request.getParameter("condition");
		String keyword = request.getParameter("keyword");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		List<WriteDTO>list = wservice.noticeSearch(cpage,condition,"02",keyword);
		String navi = wservice.boardSearchNavi(cpage,condition,"02",keyword);
		wservice.addDateStr(list);
		
		m.addAttribute("list",list);
		m.addAttribute("navi", navi);
		m.addAttribute("keyword",keyword);

		return "/write/boardsearchlist";
	}

	//------------ 회사 게시글 글쓰기 페이지 전환
	@RequestMapping("boardWrite.write")
	public String boardWrite(Model m) throws Exception{
		return "/write/boardwrite";
	}

	//----------- 회사 게시글 글쓰기
	@RequestMapping("insertBoardWrite.write")
	public String insertBoardWrite(WriteDTO dto) throws Exception{
		int result = wservice.insertBoardWrite(dto);
		return "redirect:/write/boardList.write?cpage="+session.getAttribute("cpage");
	}

	//----------- 회사 게시글 삭제
	@RequestMapping("deleteBoardWrite.write")
	public String deleteBoardWrite(HttpServletRequest request, WriteDTO dto) throws Exception{
		dto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		int result = wservice.deleteBoardWrite(dto.getWrite_seq());

		return "redirect:/write/boardList.write?cpage="+session.getAttribute("cpage");
	}
	//------------회사 게시글 수정 전
	@RequestMapping("modifyBeforeBoard.write")
	public String modifyBeforeBoard(HttpServletRequest request, Model m) throws Exception{
		int write_seq = Integer.parseInt(request.getParameter("write_seq"));
		WriteDTO dto = wservice.modifyBeforeBoard(write_seq);
		m.addAttribute("dto", dto);
		return "/write/board_modify_view";
	}
	//------------회사 게시글 수정 후
	@RequestMapping("modifyAfterBoard.write")
	public String modifyAfterBoard(WriteDTO dto) throws Exception{
		int result = wservice.modifyAfterBoard(dto);
		return "redirect:/write/boardList.write?cpage=1";
	}

	//---------- 회사 소개하기
	@RequestMapping("introCompany.write")
	public String introCompany() throws Exception{
		return "/write/company";
	}

	//--------- 갤러리 게시판 리스트
	@RequestMapping("boardGalleryList.write")
	public String boardGalleryList(Model m, HttpServletRequest request) throws Exception{
		String cpage = request.getParameter("cpage");
		List<WriteDTO>list = wservice.noticeByCpage(Integer.parseInt(cpage),"03");
		String navi = wservice.noticeGetNavi(Integer.parseInt(cpage),"03");
		wservice.addDateStr(list);
		
		m.addAttribute("list", list);
		m.addAttribute("navi", navi);

		session.setAttribute("cpage", cpage);

		return "/write/boardgallerylist";
	}

	//------------ 갤러리 게시판 제목 눌렀을 때 상세 게시판
	@RequestMapping("boardGalleryView.write")
	public String boardGalleryView(Model m, HttpServletRequest request, WriteDTO dto, Write_commentsDTO cdto) throws Exception{
		dto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		WriteDTO dtos = wservice.noticeView(dto.getWrite_seq());
		
		dtos.setWrite_contents(wservice.getHtmlText(Integer.parseInt(request.getParameter("write_seq"))));

		wservice.addDateStrOne(dtos);
		int result = wservice.addViewCount(dto.getWrite_seq()); // 조회수+1
		
		cdto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.commentView(cdto.getWrite_seq());

		m.addAttribute("dtos", dtos);
		m.addAttribute("list", list);
		return "/write/boardgalleryview";
	}

	//----------- 갤러리 게시판 글 찾기
	@RequestMapping("boardGallerySearch.write")
	public String boardGallerySearch(Model m, HttpServletRequest request, WriteDTO dto) throws Exception{
		String condition = request.getParameter("condition");
		String keyword = request.getParameter("keyword");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		List<WriteDTO>list = wservice.noticeSearch(cpage,condition,"03",keyword);
		String navi = wservice.gallerySearchNavi(cpage,condition,"03",keyword);
		wservice.addDateStr(list);
		
		m.addAttribute("list",list);
		m.addAttribute("navi", navi);
		m.addAttribute("keyword",keyword);

		return "/write/boardgallerysearchlist";
	}

	//------------ 갤러리 게시글 글쓰기 페이지 전환
	@RequestMapping("galleryWrite.write")
	public String galleryWrite(Model m) throws Exception{
		return "/write/boardgallerywrite";
	}

	//----------- 갤러리 게시글 글쓰기
	@RequestMapping("insertGalleryWrite.write")
	public String insertGalleryWrite(WriteDTO dto) throws Exception{
		int result = wservice.insertGalleryWrite(dto);
		return "redirect:/write/boardGalleryList.write?cpage="+session.getAttribute("cpage");
	}

	//---------- 갤러리 게시글 삭제
	@RequestMapping("deleteGalleryWrite.write")
	public String deleteGalleryWrite(HttpServletRequest request, WriteDTO dto) throws Exception{
		dto.setWrite_seq(Integer.parseInt(request.getParameter("write_seq")));
		int result = wservice.deleteGalleryWrite(dto.getWrite_seq());

		return "redirect:/write/boardGalleryList.write?cpage="+session.getAttribute("cpage");
	}
	//------------갤러리 게시글 수정 전
	@RequestMapping("modifyBeforeGallery.write")
	public String modifyBeforeGallery(HttpServletRequest request, Model m) throws Exception{
		int write_seq = Integer.parseInt(request.getParameter("write_seq"));
		WriteDTO dto = wservice.modifyBeforeGallery(write_seq);
		m.addAttribute("dto", dto);
		return "/write/gallery_modify_view";
	}
	//------------갤러리 게시글 수정 후
	@RequestMapping("modifyAfterGallery.write")
	public String modifyAfterGallery(WriteDTO dto) throws Exception{
		int result = wservice.modifyAfterGallery(dto);
		return "redirect:/write/boardGalleryList.write?cpage=1";
	}
	
	//------------- 댓글 쓰기
	@RequestMapping(value = "commentWrite.write", method = RequestMethod.POST)
	@ResponseBody
	public Object commentWrite(Write_commentsDTO dto, HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		int result = wservice.commentWrite(dto);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.commentNow(dto);
		map.put("cmtList", list);
		String a = gson.toJson(map);
		System.out.println(a);
		return a;
	}
	
	//------------- 댓글 삭제
	@RequestMapping(value = "commentDelete.write", method = RequestMethod.POST)
	@ResponseBody
	public Object commentDelete(HttpServletRequest request, Write_commentsDTO cdto) throws Exception{
//		cdto.setWrite_cmt_seq(Integer.parseInt(request.getParameter("write_cmt_seq")));
		System.out.println(cdto.getWrite_cmt_seq());
		int ac = wservice.commentDelete(cdto.getWrite_cmt_seq());
		if(ac == 1) {
			
			System.out.println("삭제됨");
			
		}else {
			System.out.println("삭제안돼");
			
		}
		Gson gson = new Gson();
		String a = gson.toJson(ac);
		return a;
	}

	//------------- 대댓글 불러오기
	@RequestMapping(value = "reCommentList.write", method = RequestMethod.POST)
	@ResponseBody
	public Object reCommentList(Write_commentsDTO dto, HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.reCommentList(dto);
		map.put("reCmtList", list);
		String reCmtList = gson.toJson(map);
		System.out.println(reCmtList);
		return reCmtList;
	}
	
	//-----------------대댓글 쓰기
	@RequestMapping(value = "reCommentWrite.write", method = RequestMethod.POST)
	@ResponseBody
	public Object reCommentWrite(Write_commentsDTO dto, HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		System.out.println(dto.toString());
		int result = wservice.reCommentWrite(dto);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Write_commentsDTO> list = new ArrayList<Write_commentsDTO>();
		list = wservice.reCommentNow(dto);
		System.out.println(dto.getWrite_cmt_parent_seq());
		System.out.println("리스트가져와" + list);
		map.put("reCmtList", list);
		String reCmtList = gson.toJson(map);
		return reCmtList;
	}
	
	//------------- 대댓글 삭제
		@RequestMapping(value = "commentReDelete.write", method = RequestMethod.POST)
		@ResponseBody
		public Object commentReDelete(HttpServletRequest request, Write_commentsDTO cdto) throws Exception{
			System.out.println(cdto.getWrite_cmt_seq());
			int ac = wservice.commentReDelete(cdto.getWrite_cmt_seq());
			if(ac == 1) {
				
				System.out.println("삭제됨");
				
			}else {
				System.out.println("안돼?");
				
			}
			Gson gson = new Gson();
			String a = gson.toJson(ac);
			return a;
		}
		
	//메인페이지 팝업 최신 1개만 내용 보이기
		@RequestMapping("noticePopupView.write")
		public String noticePopupView(Model m, HttpServletRequest request, WriteDTO dto) throws Exception{
			String write_code = request.getParameter("write_code");
			System.out.println("=============="+write_code);
			List<WriteDTO> dtos = wservice.noticePopupView(write_code);
			wservice.addDateStrTwo(dtos);
			System.out.println("===========title======"+dtos.get(0).getWrite_title());
			m.addAttribute("dtos", dtos);
			return "/write/noticepopuplist";
		}
		
		// error
		@ExceptionHandler
		public String exceptionalHandler(Exception e) {
			e.printStackTrace();
			return "error";
		}
}
