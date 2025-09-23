package com.spring.springGroupS.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS.vo.PdsVO;
import com.spring.springGroupS.vo.ReviewVO;

public interface PdsService {

	int getTotRecCnt(String part);

	List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

	int setPdsInput(MultipartHttpServletRequest mFile, PdsVO vo);

	PdsVO getPdsContent(int idx);

	void setPdsDownNumCheck(int idx);

	int setPdsDeleteCheck(int idx, String fSName, HttpServletRequest request);

	List<ReviewVO> getReviewList(int idx, String part);

}
