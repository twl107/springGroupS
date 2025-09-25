package com.spring.springGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS.service.ReviewService;
import com.spring.springGroupS.vo.ReviewVO;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	// 원본글에 리뷰(댓글)달기
	@Transactional
	@ResponseBody
	@PostMapping("/reviewInputOk")
	public int reviewInputOkPost(ReviewVO vo) {
		List<ReviewVO> vos = reviewService.getReviewCheck(vo);
		
		// 첫번째 리뷰에서 글이 50자 이상은 100포인트, 20자이상은 50포인트, 5자이상은 10포인트, 그렇지 않으면 포인트는 없다.
    if (vos.size() == 0) {
    	if (vo.getContent().length() >= 50) reviewService.setMemberPointPlus(vo.getMid(), 100);
    	else if(vo.getContent().length() >= 20) reviewService.setMemberPointPlus(vo.getMid(), 50);
    	else if(vo.getContent().length() >= 5) reviewService.setMemberPointPlus(vo.getMid(), 10);
    }
    
		return reviewService.setReviewInputOk(vo);
	}
	
	// 원본글에 작성한 댓글 삭제하기
	@ResponseBody
	@PostMapping("/reviewDelete")
	public int reviewDeletePost(int idx) {
		return reviewService.setReviewDelete(idx);
	}
	
	// 댓글에 대댓글 달기
	@ResponseBody
	@PostMapping("/reviewReplyInputOk")
	public int reviewReplyInputOkPost(ReviewVO vo) {
		return reviewService.setReviewReplyInputOk(vo);
	}
	
	// 리뷰에 작성한 댓글 삭제하기
	@ResponseBody
	@PostMapping("/reviewReplyDelete")
	public int reviewReplyDeletePost(int replyIdx) {
		return reviewService.setReviewReplyDelete(replyIdx);
	}
}
