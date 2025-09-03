package com.spring.springGroupS.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Slf4j
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h","/index"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		// trace --- < debug < error < warning < information
		logger.info("info : Welcome home! The client locale is {}.", locale);
//		logger.warn("warn : Welcome home! The client locale is {}.", locale);
//		logger.error("erro : Welcome home! The client locale is {}.", locale);
//		logger.debug("debu : Welcome home! The client locale is {}.", locale);
//		System.out.println("==============================================");
		
		// lombok에서 제공하는 slf4j라이브러리 사용
//		log.info("lombok의 infor");
//		log.warn("lombok의 warn");
//		log.error("lombok의 error");
//		log.debug("lombok의 debug");
//		log.trace("lombok의 trace");
//		log.info("lombok의 infor");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
