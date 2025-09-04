package com.spring.springGroupS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.springGroupS.service.Study1Service;
import com.spring.springGroupS.vo.BmiVO;
import com.spring.springGroupS.vo.HoewonVO;
import com.spring.springGroupS.vo.SiteInfor2VO;
import com.spring.springGroupS.vo.SiteInforVO;
import com.spring.springGroupS.vo.SungjukVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/study1")
public class Study1Controller {
	
	@Autowired
	Study1Service study1Service;
	
	// QueryString 방식을 통한 값의 전달
	
	//@RequestMapping(value = "/study1/mapping/test1", method = RequestMethod.GET)
	@GetMapping("/mapping/menu")
	public String menuGet() {
		return "study1/mapping/menu";
	}
	
	@GetMapping("/mapping/test1")
	public String test1Get(HttpServletRequest request) {
		String mid = request.getParameter("mid");
		String pwd = request.getParameter("pwd");
		
		request.setAttribute("mid", mid);
		request.setAttribute("pwd", pwd);
		
		return "study1/mapping/test01";
	}
	
	@GetMapping("/mapping/test2")
	public String test2Get(Model model, String mid, String pwd) {
		model.addAttribute("mid", mid);
		model.addAttribute("pwd", pwd);
		return "study1/mapping/test02";
	}
	
	@GetMapping("/mapping/test3")
	public String test3Get(Model model, 
			@RequestParam(name="mid") String id, 
			@RequestParam(name="pwd") String passwd
		) {
		model.addAttribute("mid", id);
		model.addAttribute("pwd", passwd);
		return "study1/mapping/test03";
	}
	
	@GetMapping("/mapping/test4")
	public String test4Get(Model model, 
			@RequestParam(name="mid") String id, 
			@RequestParam(name="pwd") String passwd,
			@RequestParam(name="name", defaultValue = "손님", required = false) String name,
			@RequestParam(name="sex") int sex
			) {
		
		String gender = "";
		if(sex == 1 || sex == 3) gender = "남자";
		else if(sex == 2 || sex == 4) gender = "여자";
		else gender = "중성";
		
		model.addAttribute("mid", id);
		model.addAttribute("pwd", passwd);
		model.addAttribute("name", name);
		model.addAttribute("gender", gender);
		return "study1/mapping/test04";
	}
	
	@GetMapping("/mapping/test5")
	public String test5Get(Model model, String mid, String pwd, String name, String gender, int age) {
		model.addAttribute("mid", mid);
		model.addAttribute("pwd", pwd);
		model.addAttribute("name", name);
		model.addAttribute("gender", gender);
		model.addAttribute("age", age);
		return "study1/mapping/test05";
	}
	
	@GetMapping("/mapping/test6")
	public String test6Get(Model model, String mid, String pwd, String name, String gender, int age) {
		HoewonVO vo = new HoewonVO();
		
		vo.setMid(mid);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setGender(gender);
		vo.setAge(age);
		
		model.addAttribute("vo", vo);
		return "study1/mapping/test06";
	}
	
	@GetMapping("/mapping/test7")
	public String test7Get(Model model, String mid, String pwd, String name, String gender, int age, HoewonVO vo) {
		//HoewonVO vo = new HoewonVO();
		
		vo.setMid(mid);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setGender(gender);
		vo.setAge(age);
		
		model.addAttribute("vo", vo);
		return "study1/mapping/test07";
	}
	
	@GetMapping("/mapping/test8")
	public String test8Get(Model model, HoewonVO vo) {
		model.addAttribute("vo", vo);
		return "study1/mapping/test08";
	}
	
	@GetMapping("/mapping/test9")
	public ModelAndView test9Get(HoewonVO vo) {
		ModelAndView mv = new ModelAndView("study1/mapping/test09");
		mv.addObject("vo", vo);
		return mv;
	}
	
	/* ---------------------------------------------- */
	
	// Path Variable방식으로의 값전달연습
	@GetMapping("/mapping/test21/{mid}/{pwd}")
	public String test21Get(Model model, @PathVariable String mid, @PathVariable String pwd) {
		model.addAttribute("mid", mid);
		model.addAttribute("pwd", pwd);
		return "study1/mapping/test21";
	}
	
	@GetMapping("/mapping/test22/{id}/{passwd}")
	public String test22Get(Model model, @PathVariable String id, @PathVariable String passwd) {
		model.addAttribute("mid", id);
		model.addAttribute("pwd", passwd);
		return "study1/mapping/test22";
	}
	
