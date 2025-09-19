package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.PdsVO;

public interface PdsDAO {

	int getTotRecCnt(@Param("part") String part);

	List<PdsVO> getPdsList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part);

}
