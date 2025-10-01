package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.DbProductVO;

public interface DbShopDAO {

	List<DbProductVO> getCategoryMain();

	List<DbProductVO> getCategoryMiddle();

	List<DbProductVO> getCategorySub();

	DbProductVO getCategoryMainOne(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMainName") String categoryMainName);

	int setCategoryMainInput(@Param("vo") DbProductVO vo);

	DbProductVO getCategoryMiddleOne(@Param("vo") DbProductVO vo);

	int setCategoryMainDelete(@Param("categoryMainCode") String categoryMainCode);

	int setCategoryMiddleInput(@Param("vo") DbProductVO vo);

	DbProductVO getCategorySubOne(@Param("vo") DbProductVO vo);

	int setCategoryMiddleDelete(@Param("categoryMiddleCode") String categoryMiddleCode);

	List<DbProductVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	int setCategorySubInput(@Param("vo") DbProductVO vo);

	DbProductVO getCategoryProductName(@Param("vo") DbProductVO vo);

	int setCategorySubDelete(@Param("categorySubCode") String categorySubCode);

}
