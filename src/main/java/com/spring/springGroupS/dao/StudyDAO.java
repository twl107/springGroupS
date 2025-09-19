package com.spring.springGroupS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.MemberVO;
import com.spring.springGroupS.vo.UserVO;

public interface StudyDAO {

	ArrayList<UserVO> getUserList(@Param("mid") String mid);

	UserVO getUserMidSearch(@Param("mid") String mid);

	ArrayList<UserVO> getUserListSearch(@Param("mid") String mid);

	List<MemberVO> getMemberList();

}
