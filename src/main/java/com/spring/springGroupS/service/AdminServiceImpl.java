package com.spring.springGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS.dao.AdminDAO;
import com.spring.springGroupS.vo.ComplaintVO;

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
	
}
