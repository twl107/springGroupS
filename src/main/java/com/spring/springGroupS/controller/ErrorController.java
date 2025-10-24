package com.spring.springGroupS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.springGroupS.vo.UserVO;

@Controller
@RequestMapping("/error")
public class ErrorController {

	@GetMapping("/errorForm")
	public String errorFormGet() {
		return "error/errorForm";
	}
	
	@GetMapping("/errorMessage")
	public String errorMessageGet() {
		return "error/errorMessage";
	}
	
	@GetMapping("/errorJsp")
	public String errorJspGet() {
		return "error/errorJsp";
	}
	
	@GetMapping("/errorTest400")
	public String errorTest400Get(UserVO vo) {
		int idx = vo.getIdx();
		System.out.println("idx : " + idx);
		return "error/errorForm";
	}
	
	@GetMapping("/error400")
	public String error400Get() {
		return "error/error400";
	}
	
	@GetMapping("/error404")
	public String error404Get() {
		return "error/error404";
	}
	
	@PostMapping("/errorTest405")
	public String errorTest405Post() {
		return "error/errorForm";
	}
	
	@GetMapping("/error405")
	public String error405Get() {
		return "error/error405";
	}
	
	@GetMapping("/errorTest500")
	public String errorTest500Get(UserVO vo) {
//		int len = vo.getMid().length();
//		System.out.println("len : " + len);
		
		//String su = "010";
//		String su = "O1O";
//		int intSu = Integer.parseInt(su);
//		System.out.println("intSu : " + intSu);
		
		int su = 10 / 0;
		System.out.println("su : " + su);
		
		return "error/error500";
	}
	
	@GetMapping("/error500")
	public String error500Get() {
		return "error/error500";
	}
	
	@GetMapping("/errorNullPointer")
	public String errorNullPointerGet() {
		return "error/errorNullPointer";
	}
	
	@GetMapping("/errorNumberFormat")
	public String errorNumberFormatGet() {
		return "error/errorNumberFormat";
	}
	
	@GetMapping("/errorArithmetic")
	public String errorArithmeticGet() {
		return "error/errorArithmetic";
	}
	
}
