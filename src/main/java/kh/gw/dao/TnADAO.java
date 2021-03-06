package kh.gw.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.gw.dto.TnA_StandardTimeDTO;
import kh.gw.dto.TnADTO;
import kh.gw.dto.TnA_objectionDTO;
import kh.gw.dto.TnA_statusDTO;
import kh.gw.statics.BoardConfigurator;

@Repository
public class TnADAO {
	
	@Autowired
	private SqlSession db;
	
	public TnADTO selectByID(String id) {
		return db.selectOne("selectByID",id);
	}
	
	public int attendanceInput(TnADTO tdto) {
		return db.insert("TnA.attendanceInput",tdto);
	}
	
	public int leaveWorkInput(TnADTO tdto) {
		return db.update("TnA.leaveWorkInput",tdto);
	}
	
	public int getTnaSeq(String sessionId) {
		return db.selectOne("TnA.getTnaSeq", sessionId);
	}
	
	public Date getAttendanceTime(String sessionId) {
		return db.selectOne("TnA.getAttendanceTime",sessionId);
	}
	
	public Date getLeaveWorkTime(String sessionId) {
		return db.selectOne("TnA.getLeaveWorkTime",sessionId);
	}
	
	public List<Map<String, Object>> getTnaCalendarList(String sessionId) {
		List<Map<String, Object>> result = db.selectList("TnA.getTnaCalendarList", sessionId);
		System.out.println("=====dao다!!==="+result.size());
		 return result;
	}
	
	public Map<String, Object> getTnaCalendarValue(Map<String, Object> param) {
		return db.selectOne("TnA.getTnaCalendarValue", param);
	}
	
	public List<TnA_statusDTO> getTnaStatusList() {
		return db.selectList("TnA.getTnaStatusList");
	}
	
	public int tnaFixRequestInput(TnA_objectionDTO dto) {
		return db.insert("TnA.tnaFixRequestInput", dto);
	}
	
	public Map<String, Object> tnaCheckOverlap(TnA_objectionDTO dto) {
		return db.selectOne("TnA.tnaCheckOverlap", dto);
	}
	
	public List<Map<String, Object>> getTnaStartCountList(String sessionId, String selectYearValue) {
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		param.put("selectYearValue", selectYearValue);
		
		return db.selectList("TnA.getTnaStartCountList", param);
	}
	
	public List<Map<String, Object>> getTnaStartCountSumList(String sessionId, String selectYearValue) {
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		param.put("selectYearValue", selectYearValue);
		
		return db.selectList("TnA.getTnaStartCountSumList", param);
	}
	
	public List<Map<String, Object>> getTnaEndCountList(String sessionId, String selectYearValue) {
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		param.put("selectYearValue", selectYearValue);
		
		return db.selectList("TnA.getTnaEndCountList", param);
	}
	
	public List<Map<String, Object>> getTnaEndCountSumList(String sessionId, String selectYearValue) {
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		param.put("selectYearValue", selectYearValue);
		
		return db.selectList("TnA.getTnaEndCountSumList", param);
	}
	
	public List<Map<String, Object>> getTnaSelectYearList(String sessionId) {
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		
		return db.selectList("TnA.getTnaSelectYearList", param);
	}
	
	public List<Map<String, Object>> getTnaFixRequestList(String sessionId, int cpage) {
		
		int startRowNum = (cpage-1) * BoardConfigurator.RECORD_COUNT_PER_PAGE + 1;
		int endRowNum = startRowNum + BoardConfigurator.RECORD_COUNT_PER_PAGE - 1;
		
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		param.put("startRowNum", startRowNum);
		param.put("endRowNum", endRowNum);
		
		return db.selectList("TnA.getTnaFixRequestList", param);
	}
	
	public List<Map<String, Object>> getTnaFixRequestListAll(String sessionId) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		
		return db.selectList("TnA.getTnaFixRequestListAll", param);
	}
	
	public List<Map<String, Object>> tnaHistory() throws Exception{
		return db.selectList("TnA.tnaHistory");
	}

	public int tnaStartApp(int tnaSeq, int finalChange) throws Exception {
		Map<String,Integer> param = new HashMap<>();
		param.put("tnaSeq", tnaSeq);
		param.put("finalChange", finalChange);
		return db.update("TnA.tnaStartApp",param);
	}

	public int tnaEndApp(int tnaSeq, int finalChange) throws Exception{
		Map<String,Integer> param = new HashMap<>();
		param.put("tnaSeq", tnaSeq);
		param.put("finalChange", finalChange);
		return db.update("TnA.tnaEndApp",param);
	}

	public int objApproval(int objSeq, int statusCode) throws Exception{
		Map<String,Integer> param = new HashMap<>();
		param.put("objSeq", objSeq);
		param.put("statusCode", statusCode);
		return db.update("TnA.objApproval",param);
	}

	public int tnaReFixSubmit(String sessionId, int tna_seq, String tna_obj_status, int tna_obj_changed_code,
			String tna_obj_reason) throws Exception{
		Map<String, Object> param = new HashMap<>();
		param.put("sessionId", sessionId);
		param.put("tna_seq", tna_seq);
		param.put("tna_obj_status", tna_obj_status);
		param.put("tna_obj_changed_code", tna_obj_changed_code);
		param.put("tna_obj_reason", tna_obj_reason);
		return db.update("TnA.tnaReFixSubmit",param);
	}
	
	public TnA_StandardTimeDTO tnaStandardTime(){
		return db.selectOne("TnA.tnaStandardTime");
	}

	public int tnaUpdateTime(String att_time, String lea_time, String nig_time) throws Exception {
		Map<String, String> param = new HashMap<>();
		param.put("att_time", att_time);
		param.put("lea_time", lea_time);
		param.put("nig_time", nig_time);
		return db.update("TnA.tnaUpdateTime",param);
	}
	
	
	
}
