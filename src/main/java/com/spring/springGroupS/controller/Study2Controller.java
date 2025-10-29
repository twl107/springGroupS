package com.spring.springGroupS.controller;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.spring.springGroupS.service.Study2Service;
import com.spring.springGroupS.vo.ChartVO;
import com.spring.springGroupS.vo.CrawlingVO;
import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.DbPayMentVO;
import com.spring.springGroupS.vo.KakaoAddressVO;
import com.spring.springGroupS.vo.KakaoPlaceVO;
import com.spring.springGroupS.vo.QrCodeVO;
import com.spring.springGroupS.vo.TransactionVO;

import io.github.bonigarcia.wdm.WebDriverManager;

@Controller
@RequestMapping("/study2")
public class Study2Controller {
	
	@Autowired
	Study2Service study2Service;
	

	@GetMapping("/random/randomForm")
	public String randomFormGet() {
		return "study2/random/randomForm";
	}
	
	@ResponseBody
	@PostMapping("/random/randomCheck")
	public String randomCheckPost() {
		// ((int)(Math.random()*(최대값-최소값+1)) + 최소값;
		return ((int)(Math.random()*(99999999 - 10000000 + 1)) + 10000000) + "";
	}
	
	@ResponseBody
	@PostMapping("/random/uuidCheck")
	public String uuidCheckPost() {
		return UUID.randomUUID().toString();
	}
	
	@ResponseBody
	@PostMapping("/random/alphaNumericCheck")
	public String alphaNumericCheckPost() {
		return RandomStringUtils.randomAlphanumeric(64);
	}
	
	// 달력 출력하기
	@GetMapping("/calendar/calendar")
	public String calendarGet() {
		study2Service.getCalendar();
		return "study2/calendar/calendar";
	}
	
	// validator 폼보기
	@GetMapping("/validator/validatorForm")
	public String validatorFormGet(Model model) {
		List<TransactionVO> vos = study2Service.getUserList();
		model.addAttribute("vos", vos);
		return "study2/validator/validatorForm";
	}
	
	// validator 유저 회원 가입처리
	@ResponseBody
	@PostMapping(value="/validator/validatorForm", produces="application/text; charset=utf8")
	public String validatorFormPost(@Validated TransactionVO vo, BindingResult br) {
		if(br.hasFieldErrors()) {
			System.out.println("error 발생");
			System.out.println("에러 내역 : " + br);
			List<ObjectError> errorList = br.getAllErrors();
			String temp = "";
			for(ObjectError error : errorList) {
				temp = error.getDefaultMessage();
				System.out.println("temp : " + temp);
			}
			return temp;
		}
		else {
			study2Service.setValidatorFormOk(vo);
			return "회원 가입 완료";
		}
		
	}
	
	// validator 유저 회원 삭제처리
	@ResponseBody
	@PostMapping("/validator/validatorDeleteOk")
	public int validatorDeleteOkPost(int idx) {
		return study2Service.setValidatorDeleteOk(idx);
	}
	
	// 트랜잭션 연습폼 보기
	@GetMapping("/transaction/transactionForm")
	public String transactionFormGet(Model model) {
		List<TransactionVO> vos = study2Service.getTransactionList();
		List<TransactionVO> vos2 = study2Service.getTransactionList2();
		//System.out.println("vos : " + vos);
		model.addAttribute("vos", vos);
		model.addAttribute("vos2", vos2);
		
		return "study2/transaction/transactionForm";
	}
	
	// 트랜잭션 회원 각각 가입처리(user, user2)
	@Transactional
	@PostMapping("/transaction/transactionForm")
	public String transactionFormPost(TransactionVO vo) {
		// BackEnd 체크 완료... 가정...
		
		study2Service.setTransactionUser1Input(vo);
		study2Service.setTransactionUser2Input(vo);
		
		return "redirect:/message/transactionUserInputOk";
	}
	
	// 회원가입처리를 한번에 처리하기
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/transaction/transaction2", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String transaction2Post(@Validated TransactionVO vo, BindingResult bindingResult, Model model) {
		System.out.println("vo : " + vo);
		System.out.println("error : " + bindingResult.hasErrors());
		
