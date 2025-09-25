package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.ReviewVO;

public interface ReviewDAO {

	int setReviewInputOk(@Param("vo") ReviewVO vo);

	int setReviewDelete(@Param("idx") int idx);

	int setReviewReplyInputOk(@Param("vo") ReviewVO vo);

	int setReviewReplyDelete(@Param("replyIdx") int replyIdx);

	List<ReviewVO> getReviewCheck(@Param("vo") ReviewVO vo);

	void setMemberPointPlus(@Param("mid") String mid, @Param("point") int point);

}