	@GetMapping("/mapping/{passwd}/test23/{id}")
	public String test23Get(Model model, @PathVariable String id, @PathVariable String passwd) {
		model.addAttribute("mid", id);
		model.addAttribute("pwd", passwd);
		return "study1/mapping/test23";
	}
	
	@GetMapping("/mapping/{passwd}/{temp}/test24/{id}")
	public String test24Get(Model model, @PathVariable String id, @PathVariable String passwd) {
		model.addAttribute("mid", id);
		model.addAttribute("pwd", passwd);
		return "study1/mapping/test24";
	}
	
	@GetMapping("/mapping/test25/{mid}/{pwd}/{name}/{temp}/{gender}/{age}")
	public String test25Get(Model model, HoewonVO vo
		) {
		model.addAttribute("vo", vo);
		return "study1/mapping/test25";
	}
	
	/* ====================================================== */
	
	// Post방식에 의한 값의 전달
	
	//@GetMapping("/mapping/test31")
	//@RequestMapping(value = "/mapping/test31", method = RequestMethod.POST)
	@PostMapping("/mapping/test31")
	public String test31Post(Model model, HoewonVO vo,
			String mid,
			String pwd,
			String name,
			String gender,
			String strGender,
			int age,
			String nickName			
		) {
		
		vo.setMid(mid);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setGender(strGender);
		//vo.setStrGender(strGender);
		vo.setAge(age);
		vo.setNickName(nickName);
		
		model.addAttribute("vo", vo);
		return "study1/mapping/test31";
	}
	
	@PostMapping("/mapping/test32")
	public String test32Post(Model model, HoewonVO vo) {
		model.addAttribute("vo", vo);
		return "study1/mapping/test32";
	}
	
	@GetMapping("/mapping/test33")
	public String test33Get(Model model, String mid, HoewonVO vo) {
		// 아이디로 DB에서 회원정보를 가져와서 VO에 담아서 jsp로 넘겨준다.
		vo.setMid(mid);
		
		model.addAttribute("vo", vo);
		
		return "study1/mapping/test33";
	}
	
	@PostMapping("/mapping/test33")
	public String test33Post(Model model, HoewonVO vo) {
		// DB에 회원 정보를 저장시킨다.(회원가입처리)
		
		// 회원 가입후 메세지처리한다.
		model.addAttribute("message", vo.getMid() + "님 회원 가입 되었습니다.");
		model.addAttribute("url", "/study1/mapping/test33");
		model.addAttribute("mid", vo.getMid());
//		model.addAttribute("url","/study1/mapping/test33?mid="+vo.getMid());
		return "include/message";
	}
	
	@PostMapping("/mapping/test34")
	public String test34Post(Model model, HoewonVO vo) {
	  // DB에 회원 정보를 저장시킨다.(회원가입처리)
		System.out.println("1.이곳은 회원 정보를 DB에 저장처리하고 있습니다.");
		
		model.addAttribute("message","회원 가입 되었습니다.");
		model.addAttribute("vo", vo);
		
		return "study1/mapping/test34";
	}
	
	@GetMapping("/mapping/test35")
	public String test35Get(Model model, HoewonVO vo) {
		// 아이디로 DB에서 회원정보를 가져와서 VO에 담아서 jsp로 넘겨준다.
		// vo.setMid(mid);
		
		model.addAttribute("vo", vo);
		
		return "study1/mapping/test35";
	}
	
	@PostMapping("/mapping/test35")
	public String test35Post(Model model, HoewonVO vo) {
		// 회원아이디의 첫글자가 'a'로 시작하는 회원만 가입처리하도록 한다.
		
		if(vo.getMid().substring(0, 1).equals("a")) {
			// DB에 회원 정보를 저장시킨다.(회원가입처리)
			System.out.println("2.이곳은 회원 정보를 DB에 저장처리하고 있습니다.");
			return "redirect:/message/hoewonInputOk?mid="+vo.getMid();
		}
		else return "redirect:/message/hoewonInputNo";
	}
	
	@GetMapping("/aop/aopMenu")
	public String aopMenuGet() {
		log.info("study1컨트롤러의 aopMenu메소드입니다.");
		return "study1/aop/aopMenu";
	}
	
	@GetMapping("/aop/test1")
	public String aopTest1Get() {
		log.info("study1컨트롤러의 test1메소드입니다.");
		
		//Study1Service service = new Study1Service();
		//service.getAopServiceTest1();
		
		study1Service.getAopServiceTest1();
		
		return "study1/aop/aopMenu";
	}
	
