package com.spring.springGroupS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS.service.ScheduleService;
import com.spring.springGroupS.vo.ScheduleVO;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	
	@GetMapping("/schedule")
	public String scheduleGet() {
		scheduleService.getScheduleList();
		return "schedule/schedule";
	}
	
	// 보고자 하는 일정 가져오기(날짜형식을 맞춰서 DB에서 비교할수 있게한다.)
	@GetMapping("/scheduleMenu")
	public String scheduleMenuGet(Model model, HttpSession session, String ymd) {
		String mid = (String) session.getAttribute("sMid");
		
		String mm = "", dd = "";	// ymd : 2025-9-5 => 2025-09-05
		if(ymd.length() != 10) {
			String[] ymdArr = ymd.split("-");
			if(ymdArr[1].length() == 1) mm = "0" + ymdArr[1];
			else mm = ymdArr[1];
			if(ymdArr[2].length() == 1) dd = "0" + ymdArr[2];
			else dd = ymdArr[2];
			ymd = ymdArr[0] + "-" + mm + "-" + dd;
		}
		
		List<ScheduleVO> vos = scheduleService.getScheduleMenu(mid, ymd);
		model.addAttribute("vos", vos);
		model.addAttribute("ymd", ymd);
		model.addAttribute("scheduleCnt", vos.size());
		
		return "schedule/scheduleMenu";
	}
	
	// 스케줄 등록하기
	@ResponseBody
	@RequestMapping(value = "/scheduleInputOk", method=RequestMethod.POST)
	public int scheduleInputOkPost(ScheduleVO vo) {
		return scheduleService.setScheduleInputOk(vo);
	}
	
	// 스케줄 수정하기
	@ResponseBody
	@RequestMapping(value = "/scheduleUpdateOk", method=RequestMethod.POST)
	public int scheduleUpdateOkPost(ScheduleVO vo) {
		return scheduleService.setScheduleUpdateOk(vo);
	}
	
	// 스케줄 삭제하기
	@ResponseBody
	@RequestMapping(value = "/scheduleDeleteOk", method=RequestMethod.POST)
	public int scheduleDeleteOkPost(int idx) {
		return scheduleService.setScheduleDeleteOk(idx);
	}
	
}
