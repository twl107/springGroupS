package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.UserVO;

public interface User2Service {

	List<UserVO> getUserList();

	List<UserVO> getUserSearch(String mid);

	int setUserInput(UserVO vo);

	int setUserDelete(int idx);

	UserVO getUserIdxSearch(int idx);

	int setUserUpdate(UserVO vo);


}
