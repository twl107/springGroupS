package com.spring.springGroupS.controller;

import java.awt.Point;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/restApi")
public class RestAPIController {

	// REST Controller을 통한 일반 메세지 처리3(O)
	@GetMapping("/restApi1/{message}")
	public String restApi1Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : " + message;
	}
	
	// REST Controller을 통한 객체 처리4(O)
	//@GetMapping("/restApi2/{message}")
	@RequestMapping(value = "/restApi2/{message}", method = RequestMethod.GET)
	public Point restApi2Get(@PathVariable String message) {
		System.out.println("message : " + message);
		
		Point p = new Point(123, 567);
		System.out.println("p : " + p);
		
		return p;
	}
	
}
