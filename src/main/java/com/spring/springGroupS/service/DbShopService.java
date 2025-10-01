package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.DbProductVO;

public interface DbShopService {

	List<DbProductVO> getCategoryMain();

	List<DbProductVO> getCategoryMiddle();

	List<DbProductVO> getCategorySub();

	DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName);

	int setCategoryMainInput(DbProductVO vo);

	DbProductVO getCategoryMiddleOne(DbProductVO vo);

	int setCategoryMainDelete(String categoryMainCode);

	int setCategoryMiddleInput(DbProductVO vo);

	DbProductVO getCategorySubOne(DbProductVO vo);

	int setCategoryMiddleDelete(String categoryMiddleCode);

	List<DbProductVO> getCategoryMiddleName(String categoryMainCode);

	int setCategorySubInput(DbProductVO vo);

	DbProductVO getCategoryProductName(DbProductVO vo);

	int setCategorySubDelete(String categorySubCode);

}
