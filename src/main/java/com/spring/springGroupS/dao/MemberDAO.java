package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberIdCheck(@Param("mid") String mid);

	MemberVO getMemberNickCheck(@Param("nickName") String nickName);

	int setMemberJoin(@Param("vo") MemberVO vo);

	//void setLastDateUpdate(@Param("mid") String mid);

	int setMemberPwdChange(@Param("mid") String mid, @Param("pwd") String pwd);

	void setMemberTodayCntClear(@Param("mid") String mid);

	void setMemberInforUpdate(@Param("mid") String mid, @Param("point") int point);

	List<MemberVO> getmemberIdSearch(@Param("email") String email);

	void setMemberInforUpdateMinus(@Param("mid") String mid);

	void setMemberLevelUp(@Param("mid") String mid);

	int setMemberUpdateOk(@Param("vo") MemberVO vo);

	int setUserDelete(@Param("mid") String mid);

	List<MemberVO> getMemberList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("level") int level);

	int getTotRecCnt();

	List<MemberVO> getMemberLevelCount(@Param("level") int level);

}
