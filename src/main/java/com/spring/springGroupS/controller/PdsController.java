package com.spring.springGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.springGroupS.common.Pagination;
import com.spring.springGroupS.service.PdsService;
import com.spring.springGroupS.vo.PageVO;
import com.spring.springGroupS.vo.PdsVO;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	Pagination pagination;
	
	
	@GetMapping("/pdsList")
	public String pdsListGet(Model model, PageVO pageVO) {
		pageVO.setSection("pds");
		pageVO = pagination.pagination(pageVO);
		
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(), pageVO.getPageSize(), pageVO.getPart());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "pds/pdsList";
	}
	
	@GetMapping("/pdsInput")
	public String pdsInputGet() {
		return "pds/pdsInput";
	}
	
	
	
}
