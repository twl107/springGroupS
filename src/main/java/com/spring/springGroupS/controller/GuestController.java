package com.spring.springGroupS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springGroupS.service.GuestService;
import com.spring.springGroupS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	GuestService guestService;
	
	// 방명록 전체 리스트(페이징처리)
	@GetMapping("/guestList")
	public String guestListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize
		) {
		int totRecCnt = guestService.getTotRecCnt();
		int totPage = (totRecCnt % pageSize) == 0 ? totRecCnt / pageSize : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;	
		
		List<GuestVO> vos = guestService.getGuestList(startIndexNo, pageSize);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		//model.addAttribute("totRecCnt", totRecCnt);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		model.addAttribute("vos", vos);
		
		return "guest/guestList";
	}
	
	// 방명록 등록폼 보기
	@GetMapping("/guestInput")
	public String guestInputGet() {
		return "guest/guestInput";
	}
	
	// 방명록 등록 처리
	@PostMapping("/guestInput")
	public String guestInputPost(GuestVO vo) {
		int res = guestService.setGuestInput(vo);
		
		if(res != 0) return "redirect:/message/guestInputOk";
		else return "redirect:/message/guestInputNo";
	}
	
	// 관리자 인증폼 보기
	@GetMapping("/admin")
	public String adminGet() {
		return "guest/admin";
	}
	
	// 관리자 인증처리
	@PostMapping("/admin")
	public String adminPost(String mid, String pwd, HttpSession session) {
		if(mid.equals("admin") && pwd.equals("1234")) {
			session.setAttribute("sAdmin", "adminOK");
			return "redirect:/message/adminOk";
		}
		else return "redirect:/message/adminNo";
	}
	
	// 관리자 인증 로그아웃
	@GetMapping("/adminOut")
	public String adminOutGet(HttpSession session) {
		session.removeAttribute("sAdmin");
		
		return "redirect:/message/adminOut";
	}
	
	// 방명록 게시글 삭제처리
	@GetMapping("/guestDelete")
	public String guestDeleteGet(int idx) {
		int res = guestService.setGuestDelete(idx);
		
		if(res != 0) return "redirect:/message/guestDeleteOk";
		else return "redirect:/message/guestDeleteNo";
	}
}
