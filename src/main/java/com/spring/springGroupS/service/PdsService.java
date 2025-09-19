package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.PdsVO;

public interface PdsService {

	int getTotRecCnt(String part);

	List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

}
