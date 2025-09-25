package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.ComplaintVO;
import com.spring.springGroupS.vo.ScheduleVO;

public interface AdminService {

	int setMemberLevelChange(int idx, int level);

	int setMemberLevelSelectChange(String idxSelectArray, int levelSelect);

	int setBoardComplaintInput(ComplaintVO vo);

	void setBoardTableComplaintOk(int partIdx);

	List<ComplaintVO> getComplaintList(int startIndexNo, int pageSize, String part);

	ComplaintVO getComplaintSearch(int partIdx);

	int setComplaintDelete(int partIdx, String part);

	int setComplaintProcess(int partIdx, String flag);

	int setComplaintProcessOk(int idx, String complaintSw);

	int getComplaintTotRecCnt(String part);

	List<ScheduleVO> getScheduleMainList();

	int setAdScheduleInput(ScheduleVO vo);

}
