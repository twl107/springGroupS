package com.spring.springGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS.dao.PdsDAO;
import com.spring.springGroupS.vo.PdsVO;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;

	@Override
	public int getTotRecCnt(String part) {
		return pdsDAO.getTotRecCnt(part);
	}

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}
	
	
}
