package com.spring.springGroupS.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.springGroupS.common.ARIAUtil;
import com.spring.springGroupS.common.SecurityUtil;
import com.spring.springGroupS.service.Study1Service;
import com.spring.springGroupS.service.StudyService;
import com.spring.springGroupS.vo.BmiVO;
import com.spring.springGroupS.vo.HoewonVO;
import com.spring.springGroupS.vo.MailVO;
import com.spring.springGroupS.vo.MemberVO;
import com.spring.springGroupS.vo.SiteInfor2VO;
import com.spring.springGroupS.vo.SiteInforVO;
import com.spring.springGroupS.vo.SungjukVO;
import com.spring.springGroupS.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/study1")
public class Study1Controller {
	
	@Autowired
	Study1Service study1Service;
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
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
	
	// restApi 폼보기
	@GetMapping("/restApi/restApiForm")
	public String restApiFormGet() {
		return "study1/restApi/restApiForm";
	}
	
	// REST API를 통한 일반 메세지 처리1(X)
	@GetMapping("/restApi/test1/{message}")
	public String restApiTest1Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : " + message;
	}
	
	// REST API를 통한 일반 메세지 처리2(O)
	@ResponseBody
	@GetMapping("/restApi/test2/{message}")
	public String restApiTest2Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : " + message;
	}
	
	// AJax 폼보기
	@GetMapping("/ajax/ajaxForm")
	public String ajaxFormGet() {
		return "study1/ajax/ajaxForm";
	}
	
	// 일반 값 처리
	@ResponseBody
	@GetMapping("/ajax/ajaxTest1")
	public String ajaxTest1Get(int item) {
		return "item = " + item;
	}
	
	// AJax 숫자값 처리
	@ResponseBody
	@PostMapping("/ajax/ajaxTest2")
	public int ajaxTest2Get(int item) {
		return item;
	}
	
	// AJax 문자값 처리
	@ResponseBody
	@PostMapping("/ajax/ajaxTest3")
	public String ajaxTest3Post(String item) {
		return "item = " + item;
	}
	
	// AJax 객체 전송 폼보기
	@GetMapping("/ajax/ajaxObjectForm")
	public String ajaxObjectFormGet() {
		return "study1/ajax/ajaxObjectForm";
	}
	
	// ajax처리결과를 배열(String배열)로 전송...
	@ResponseBody
	@PostMapping("/ajax/ajaxObject1")
	public String[] ajaxObject1Post(String dodo) {
//		String[] strArray = new String[100];
//		strArray = studyService.getCityStringArray(dodo);
//		return strArray;
		return studyService.getCityStringArray(dodo);
	}
	
	// ajax처리결과를 객체배열(ArrayList<String>)로 전송...
	@ResponseBody
	@PostMapping("/ajax/ajaxObject2")
	public ArrayList<String> ajaxObject2Post(String dodo) {
		return studyService.getCityArrayList(dodo);
	}
	
	// ajax처리결과를 객체배열(Map<Object, Object>)로 전송...
	@ResponseBody
	@PostMapping("/ajax/ajaxObject3")
	public Map<Object, Object> ajaxObject3Post(String dodo) {
		ArrayList<String> vos = studyService.getCityArrayList(dodo);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		return map;
	}
	
	// 객체배열(arrayList)로 전송...
	@PostMapping("/ajax/ajaxObject4")
	public String ajaxObject4Post(Model model, String mid) {
		ArrayList<UserVO> vos = studyService.getUserList(mid);
		model.addAttribute("vos", vos);
		
		return "study1/ajax/ajaxObjectForm";
	}
	
	// vo객체로 전송...
	@ResponseBody
	@PostMapping("/ajax/ajaxObject5")
	public UserVO ajaxObject5Post(String mid) {
		return studyService.getUserMidSearch(mid);
	}
	
	// vos객체로 전송...(완전일치)
	@ResponseBody
	@PostMapping("/ajax/ajaxObject6")
	public ArrayList<UserVO> ajaxObject6Post(String mid) {
		return studyService.getUserList(mid);
	}
	
	// vos객체로 전송...(부분일치)
	@ResponseBody
	@PostMapping("/ajax/ajaxObject7")
	public ArrayList<UserVO> ajaxObject7Post(String mid) {
		return studyService.getUserListSearch(mid);
	}
	
	// 암호화 연습 폼
	@GetMapping("/password/passwordForm")
	public String passwordFormGet() {
		return "study1/password/passwordForm";
	}
	
	// sha256암호화(ajax처리)
	@ResponseBody
	@PostMapping(value="/password/sha256", produces="application/text; charset=utf8")
	public String sha256Post(String pwd) {
		String salt = UUID.randomUUID().toString().substring(0, 8);
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(salt + pwd);
		pwd = "salt : " + salt + " / 암호화된 비밀번호 : " + encPwd;
		return pwd;
	}
	
	// aria암호화(ajax처리)
	@ResponseBody
	@PostMapping(value="/password/aria", produces="application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String salt = UUID.randomUUID().toString().substring(0, 8);
		
		String encPwd = ARIAUtil.ariaEncrypt(salt + pwd);
		String decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd = "salt : " + salt + " / 암호화된 비밀번호 : " + encPwd + " / 복호화비번 : " + decPwd.substring(8);
		return pwd;
	}
	
	// BCryptPasswordEncoder암호화(ajax처리)
	@ResponseBody
	@PostMapping(value="/password/bCryptPassword", produces="application/text; charset=utf8")
	public String bCryptPasswordPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String encPwd = passwordEncoder.encode(pwd);
		
		pwd = "암호화된 비밀번호 : " + encPwd;
		return pwd;
	}
	
	// 메일 작성폼 보기
	@GetMapping("/mail/mailForm")
	public String mailFormGet(Model model) {
		List<MemberVO> memberVos = studyService.getMemberList();
		model.addAttribute("memberVos", memberVos);
		model.addAttribute("memberCnt", memberVos.size());
		
		return "study1/mail/mailForm";
	}
	
	// 메일 보내기
	@PostMapping("/mail/mailForm")
	public String mailFormPost(MailVO vo, HttpServletRequest request) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메세지보관함에 저장되는 'content'변수안에 발신자의 필요한 정보를 추가로 담아준다.
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>SpringGroup에서 보냅니다.</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>springGroup</a></p>";
		content += "<hr>";
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content, true);
		
		// FileSystemResource file = new FileSystemResource("D:\\springGroup\\springframework\\works\\springGroupS\\src\\main\\webapp\\resources\\images\\main.jpg");
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		// 첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/2.jpg"));
		messageHelper.addAttachment("2.jpg", file);
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/3.jpg"));
		messageHelper.addAttachment("3.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	// 파일 업로드폼 보기
	@GetMapping("/fileUpload/fileUploadForm")
	public String fileUploadFormGet(Model model, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload");
		
		String[] files = new File(realPath).list();
		
		for(String file : files) {
			System.out.println("file : " + file);
		}
		
		model.addAttribute("files", files);
		model.addAttribute("fileCount", files.length);
		
		return "study1/fileUpload/fileUploadForm";
	}
	
	// 1개 파일 업로드 처리
	@PostMapping("/fileUpload/fileUploadForm")
	public String fileUploadFormPost(MultipartFile fName, String mid) {
		int res = studyService.setFileUpload(fName, mid);
		
		if(res != 0) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadNo";
	}
	
	// 1개 파일 삭제 처리
	@ResponseBody
	@PostMapping("/fileUpload/fileDelete")
	public int fileDeletePost(String file, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		
		int res = 0;
		File fName = new File(realPath + file);
		
		if(fName.exists()) {
			fName.delete();
			res = 1;
		}
		
		return res;
	}
	
	// 전체 파일 삭제 처리
	@ResponseBody
	@PostMapping("/fileUpload/fileAllDelete")
	public int fileAllDeletePost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		
		int res = 0;
		File folder = new File(realPath);
		if(!folder.exists()) return res;
		
		File[] files = folder.listFiles();
		
		if(files.length != 0) {
			for(File file : files) {
				if(!file.isDirectory()) file.delete();
			}
			res = 1;
		}
		return res;
	}
	
	// 멀티 파일 업로드 폼보기
	@GetMapping("/fileUpload/multiFileUpload")
	public String multiFileUploadGet() {
		return "study1/fileUpload/multiFileUploadForm";
	}
	
	// 멀티 파일 업로드 처리하기
	@PostMapping("/fileUpload/multiFileUpload")
	public String multiFileUploadPost(MultipartHttpServletRequest mFile, String mid) {
		int res = studyService.setMultiFileUpload(mFile, mid);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	// sweetAlert 폼보기
	@GetMapping("/sweetAlert/sweetAlertForm")
	public String sweetAlertFormGet() {
		return "study1/sweetAlert/sweetAlertForm";
	}
	
	@ResponseBody
	@PostMapping("/sweetAlert/ajaxSweet")
	public int ajaxSweetPost(int idx) {
		int res = idx + 100;
		return res;
	}
	
	// Range Slider버튼 연습
	@GetMapping("/rangeSlider/rangeSlider")
	public String rangeSliderGet(Model model,
			@RequestParam(name="price", defaultValue = "500000", required=false) int price
		) {
		model.addAttribute("price", price);
		
		return "study1/rangeSlider/rangeSlider";
	}
	
}
