package com.spring.springGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.springGroupS.service.UserService;
import com.spring.springGroupS.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/userList")
	public String userListGet(Model model) {
		List<UserVO> vos = userService.getUserList();
		
		model.addAttribute("vos", vos);
		
		return "user/userList";
	}
	
	@GetMapping("/userSearch")
	public String userSearchGet(Model model, String mid) {
		//mid = "hkd1234";
		UserVO vo = userService.getUserSearch(mid);
		
		System.out.println("vo : " + vo);
		
		return "";
	}
	
}
