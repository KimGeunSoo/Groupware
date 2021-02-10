package kh.gw.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import kh.gw.dao.MemberDAO;
import kh.gw.dto.DepartmentDTO;
import kh.gw.dto.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO mdao;
	@Autowired 
	private ServletContext servletContext;
	
	public int loginCheck(MemberDTO dto) throws Exception{
		String result = mdao.loginCheck(dto);
		if(result!=null) {return 1;}
		else return 0;
	}
	
	public MemberDTO getMemInfo(String id) throws Exception{
		return mdao.getMemInfo(id);
	}
	
	public List<MemberDTO> listMem() throws Exception{
		return mdao.listMem();
	}
	
	public List<DepartmentDTO> listDept() throws Exception{
		return mdao.listDept();
	}
	public Map<String,Object> getMyInfo(String id) throws Exception{
		return mdao.getMyInfo(id);
	}
	public int insertMem(MemberDTO dto) throws Exception{
		return mdao.insertMem(dto);
	}
	public void updateMemList(List<MemberDTO> list) throws Exception {
		for(MemberDTO dto : list) {
			mdao.updateMemInfo(dto);
		}
	}
	public void insertProfile(MultipartFile profilePic, String id) throws IOException {
		String realPath = servletContext.getRealPath("/resources/profileImage");
		//String dir2 = "C:\\nexacro\\workspace_final\\Groupware\\src\\main\\webapp\\resources\\profileImage";
		File filesPath = new File(realPath);
		
		if(!filesPath.exists()) {filesPath.mkdir();}
		
		File targetLoc = new File(filesPath.getAbsoluteFile()+"/"+id + ".png");
		FileCopyUtils.copy(profilePic.getBytes(), targetLoc);			
		System.out.println(filesPath.getAbsoluteFile());
		System.out.println("real path : "+realPath);
		System.out.println(targetLoc);
		//Context는 맥락이라는 의미인데, 같은 행동을 하더라고 환경에 따라 다르게 output이 나오는 것을 의미한다. (마우스 오른쪽 버튼 메뉴와 비슷함)
		//session.getServletContext().getRealPath("path")은 session이 존재하는 환경의 realPath를 가져오는 것인데, 이는 이 project가 실행되는 폴더를 의미한다. 
		//getRealPath에 넣는 param은 realPath뒤쪽에 해당 param이 붙은 경로가 return된다. 

	}
}
