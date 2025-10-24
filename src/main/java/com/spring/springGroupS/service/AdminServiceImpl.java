package com.spring.springGroupS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS.dao.AdminDAO;
import com.spring.springGroupS.vo.ComplaintVO;
import com.spring.springGroupS.vo.InquiryReplyVO;
import com.spring.springGroupS.vo.InquiryVO;
import com.spring.springGroupS.vo.ScheduleVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminDAO adminDAO;

	@Override
	public int setMemberLevelChange(int idx, int level) {
		return adminDAO.setMemberLevelChange(idx, level);
	}

	@Override
	public int setMemberLevelSelectChange(String idxSelectArray, int levelSelect) {
		String[] idxSelectArrays = idxSelectArray.split("/");
		
		int res = 0;
		for(String idx : idxSelectArrays) {
			res = adminDAO.setMemberLevelChange(Integer.parseInt(idx), levelSelect);
		}
		
		return res;
	}

	@Override
	public int setBoardComplaintInput(ComplaintVO vo) {
		return adminDAO.setBoardComplaintInput(vo);
	}

	@Override
	public void setBoardTableComplaintOk(int partIdx) {
		adminDAO.setBoardTableComplaintOk(partIdx);
	}

	@Override
	public List<ComplaintVO> getComplaintList(int startIndexNo, int pageSize, String part) {
		return adminDAO.getComplaintList(startIndexNo, pageSize, part);
	}

	@Override
	public ComplaintVO getComplaintSearch(int partIdx) {
		return adminDAO.getComplaintSearch(partIdx);
	}

	@Override
	public int setComplaintDelete(int partIdx, String part) {
		return adminDAO.setComplaintDelete(partIdx, part);
	}

	@Override
	public int setComplaintProcess(int partIdx, String flag) {
		return adminDAO.setComplaintProcess(partIdx, flag);
	}

	@Override
	public int setComplaintProcessOk(int idx, String complaintSw) {
		return adminDAO.setComplaintProcessOk(idx, complaintSw);
	}

	@Override
	public int getComplaintTotRecCnt(String part) {
		return adminDAO.getComplaintTotRecCnt(part);
	}

	@Override
	public List<ScheduleVO> getScheduleMainList() {
		return adminDAO.getScheduleMainList();
	}

	@Override
	public int setAdScheduleInput(ScheduleVO vo) {
		return adminDAO.setAdScheduleInput(vo);
	}

	@Override
	public ArrayList<InquiryVO> getInquiryListAdmin(int startIndexNo, int pageSize, String part) {
		return adminDAO.getInquiryListAdmin(startIndexNo, pageSize, part);
	}

	@Override
	public InquiryVO getInquiryContent(int idx) {
		return adminDAO.getInquiryContent(idx);
	}

	@Override
	public InquiryReplyVO getInquiryReplyContent(int idx) {
		return adminDAO.getInquiryReplyContent(idx);
	}

	@Override
	public int setInquiryInputAdmin(InquiryReplyVO vo) {
		return adminDAO.setInquiryInputAdmin(vo);
	}

	@Override
	public void setInquiryUpdateAdmin(int inquiryIdx) {
		adminDAO.setInquiryUpdateAdmin(inquiryIdx);
	}

	@Override
	public int setInquiryReplyUpdate(InquiryReplyVO reVO) {
		return adminDAO.setInquiryReplyUpdate(reVO);
	}

	@Override
	public int setAdInquiryReplyDelete(int reIdx) {
		return adminDAO.setAdInquiryReplyDelete(reIdx);
	}

	@Override
	public void setAdInquiryDelete(int idx, String fSName, int reIdx) {
		adminDAO.setAdInquiryDelete(idx, fSName, reIdx);
	}

	@Override
	public int setInquiryReplyStatusUpdate(int inquiryIdx) {
		return adminDAO.setInquiryReplyStatusUpdate(inquiryIdx);
	}
	
}
