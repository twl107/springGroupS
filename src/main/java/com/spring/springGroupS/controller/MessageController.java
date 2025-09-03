package com.spring.springGroupS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid
		) {
		
		if(msgFlag.equals("hoewonInputOk")) {
			model.addAttribute("message", mid + "님 회원에 정상적으로 가입되었습니다.");
			model.addAttribute("url", "/study1/mapping/test35?mid="+mid);
		}
		else if(msgFlag.equals("hoewonInputNo")) {
			model.addAttribute("message", "회원 가입 실패");
			model.addAttribute("url", "/study1/mapping/menu");
		}
		
		return "include/message";
	}
	
}