		if(bindingResult.hasFieldErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			String temp = "";
			for(ObjectError error : errorList) {
				temp = error.getDefaultMessage();
				System.out.println("temp : " + temp);
			}
			return temp;
		}
		else {
			study2Service.setTransactionUserTotalInput(vo);
			return "두개 테이블에 모두 저장되었습니다.";
		}
	}
	
	// 공공데이터 API(전국 강력범죄현황)
	@GetMapping("/dataApi/dataApiForm1")
	public String dataApiForm1Get(Model model) {
		return "study2/dataApi/dataApiForm1";
	}
	
	// 공공데이터 API(강력범죄발생현현황 년도별 저장처리)
	@ResponseBody
	@PostMapping("/dataApi/saveCrimeCheck")
	public void saveCrimeCheckPost(CrimeVO vo) {
		study2Service.setSaveCrimeCheck(vo);
	}
	
	// 공공데이터 API(강력범죄발생현현황 년도별 삭제처리)
	@ResponseBody
	@PostMapping("/dataApi/deleteCrimeCheck")
	public void deleteCrimeCheckPost(int year) {
		study2Service.setDeleteCrimeCheck(year);
	}
	
	// 공공데이터 API(강력범죄발생현현황 년도별 출력처리)
	@ResponseBody
	@PostMapping("/dataApi/dbListCrimeCheck")
	public List<CrimeVO> dbListCrimeCheckPost(int year) {
		 return study2Service.setDbListCrimeCheck(year);
	}
	
	// 년도별 + 경찰서 지역별 DB자료 출력처리호출하기
	@Transactional
	@PostMapping("/dataApi/dataApiForm1")
	public String dataApiForm1Post(Model model, int year, String policeZone) {
		List<CrimeVO> vos = study2Service.getDataApiPoliceForm(year, policeZone);
		model.addAttribute("vos", vos);
		model.addAttribute("policeZone", policeZone);	
		
		CrimeVO analyzeVO = study2Service.getCrimeAnalyze(year, policeZone);
		model.addAttribute("year", year);
		model.addAttribute("analyzeVO", analyzeVO);
		
		return "study2/dataApi/dataApiForm1";
	}
	
	
	// 차트연습폼 보기
	@GetMapping("/chart/chartForm")
	public String chartFormGet(Model model, ChartVO vo, 
			@RequestParam(name="part", defaultValue = "barVChart", required = false) String part
		) {
		model.addAttribute("part", part);
		model.addAttribute("vo", vo);
		return "study2/chart2/chartForm";
	}

	// 차트연습폼 보기2
	@RequestMapping(value = "/chart/chart2Form", method = RequestMethod.GET)
	public String chart2FormGet(Model model,
			@RequestParam(name="part", defaultValue="barVChart", required=false) String part) {
		model.addAttribute("part", part);
		return "study2/chart2/chart2Form";
	}
	
	@RequestMapping(value = "/chart/googleChart2", method = RequestMethod.POST)
	public String googleChart2Post(Model model, ChartVO vo) {
		//System.out.println("vo : " + vo);
		model.addAttribute("vo", vo);
		return "study2/chart2/chart2Form";
	}
	
	// Kakaomap 연습
	@GetMapping("/kakao/kakaomap")
	public String kakaomapGet() {
		return "study2/kakao/kakaomap";
	}
	
	// Kakaomap(지도정보획득) - 수정....
	@SuppressWarnings("null")
	@GetMapping("/kakao/kakaoEx1")
	public String kakaoEx1Get(Model model, KakaoAddressVO vo) {
		System.out.println("1.vo : " + vo);
		if(vo.getLatitude() == 0.0) {
			//vo.setAddress("청주그린컴퓨터");
			vo.setLatitude(36.635110507473016);
			vo.setLongitude(127.45959389722837);
		}
		System.out.println("2.vo : " + vo);
		model.addAttribute("vo", vo);
		return "study2/kakao/kakaoEx1";
	}
	
	// Kakaomap(클릭한위치에마커표시)
	@GetMapping("/kakao/kakaoEx2")
	public String kakaoEx2Get() {
		return "study2/kakao/kakaoEx2";
	}
	
	// Kakaomap(클릭한위치에마커표시 DB저정하기)
	@ResponseBody
	@PostMapping("/kakao/kakaoEx2")
	public int kakaoEx2Post(KakaoAddressVO vo) {
		int res = 0;
		KakaoAddressVO searchVO = study2Service.getKakaoAddressSearch(vo.getAddress());
		if(searchVO == null) res = study2Service.setKakaoAddressInput(vo);
		return res;
	}
	
	// Kakaomap(검색한 장소를 DB에서 삭제하기)
	@ResponseBody
	@PostMapping("/kakao/kakaoAddressDelete")
	public int kakaoAddressDeletePost(String address) {
		return study2Service.setKakaoAddressDelete(address);
	}
	
	// Kakaomap(DB에 저장된 장소 표시/이동하기)
	@GetMapping("/kakao/kakaoEx3")
	public String kakaoEx3Get(Model model, KakaoAddressVO vo,
			@RequestParam(name="address", defaultValue = "", required = false) String address
		) {
		if(address.equals("")) {
			vo.setAddress("청주그린컴퓨터");
			vo.setLatitude(36.635110507473016);
			vo.setLongitude(127.45959389722837);
		}
		else {
			vo = study2Service.getKakaoAddressSearch(address);
		}
		
		List<KakaoAddressVO> addressVos = study2Service.getKakaoAddressList();
		model.addAttribute("vo", vo);
		model.addAttribute("addressVos", addressVos);
		
		return "study2/kakao/kakaoEx3";
	}
	
	// Kakaomap(KakaoDB에 저장된 장소 표시/이동하기)
	@GetMapping("/kakao/kakaoEx4")
	public String kakaoEx4Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address	
		) {
		model.addAttribute("address", address);
		return "study2/kakao/kakaoEx4";
	}
	
  // 카카오맵 MyDB에 저장된 지명 주변지역 검색하여 좌표 보여주기
	@GetMapping("/kakao/kakaoEx5")
	public String kakaoEx5Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address
		) {
		//System.out.println("address : " + address);
		KakaoAddressVO vo = new KakaoAddressVO();
		
		List<KakaoAddressVO> addressVos = study2Service.getKakaoAddressList();
		System.out.println("addressVos : " + addressVos);
		if(address.equals("")) {
			vo.setAddress("청주그린컴퓨터");
			vo.setLatitude(36.63508163115122);
			vo.setLongitude(127.45948750459904);
			vo.setIdx(2);
		}
		else {
			vo = study2Service.getKakaoAddressSearch(address);
		}
		model.addAttribute("addressVos", addressVos);
		model.addAttribute("vo", vo);
		
		return "study2/kakao/kakaoEx5";
	}
	
	// 카카오맵에서 선택한 지역을 MyDB에 저장
	@ResponseBody
	@PostMapping("/kakao/kakaoEx5")
	public int kakaoEx5Post(KakaoPlaceVO vo) {
		return study2Service.setKakaoPlaceInput(vo);
	}
	
	// Kakaomap(KakaoDB에 저장된 장소와 주변 표시)
	@GetMapping("/kakao/kakaoEx6")
	public String kakaoEx6Get(Model model,
			@RequestParam(name="idx", defaultValue = "14", required = false) int idx		// '청주그린컴퓨터'가 db에 'idx=14'번이다.
		) {
		KakaoAddressVO centerVO = study2Service.getKakaoAddressSearchIdx(idx);	// 중심좌표
		List<KakaoAddressVO> addressVos = study2Service.getKakaoAddressList();	// 콤보상사에 출력될 지역들
		
		List<KakaoPlaceVO> vo = study2Service.getKakaoAddressPlaceSearch(idx);	// 지도에 표시될 주변관광지
		String json = new Gson().toJson(vo);	// JSON 객체로 바꿔서 넘겨준다.
		
		model.addAttribute("addressVos", addressVos);
		model.addAttribute("voJson", json);
		model.addAttribute("centerVO", centerVO);
		model.addAttribute("idx", idx);
		return "study2/kakao/kakaoEx6";
	}
	
	// 날씨 API 폼
	@GetMapping("/weather/weatherForm")
	public String weatherFormGet(Model model) {
		List<KakaoAddressVO> jiyukVos = study2Service.getKakaoAddressList();
		model.addAttribute("jiyukVos", jiyukVos);
		return "study2/weather/weatherForm";
	}	
	
	// QR Code 연습 폼
	@GetMapping("/qrCode/qrCodeForm")
	public String qrCodeCreateGet() {
		return "study2/qrCode/qrCodeForm";
	}
	
	// QR Code 생성하기
	@ResponseBody
	@PostMapping("/qrCode/qrCodeCreate")
	public String qrCodeCreatePost(HttpServletRequest request, HttpSession session, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(0);
		vo.setMid((String) session.getAttribute("sMid"));
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 개인정보 QR 코드로 생성하기 폼보기
	@GetMapping("/qrCode/qrCodeEx1")
	public String qrCodeEx1Get() {
		return "study2/qrCode/qrCodeEx1";
	}
	
	// QR Code 개인정보 QR 코드 생성
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx1")
	public String qrCodeEx1Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(1);
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 소개사이트 주소 생성하기 폼보기
	@GetMapping("/qrCode/qrCodeEx2")
	public String qrCodeEx2Get() {
		return "study2/qrCode/qrCodeEx2";
	}
	
	// QR Code 소개사이트 주소 생성하기
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx2")
	public String qrCodeEx2Post(HttpServletRequest request, QrCodeVO vo, HttpSession session) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(2);
		vo.setMid((String) session.getAttribute("sMid"));
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 티켓예매 폼보기
	@GetMapping("/qrCode/qrCodeEx3")
	public String qrCodeEx3Get() {
		return "study2/qrCode/qrCodeEx3";
	}
	
	// QR Code 티켓예매 생성하기
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx3")
	public String qrCodeEx3Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(3);
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 티켓예매 폼보기(DB저장 검색)
	@GetMapping("/qrCode/qrCodeEx4")
	public String qrCodeEx4Get() {
		return "study2/qrCode/qrCodeEx4";
	}
	
	// QR Code 티켓예매 생성하기(DB저장 검색)
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx4")
	public String qrCodeEx4Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(4);
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code명 검색하기(DB저장 검색)
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeSearch", method = RequestMethod.POST)
	public QrCodeVO qrCodeSearchPost(String qrCode) {
		return study2Service.getQrCodeSearch(qrCode);
	}
	
	// 썸네일 연습 폼보기
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.GET)
	public String thumbnailFormGet() {
		return "study2/thumbnail/thumbnailForm";
	}
	
	// 썸네일 연습 사진처리
	@ResponseBody
	@PostMapping("/thumbnail/thumbnailForm")
	public String thumbnailFormPost(MultipartFile file, HttpSession session, HttpServletRequest request) {
		String mid = (String) session.getAttribute("sMid");
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		return study2Service.setThumbnailCreate(file, mid, realPath);
	}
	
	// 썸네일 전체 리스트 이미지 보기
	@RequestMapping(value = "/thumbnail/thumbnailResult", method = RequestMethod.GET)
	public String thumbnailResultGet(Model model, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		String[] files = new File(realPath).list();
		
		model.addAttribute("files", files);
		model.addAttribute("fileCount", (files.length / 2));
		
		return "study2/thumbnail/thumbnailResult";
	}
	
	// 썸네일 이미지 삭제처리(1개파일삭제)
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailDelete", method = RequestMethod.POST)
	public int thumbDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		
		int res = 0;
		File sName = new File(realPath + file);
		File fName = new File(realPath + file.substring(2));
		if(fName.exists()) {
			sName.delete();
			fName.delete();
			res = 1;
		}
		return res;
	}
	
  // 썸네일 이미지 삭제처리(전체파일삭제)
	@ResponseBody
	@PostMapping("/thumbnail/thumbnailDeleteAll")
	public int thumbnailDeleteAllPost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		
		int res = 0;
		File targetFolder = new File(realPath);
		if(!targetFolder.exists()) return res;
		
		File[] files = targetFolder.listFiles();
		
		if(files.length != 0) {
			for(File f : files) {
				if(!f.isDirectory()) f.delete();
			}
			res = 1;
		}
		return res;
	}
	
  // 결제처리 연습하기 폼..
  @GetMapping("/payment/payment")
  public String paymentGet() {
  	return "study2/payment/payment";
  }
  
  // 결제처리 연습하기 폼..처리
  @PostMapping("/payment/payment")
  public String paymentPost(Model model, HttpSession session, DbPayMentVO vo) {
  	session.setAttribute("sPayMentVO", vo);
  	model.addAttribute("vo", vo);
  	return "study2/payment/sample";
  }
  
  // 결제처리완료후 확인하는 폼...
  @GetMapping("/payment/paymentOk")
  public String paymentOkGet(Model model, HttpSession session) {
  	DbPayMentVO vo = (DbPayMentVO) session.getAttribute("sPayMentVO");
  	model.addAttribute("vo", vo);
  	session.removeAttribute("sPayMentVO");
  	return "study2/payment/paymentOk";
  }
  
  // Moment라이브러리 연습폼
  @GetMapping("/moment/momentForm")
  public String momentFormGet() {
  	return "study2/moment/momentForm";
  }
  
  // 크롤링(crawling)연습폼 보기
  @GetMapping("/crawling/jsoup")
  public String jsoupGet() {
  	return "study2/crawling/jsoup";
  }
  
  // jsoup을 이용한 크롤링
  @ResponseBody
  @PostMapping("/crawling/jsoup")
  public ArrayList<String> jsoupPost(String url, String selector) throws IOException {
  	Connection conn = Jsoup.connect(url);
  	
  	Document document = conn.get();
  	//System.out.println("document : " + document);
  	
  	Elements selects = document.select(selector);
  	System.out.println("selects : " + selects);
  	System.out.println(selects.text());
  	
  	ArrayList<String> vos = new ArrayList<String>();
  	int i = 0;
  	for(Element select : selects) {
  		i++;
  		System.out.println(i + " : " + select.text());
  		//vos.add(i + " : " + select);
  		vos.add(i + " : " + select.html().replace("data-",""));
  	}
  	
  	return vos;
  }
  
  // jsoup을 이용한 크롤링
  @ResponseBody
  @PostMapping("/crawling/jsoup2")
  public ArrayList<CrawlingVO> jsoup2Post() throws IOException {
  	Connection conn = Jsoup.connect("https://news.naver.com/");
  	
  	Document document = conn.get();
  	
  	
  	ArrayList<String> titleVos = new ArrayList<String>();
  	Elements selects = document.select("strong.cnf_news_title");
  	for(Element select : selects) {
  		titleVos.add(select.html());
  	}
  	
  	ArrayList<String> imageVos = new ArrayList<String>();
  	selects = document.select("div.cnf_news_thumb");
  	for(Element select : selects) {
  		imageVos.add(select.html().replace("data-",""));
  	}
  	
  	ArrayList<String> journalVos = new ArrayList<String>();
  	selects = document.select("em.cnf_journal_name");
  	for(Element select : selects) {
  		journalVos.add(select.html());
  	}
  	
  	
  	ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
  	CrawlingVO vo = null;
  	for(int i=0; i<imageVos.size(); i++) {
  		vo = new CrawlingVO();
  		vo.setItem1(titleVos.get(i));
  		vo.setItem2(imageVos.get(i));
  		vo.setItem3(journalVos.get(i));
  		System.out.println("vo : " + vo);
  		vos.add(vo);
  	}
  	return vos;
  }
  
	// 크롤링연습 처리3(jsoup) - 네이버 검색어를 이용한 검색처리
	@ResponseBody
	@PostMapping("/crawling/jsoup3")
	public ArrayList<CrawlingVO> jsoup3Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		
		Document document = conn.get();
		
		Elements selects = null;
		
		ArrayList<String> contentVos = new ArrayList<String>();
		//selects = document.select(searchSelector);
		selects = document.select("span.sds-comps-text-content");
		for(Element select : selects) {
			contentVos.add(select.html());
			System.out.println("content : " + select);
		}
		
		ArrayList<String> journalVos = new ArrayList<String>();
		selects = document.select("div.sds-comps-profile-info");
		for(Element select : selects) {
			journalVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		//selects = document.select("div.sds-comps-vertical-layout");
		selects = document.select("div.sds-comps-vertical-layout a div.sds-comps-image");
		for(Element select : selects) {
			imageVos.add(select.html());
		}
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
  	CrawlingVO vo = null;
  	for(int i=0; i<contentVos.size(); i++) {
  		vo = new CrawlingVO();
  		vo.setItem1(contentVos.get(i));
  		vo.setItem2(journalVos.get(i));
  		vo.setItem3(imageVos.get(i));
  		vos.add(vo);
  	}
		return vos;
	}
	
	// 크롤링연습 처리4(jsoup) - 네이버 검색어를 이용한 검색처리
	@ResponseBody
	@PostMapping(value="/crawling/jsoup4", produces="application/text; charset=utf8")
	public String jsoup4Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		
		Document document = conn.get();
		
		Elements selects = document.select(searchSelector);
		System.out.println("selects : " + selects);
		
		Element select = selects.first();
		return select.html();
	}
	
	// 크롤링연습 처리5(다음 연예계 소식)
	@ResponseBody
	@PostMapping(value="/crawling/jsoup5")
	public ArrayList<CrawlingVO> jsoup5Post() throws IOException {
		Connection conn = Jsoup.connect("https://entertain.daum.net/");
		
		Document document = conn.get();
		
		Elements selects = null;
		
		ArrayList<String> titleVos = new ArrayList<String>();
		selects = document.select("a.link_txt.valid_link");
		for(Element select : selects) {
			titleVos.add(select.html());
		}		
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selects = document.select("a.link_thumb");
		for(Element select : selects) {
			imageVos.add(select.html().replace("data-onshow-",""));
		}		
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selects = document.select("span.info_thumb");
		for(Element select : selects) {
			broadcastVos.add(select.html());
		}		
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		CrawlingVO vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawlingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vos.add(vo);
		}
		
		return vos;
	}
	
	// 크롤링연습(selenium)
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.GET)
	public String seleniumGet() {
		return "study2/crawling/selenium";
	}
	
	// 크롤링연습 처리(selenium) - google 이미지 검색
	@SuppressWarnings("unused")
	@ResponseBody
	@PostMapping("/crawling/googleImageSearch")
	public List<HashMap<String, Object>> googleImageSearchPost(HttpServletRequest request, String search) {
		List<HashMap<String, Object>> vos = new ArrayList<>();
		
		try {
			// 크롬브라우저를 사용하기 위한 드라이버를 연결한다. 
			WebDriver driver = new ChromeDriver();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			
			WebDriverManager.chromedriver().setup();
			
			driver.get("https://www.google.com/imghp?hl=ko&tab=ri&authuser=0&ogbl");
			
			
			WebElement btnMore = driver.findElement(By.name("q"));
			
			btnMore.sendKeys(search);
			
			btnMore.sendKeys(Keys.ENTER);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
      for (int i = 0; i < 5; i++) {
        js.executeScript("window.scrollBy(0, 1000)");
        Thread.sleep(1000);
      }
			
      List<WebElement> imageElements = driver.findElements(By.cssSelector("img.YQ4gaf"));
      System.out.println("찾은 이미지 개수: " + imageElements.size());
      
      int i = 0;
      for (WebElement img : imageElements) {
      	String src = img.getAttribute("src");
        String dataSrc = img.getAttribute("data-src");
        String srcset = img.getAttribute("srcset");
        String alt = img.getAttribute("alt");

        String imageUrl = null;
        if (dataSrc != null && dataSrc.startsWith("https")) {
            imageUrl = dataSrc;
        } else if (src != null && src.startsWith("https")) {
            imageUrl = src;
        } else if (srcset != null && srcset.contains("https")) {
            imageUrl = srcset.split(" ")[0];
        }

        if (imageUrl != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("index", i++);
            map.put("fileName", alt);
            map.put("imageUrl", imageUrl);
            vos.add(map);

            System.out.println(i + " : " + alt + " => " + imageUrl);
        }
      }
			
			driver.close();
      
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}
	
	// 크롤링연습 처리(selenium) - SRT 열차 조회하기
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/crawling/train", method = RequestMethod.POST)
	public List<HashMap<String, Object>> trainPost(HttpServletRequest request, String stationStart, String stationStop) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String,Object>>();
		try {
			WebDriver driver = new ChromeDriver();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebDriverManager.chromedriver().setup();			
			
			driver.get("http://srtplay.com/train/schedule");

			WebElement btnMore = driver.findElement(By.xpath("//*[@id=\"station-start\"]/span"));
			btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-pos-input\"]"));
      btnMore.sendKeys(stationStart);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-arrive\"]/span"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      btnMore = driver.findElement(By.id("station-pos-input"));
      
      btnMore.sendKeys(stationStop);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}

      btnMore = driver.findElement(By.xpath("//*[@id=\"sr-train-schedule-btn\"]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      List<WebElement> timeElements = driver.findElements(By.cssSelector(".table-body ul.time-list li"));
 			
      HashMap<String, Object> map = null;
      
			for(WebElement element : timeElements){
				map = new HashMap<String, Object>();
				String train=element.findElement(By.className("train")).getText();
				String start=element.findElement(By.className("start")).getText();
				String arrive=element.findElement(By.className("arrive")).getText();
				String time=element.findElement(By.className("time")).getText();
				String price=element.findElement(By.className("price")).getText();
				map.put("train", train);
				map.put("start", start);
				map.put("arrive", arrive);
				map.put("time", time);
				map.put("price", price);
				array.add(map);
			}      
			
      btnMore = driver.findElement(By.xpath("//*[@id=\"scheduleDiv\"]/div[2]/div/ul/li[1]/div/div[5]/button"));
      //System.out.println("요금 조회버튼클릭");
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
			
      driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}      
      
      
	
}
