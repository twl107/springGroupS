package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.ReviewVO;

public interface ReviewService {

	int setReviewInputOk(ReviewVO vo);

	int setReviewDelete(int idx);

	int setReviewReplyInputOk(ReviewVO vo);

	int setReviewReplyDelete(int replyIdx);

	List<ReviewVO> getReviewCheck(ReviewVO vo);

	void setMemberPointPlus(String mid, int point);

}
