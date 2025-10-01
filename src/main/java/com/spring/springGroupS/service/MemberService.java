package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.MemberVO;

public interface MemberService {

	MemberVO getMemberIdCheck(String mid);

	MemberVO getMemberNickCheck(String nickName);

	int setMemberJoin(MemberVO vo);

	//void setLastDateUpdate(String mid);

	int setMemberPwdChange(String mid, String pwd);

	void setMemberTodayCntClear(String mid);

	void setMemberInforUpdate(String mid, int point);

	List<MemberVO> getmemberIdSearch(String email);

	void setMemberInforUpdateMinus(String mid);

	void setMemberLevelUp(String mid);

	int setMemberUpdateOk(MemberVO vo);

	int setUserDelete(String mid);

	List<MemberVO> getMemberList(int startIndexNo, int pageSize, int level);

	int getTotRecCnt();

	List<MemberVO> getMemberLevelCount(int level);

	MemberVO getMemberNickNameEmailCheck(String nickName, String email);

	void setKakaoMemberInput(String mid, String pwd, String nickName, String email);

}