	@GetMapping("/aop/test2")
	public String aopTest2Get() {
		log.info("study1컨트롤러의 test2메소드입니다.");
		
		study1Service.getAopServiceTest2();
		
		return "study1/aop/aopMenu";
	}
	
	@GetMapping("/aop/test3")
	public String aopTest3Get() {
		log.info("study1컨트롤러의 test3메소드입니다.");
		
		study1Service.getAopServiceTest3();
		
		return "study1/aop/aopMenu";
	}
	
	@GetMapping("/aop/test4")
	public String aopTest4Get() {
		log.info("study1컨트롤러의 test4메소드입니다.");
		
		study1Service.getAopServiceTest52();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		study1Service.getAopServiceTest53();
		
		return "study1/aop/aopMenu";
	}
	
	@GetMapping("/aop/test5")
	public String aopTest5Get() {
		// log.info("study1컨트롤러의 test5메소드입니다.");
		
		study1Service.getAopServiceTest1();
		study1Service.getAopServiceTest2();
		study1Service.getAopServiceTest3();
		study1Service.getAopServiceTest52();
		study1Service.getAopServiceTest53();
		
		return "study1/aop/aopMenu";
	}
	
	// XML 값 주입연습 메뉴
	@GetMapping("/xml/xmlMenu")
	public String xmlMenuGet() {
		return "study1/xml/xmlMenu";
	}
	
	@GetMapping("/xml/xmlTest1")
	public String xmlTest1Get(Model model) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("xml/sungjuk.xml");
		
//		SungjukVO vo1 = context.getBean("vo1", SungjukVO.class);
//		System.out.println("vo1 : " + vo1);
//		
//		SungjukVO vo2 = context.getBean("vo2", SungjukVO.class);
//		System.out.println("vo2 : " + vo2);
//		
//		SungjukVO vo3 = context.getBean("vo3", SungjukVO.class);
//		System.out.println("vo3 : " + vo3);
		
		List<SungjukVO> vos = new ArrayList<SungjukVO>();
		SungjukVO vo = null;
		for(int i=1; i<=3; i++) {
			String str = "vo" + i;
			vo = context.getBean(str, SungjukVO.class);
			vos.add(vo);
		}
		
		model.addAttribute("vos", vos);
		
		context.close();
		return "study1/xml/xmlTest1";
	}
	
	@GetMapping("/xml/xmlTest2")
	public String xmlTest2Get(Model model) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("xml/sungjuk.xml");
		
		List<SungjukVO> vos = new ArrayList<SungjukVO>();
		SungjukVO vo = null;
		for(int i=1; i<=3; i++) {
			String str = "vo" + i;
			vo = context.getBean(str, SungjukVO.class);
			//vo = study1Service.getSungjukCalc(vo);
			study1Service.getSungjukCalc(vo);
			vos.add(vo);
		}
		
		model.addAttribute("vos", vos);
		
		context.close();
		return "study1/xml/xmlTest2";
	}
	
	@GetMapping("/xml/xmlTest3")
	public String xmlTest3Get(Model model) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("xml/siteInfor.xml");
		
		SiteInforVO vo = context.getBean("infor", SiteInforVO.class);
		
		model.addAttribute("vo", vo);
		
		context.close();
		return "study1/xml/xmlTest3";
	}
	
	@GetMapping("/xml/xmlTest4")
	public String xmlTest4Get(Model model) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("xml/siteInforP.xml");
		
		SiteInfor2VO vo = context.getBean("infor", SiteInfor2VO.class);
		
		model.addAttribute("vo", vo);
		
		context.close();
		return "study1/xml/xmlTest3";
	}
	
	@GetMapping("/xml/xmlTest5")
	public String xmlTest5Get(Model model) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("xml/bmi.xml");
		
		List<BmiVO> vos = new ArrayList<BmiVO>();
		
		BmiVO vo = null;
		for(int i=1; i<=50; i++) {
			String str = "person" + i;
			vo = context.getBean(str, BmiVO.class);
			if(vo.getName().equals("")) break;
			// vo = study1Service.getBmiCalc(vo);
			study1Service.getBmiCalc(vo);
			vos.add(vo);
		}
		
		model.addAttribute("vos", vos);
		
		context.close();
		return "study1/xml/xmlTest5";
	}
	
	
	
}
