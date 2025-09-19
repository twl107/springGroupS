package com.spring.springGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springGroupS.dao.MemberDAO;
import com.spring.springGroupS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
//		MemberVO vo = memberDAO.getMemberIdCheck(mid);
//		System.out.println("vo : " + vo);
//		return vo;
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickCheck(String nickName) {
		return memberDAO.getMemberNickCheck(nickName);
	}

	@Override
	public int setMemberJoin(MemberVO vo) {
		return memberDAO.setMemberJoin(vo);
	}

//	@Override
//	public void setLastDateUpdate(String mid) {
//		memberDAO.setLastDateUpdate(mid);
//	}

	@Override
	public int setMemberPwdChange(String mid, String pwd) {
		return memberDAO.setMemberPwdChange(mid, pwd);
	}

	@Override
	public void setMemberTodayCntClear(String mid) {
		memberDAO.setMemberTodayCntClear(mid);
	}

	@Override
	public void setMemberInforUpdate(String mid, int point) {
		memberDAO.setMemberInforUpdate(mid, point);
	}

	@Override
	public List<MemberVO> getmemberIdSearch(String email) {
		return memberDAO.getmemberIdSearch(email);
	}

	@Override
	public void setMemberInforUpdateMinus(String mid) {
		memberDAO.setMemberInforUpdateMinus(mid);
	}

	@Override
	public void setMemberLevelUp(String mid) {
		memberDAO.setMemberLevelUp(mid);
	}

	@Override
	public int setMemberUpdateOk(MemberVO vo) {
		return memberDAO.setMemberUpdateOk(vo);
	}

	@Override
	public int setUserDelete(String mid) {
		return memberDAO.setUserDelete(mid);
	}

	@Override
	public List<MemberVO> getMemberList(int startIndexNo, int pageSize, int level) {
		return memberDAO.getMemberList(startIndexNo, pageSize, level);
	}

	@Override
	public int getTotRecCnt() {
		return memberDAO.getTotRecCnt();
	}

	@Override
	public List<MemberVO> getMemberLevelCount(int level) {
		return memberDAO.getMemberLevelCount(level);
	}
	
}
