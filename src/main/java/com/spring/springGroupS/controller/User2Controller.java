package com.spring.springGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.springGroupS.service.User2Service;
import com.spring.springGroupS.vo.UserVO;

@Controller
@RequestMapping("/user2")
public class User2Controller {

	@Autowired
	User2Service user2Service;
	
	// 회원 전체 검색
	@GetMapping("/userList")
	public String userListGet(Model model) {
		List<UserVO> vos = user2Service.getUserList();
		
		model.addAttribute("vos", vos);
		
		return "user2/userList";
	}
	
	// 회원 개별 검색
	@GetMapping("/userSearch")
	public String userSearchGet(Model model, String mid) {
		//String mid = "hkd1234";
		List<UserVO> vos = user2Service.getUserSearch(mid);
		
		model.addAttribute("vos", vos);
		model.addAttribute("mid", mid);
		
		return "user2/userList";
	}
	
	// 회원 가입폼 보기
	@GetMapping("/userInput")
	public String userInputGet() {
		return "user2/userInput";
	}
	
	// 회원 가입 처리
	@PostMapping("/userInput")
	public String userInputPost(UserVO vo) {
		int res = user2Service.setUserInput(vo);
		
		if(res != 0) return "redirect:/message/userInputOk";
		else return "redirect:/message/userInputNo";
	}
	
	// 회원 삭제처리
	@GetMapping("/userDelete")
	public String userDeleteGet(int idx) {
		int res = user2Service.setUserDelete(idx);
		
		if(res != 0) return "redirect:/message/userDeleteOk";
		else return "redirect:/message/userDeleteNo";
	}
	
	// 회원 수정 폼보기
	@GetMapping("/userUpdate")
	public String userUpdateGet(Model model, int idx) {
		UserVO vo = user2Service.getUserIdxSearch(idx);
		
		model.addAttribute("vo", vo);
		
		return "user2/userUpdate";
	}
	
	// 회원 수정처리
	@PostMapping("/userUpdate")
	public String userUpdatePost(UserVO vo) {
		int res = user2Service.setUserUpdate(vo);
		
		if(res != 0) return "redirect:/message/userUpdateOk?idx="+vo.getIdx();
		else return "redirect:/message/userUpdateNo?idx="+vo.getIdx();
	}
}
