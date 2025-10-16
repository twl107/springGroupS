package com.spring.springGroupS.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS.common.Pagination;
import com.spring.springGroupS.service.AdminService;
import com.spring.springGroupS.service.MemberService;
import com.spring.springGroupS.vo.ComplaintVO;
import com.spring.springGroupS.vo.InquiryReplyVO;
import com.spring.springGroupS.vo.InquiryVO;
import com.spring.springGroupS.vo.MemberVO;
import com.spring.springGroupS.vo.PageVO;
import com.spring.springGroupS.vo.ScheduleVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	Pagination pagination;
	
		
	@GetMapping("/adminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@GetMapping("/adminLeft")
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@GetMapping("/adminContent")
	public String adminContentGet(Model model, PageVO pageVO) {
		pageVO.setSection("member");
		pageVO.setLevel(3);
		pageVO = pagination.pagination(pageVO);
		
		List<MemberVO> memberVOS = memberService.getMemberLevelCount(pageVO.getLevel());
		
		model.addAttribute("memberLevelCount", memberVOS.size());
		
		return "admin/adminContent";
	}
	
	@GetMapping("/member/adMemberList")
	public String adMemberListGet(Model model, PageVO pageVO) {
		pageVO.setSection("member");
		pageVO = pagination.pagination(pageVO);
		
		List<MemberVO> vos = memberService.getMemberList(pageVO.getStartIndexNo(), pageVO.getPageSize(), pageVO.getLevel());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		return "admin/member/adMemberList";
	}
	
	// 회원 등급 변경 처리
//	@ResponseBody
//	@PostMapping("/member/memberLevelChange")
//	public String memberLevelChangePost(int idx, int level) {
//		return adminService.setMemberLevelChange(idx, level) + "";
//	}
	@ResponseBody
	@PostMapping("/member/memberLevelChange")
	public int memberLevelChangePost(int idx, int level) {
		return adminService.setMemberLevelChange(idx, level);
	}
	
	// 선택한 회원들 등급 변경 처리
	@ResponseBody
	@PostMapping("/member/memberLevelSelectChange")
	public int memberLevelSelectChangePost(String idxSelectArray, int levelSelect) {
		return adminService.setMemberLevelSelectChange(idxSelectArray, levelSelect);
	}
	
	// 신고 리스트 보기
	@GetMapping("/complaint/complaintList")
	public String complaintListGet(Model model, PageVO pageVO) {
		pageVO.setSection("complaint");
		pageVO = pagination.pagination(pageVO);
		List<ComplaintVO> vos = adminService.getComplaintList(pageVO.getStartIndexNo(),pageVO.getPageSize(), pageVO.getPart());
		model.addAttribute("vos", vos);
		return "admin/complaint/complaintList";
	}
	
	// 신고 상세 내역 보기
	@GetMapping("/complaint/complaintContent")
	public String complaintContentGet(Model model, int partIdx) {
		ComplaintVO vo = adminService.getComplaintSearch(partIdx);
		model.addAttribute("vo", vo);
		return "admin/complaint/complaintContent";
	}
	
	// 신고내역자료 '취소(S)/감추기(H)/삭제(D)'
	@ResponseBody
	@PostMapping("/complaint/complaintProcess")
	public int complaintProcessPost(ComplaintVO vo) {
		int res = 0;
		if(vo.getComplaintSw().equals("D")) {
			res = adminService.setComplaintDelete(vo.getPartIdx(), vo.getPart());
			vo.setComplaintSw("처리완료(D)");
		}
		else {
			if(vo.getComplaintSw().equals("H")) {
				res = adminService.setComplaintProcess(vo.getPartIdx(), "HI");
				vo.setComplaintSw("처리중(H)");
			}
			else {
				res =adminService.setComplaintProcess(vo.getPartIdx(), "NO");
				vo.setComplaintSw("처리완료(S)");
			}
		}
		if(res != 0) adminService.setComplaintProcessOk(vo.getIdx(), vo.getComplaintSw());
		
		return res;
	}
	
	// 스케줄 공지내역 등록폼보기
	@GetMapping("/schedule/adScheduleList")
	public String adScheduleListGet(Model model) {
		List<ScheduleVO> scheduleVos = adminService.getScheduleMainList();
		model.addAttribute("scheduleVos", scheduleVos);
		return "admin/schedule/adScheduleList";
	}
	
	// 스케줄 공지 행사내역 등록하기
	@ResponseBody
	@PostMapping("/schedule/adScheduleInput")
	public int adScheduleInputPost(ScheduleVO vo) {
		return adminService.setAdScheduleInput(vo);
	}
	
  // 관리자 1:1 리스트 보여주기
	@GetMapping("/inquiry/adInquiryList")
	public String adInquiryListGet(
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
	    @RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
			Model model) {
		PageVO pageVO = new PageVO();
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setPart(part);
		pageVO.setSection("adminInquiry");
		pageVO = pagination.pagination(pageVO);
		
    ArrayList<InquiryVO> vos = adminService.getInquiryListAdmin(pageVO.getStartIndexNo(), pageSize, part);
    
    model.addAttribute("vos", vos);
	  model.addAttribute("pageVO", pageVO);
	  model.addAttribute("part", part);
		
		return "admin/inquiry/adInquiryList";
	}
	
	// 관리자 답변달기 폼 보여주기(관리자가 답변글 수정/삭제처리하였을때도 함께 처리하고 있다.)
	@GetMapping("/inquiry/adInquiryReply")
	public String adInquiryReplyGet(Model model, int idx,
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
	    @RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
	    @RequestParam(name="replySw", defaultValue="", required=false) String replySw
		) {
		InquiryVO vo = adminService.getInquiryContent(idx);
		InquiryReplyVO reVO = adminService.getInquiryReplyContent(idx);
		
		model.addAttribute("part", part);
		model.addAttribute("pag", pag);
		model.addAttribute("vo", vo);
		model.addAttribute("reVO", reVO);
		model.addAttribute("replySw", replySw);
		return "admin/inquiry/adInquiryReply";
	}
	
	// 관리자 답변달기 저장하기
	@Transactional
	@ResponseBody
	@PostMapping("/inquiry/adInquiryReplyInput")
	public int adInquiryReplyInputPost(InquiryReplyVO vo) {
		int res = 0;
		res = adminService.setInquiryInputAdmin(vo);
		if(res != 0) adminService.setInquiryUpdateAdmin(vo.getInquiryIdx());
		
		return res;
	}
	
	// 관리자 답변글 수정처리
	@PostMapping("/inquiry/adInquiryReply")
	public String adInquiryReplyUpdatePost(InquiryReplyVO reVO) {
		int res = adminService.setInquiryReplyUpdate(reVO);	// 관리자가 답변글을 수정했을때 처리루틴
		
		if(res != 0) return "redirect:/message/adInpuiryReplyUpdateOk?idx="+reVO.getInquiryIdx();
		return "redirect:/message/adInpuiryReplyUpdateNo?idx="+reVO.getInquiryIdx();
	}
	
	// 답변글만 삭제하기(답변글을 삭제처리하면 원본글의 '상태'는 '답변대기중'으로 수정해준다.
	@Transactional
	@ResponseBody
	@PostMapping("/inquiry/adInquiryReplyDelete")
	public int adInquiryReplyDeletePost(int inquiryIdx, int reIdx) {
		adminService.setAdInquiryReplyDelete(reIdx);
		return adminService.setInquiryReplyStatusUpdate(inquiryIdx);
	}
	
	// 관리자 원본글과 답변글 삭제처리(답변글이 있을경우는 답변글 먼저 삭제후 원본글을 삭제처리한다.)
	@Transactional
	@RequestMapping(value="/inquiry/adInquiryDelete", method = RequestMethod.GET)
	public String adInquiryDeleteGet(Model model, int idx, String fSName, int reIdx, int pag) {
		//adminService.setAdInquiryReplyDelete(reIdx);	// 관리자가 현재글을 삭제했을때 먼저 답변글을 삭제처리해준다.
		adminService.setAdInquiryDelete(idx, fSName, reIdx); // 답변글 삭제처리가 끝나면 원본글을 삭제처리해준다. (답변글삭제와 원본글 삭제를 동시에 처리한다.)
		model.addAttribute("pag", pag);
		return "redirect:/message/adInquiryDeleteOk";
	}
	
}
