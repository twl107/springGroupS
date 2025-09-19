package com.spring.springGroupS.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.spring.springGroupS.common.ProjectProvide;
import com.spring.springGroupS.service.GuestService;
import com.spring.springGroupS.service.MemberService;
import com.spring.springGroupS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ProjectProvide projectProvide;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	GuestService guestService;
	
	// 로그인 폼
	@GetMapping("/memberLogin")
	public String memberLoginGet(HttpServletRequest request) {
		// 쿠키를 검색해서 cMid가 있을때 가져와서 로그인창의 아이디입력박스에 뿌려준다.
		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "member/memberLogin";
	}
	
  // 로그인 처리하기
	@PostMapping("/memberLogin")
	public String memberLoginPost(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="mid", defaultValue = "hkd1234", required = false) String mid,
			@RequestParam(name="pwd", defaultValue = "1234", required = false) String pwd,
			@RequestParam(name="idSave", defaultValue = "", required = false) String idSave
		) {
		//  로그인 인증처리(스프링 시큐리티의 BCryptPasswordEncoder객체를 이용한 암호화되어 있는 비밀번호 비교하기)
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {
			// 로그인 인증완료시 처리할 부분(1.세션, 2.쿠키, 3.기타 설정값....)
			// 1.세션처리
			String strLevel = "";
			if(vo.getLevel() == 0) strLevel = "관리자";
			else if(vo.getLevel() == 1) strLevel = "우수회원";
			else if(vo.getLevel() == 2) strLevel = "정회원";
			else if(vo.getLevel() == 3) strLevel = "준회원";

			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			session.setAttribute("sLastDate", vo.getLastDate());
			
			// 2.쿠키 저장/삭제
			if(idSave.equals("on")) {
				Cookie cookieMid = new Cookie("cMid", mid);
				cookieMid.setPath("/");
				cookieMid.setMaxAge(60*60*24*7);		// 쿠키의 만료시간을 7일로 지정
				response.addCookie(cookieMid);
			}
			else {
				Cookie[] cookies = request.getCookies();
				if(cookies != null) {
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {
							cookies[i].setPath("/");
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
			
			// 3. 기타처리(DB에 처리해야할것들(방문카운트, 포인트,... 등)
			// 3-1. 기타처리 : 준회원을 정회원 등업처리.. 
			//	(1) 준회원의 정회원 등업조건 : 방명록에 3회이상 글쓰기, 회원로그인 4일 이상
			if(vo.getLevel() == 3 && vo.getVisitCnt() > 3) {
				int guestCnt = guestService.getMemberSearch(mid, vo.getNickName(), vo.getName());
				if(guestCnt >= 3) memberService.setMemberLevelUp(mid);
			}

			// (2) 오늘 첫방문이면 todayCnt = 0, 오늘 첫방문인지를 체크히가위한 변수 todaySw(1은첫방문, 0은 두번이상방문)
			int todaySw = 0;
			if(!LocalDateTime.now().toString().substring(0,10).equals(vo.getLastDate().substring(0,10))) {
				memberService.setMemberTodayCntClear(mid);
				vo.setTodayCnt(0);
				todaySw = 1;
			}
			
			// 3-2. 기타처리 : 
			// (2) 정회원 이상부터는 방문카운트로 10포인트 증정(단, 방문포인트는 정회원 이상부터 지급하기로하고, 1일 50포인트까지만 제한처리)
			if(vo.getLevel() != 3) {
				int point = vo.getTodayCnt() < 5 ? 10 : 0;
				memberService.setMemberInforUpdate(mid, point);
			}
			else memberService.setMemberInforUpdate(mid, 0);
			
			// 앞에서 모든 회원에 대하여 무조건 방문시 총방문횟수와 오늘 방문횟수를 증가시켰기에, 준회원인경우는 같은날 다시 방문했을경우는 '총방문횟수/오늘방문횟수'를 각각 1씩, 방문포인트는 -10을 뺀다.
			// 따라서 방문카운트 4일 이상이되면 정회원으로 등업될수 있는 조건이 된다.
			if(vo.getLevel() == 3 && todaySw == 0) memberService.setMemberInforUpdateMinus(mid);
			
			// 최종 방문일 업데이트처리(앞에서 처리했기에 삭제했다.)
			//memberService.setLastDateUpdate(mid);
			
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		else {
			return "redirect:/message/memberLoginNo";
		}
	}
	
	// 로그아웃 처리
	@GetMapping("/memberLogout")
	public String memberLogoutGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		
		return "redirect:/message/memberLogout?mid="+mid;
	}
	
	// 회원 가입폼 보여주기
	@GetMapping("/memberJoin")
	public String memberJoinGet() {
		return "member/memberJoin";
	}
	
	// 회원 가입 처리(회원 사진을 업로드후, DB에 회원 정보를 저장)
	@PostMapping("/memberJoin")
	public String memberJoinPost(MultipartFile fName, MemberVO vo) {
		
		// '아이디/닉네임' 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirec:/message/idCheckNo";
		if(memberService.getMemberNickCheck(vo.getNickName()) != null) return "redirec:/message/nickNameCheckNo";

		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 회원 사진 등록처리(회원이 사진을 업로드 하지 않았을시는 photo필드를 'noimage.jpg'로 DB에 저장한다.
		// 회원 사진을 등록하였을경우는, 사진을 서버에 저장시키고, 저장시킨파일명을 DB에 저장처리한다.
		//System.out.println("fName : " + fName.getOriginalFilename());
		if(fName.getOriginalFilename().equals("")) vo.setPhoto("noimage.jpg");
		else vo.setPhoto(projectProvide.fileUpload(fName, vo.getMid(), "member"));
		
		int res = memberService.setMemberJoin(vo);
		
		if(res != 0) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
	}
	
	// 아이디 중복체크처리
	@ResponseBody
	@PostMapping("/memberIdCheck")
	public MemberVO memberIdCheckGet(String mid) {
		return memberService.getMemberIdCheck(mid);
	}
	
	// 닉네임 중복체크처리
	@ResponseBody
	@PostMapping("/memberNickCheck")
	public MemberVO memberNickCheckGet(String nickName) {
		return memberService.getMemberNickCheck(nickName);
	}
	
	// 회원가입시 이메일로 인증번호 전송하기
	@ResponseBody
	@PostMapping("/memberEmailCheck")
	public int memberEmailCheckPost(String email, HttpSession session) throws MessagingException {
		String emailKey = UUID.randomUUID().toString().substring(0, 8);
		
		// 이메일 인증키를 세션에 저장시켜둔다.(2분안에 인증하지 않으면 다시 발행해야함...)
		session.setAttribute("sEmailKey", emailKey);
		
		projectProvide.mailSend(email, "이메일 인증키입니다.", "이메일 인증키 : " + emailKey);
		
		return 1;
	}
	
	// 회원가입시 이메일로 인증번호받은 인증키 확인하기
	@ResponseBody
	@PostMapping("/memberEmailCheckOk")
	public int memberEmailCheckOkPost(String checkKey, HttpSession session) {
		String emailKey = (String) session.getAttribute("sEmailKey");
		if(checkKey.equals(emailKey)) {
			session.removeAttribute("sEmeilKey");
			return 1;
		}
		return 0;
	}

	//인증번호 입력 제한시간(2분)안에 인증확인하지 못하면 발행한 인증번호 삭제하기
	@ResponseBody
	@PostMapping("/memberEmailCheckNo")
	public void memberEmailCheckNoPost(HttpSession session) {
	   session.removeAttribute("sEmeilKey");
	}
	
	// 로그인 완료시 회원방으로 이동처리
	@GetMapping("/memberMain")
	public String memberMainGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO mVo = memberService.getMemberIdCheck(mid);
		
		// 방명록에 올린 글의 수
		int guestCnt = guestService.getMemberSearch(mid, mVo.getNickName(), mVo.getName());
		model.addAttribute("guestCnt", guestCnt);
		model.addAttribute("mVo", mVo);
		
		return "member/memberMain";
	}
	
	// 회원 비밀번호 변경폼 보기
	@GetMapping("/memberPwdCheck/{flag}")
	public String memberPwdCheckGet(Model model, @PathVariable String flag) {
		model.addAttribute("flag", flag);
		return "member/memberPwdCheck";
	}
	
	// 회원 비밀번호 검색
	@ResponseBody
	@PostMapping("/memberPwdCheck")
	public String memberPwdCheckPost(String mid, String pwd) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(passwordEncoder.matches(pwd, vo.getPwd())) return "1";
		return "0";
	}
	
	// 회원 비밀번호 변경처리
	@PostMapping("/memberPwdChange")
	public String memberPwdChangePost(String mid, String newPwd) {
		int res = memberService.setMemberPwdChange(mid, passwordEncoder.encode(newPwd));
		if(res != 0) return "redirect:/message/passwordChangeOk";
		else return "redirect:/message/passwordChangeNo";
	}
	
	// 아이디 찾기 폼보기
	@GetMapping("/memberIdSearch")
	public String memberIdSearchGet() {
		return "member/memberIdSearch";
	}
	
	// 아이디 찾기
	@ResponseBody
	@PostMapping("/memberIdSearch")
	public List<MemberVO> memberIdSearchPost(Model model, String email) {
		return memberService.getmemberIdSearch(email);
	}
	
	// 회원 정보 수정폼보기
	@GetMapping("/memberUpdate")
	public String memberUpdateGet(Model model, String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		model.addAttribute("vo", vo);
		return "member/memberUpdate";
	}
	
	// 회원 정보 수정처리하기
	@PostMapping("/memberUpdate")
	public String memberUpdatePost(MultipartFile fName, MemberVO vo, HttpSession session) {
		String nickName = (String) session.getAttribute("sNickName");
		if(memberService.getMemberNickCheck(vo.getNickName()) != null && !nickName.equals(vo.getNickName())) {
			return "redirect:/message/nickCheckNo?mid="+vo.getMid();
		}
		
		// 회원 사진처리
		if(fName.getOriginalFilename() != null && !fName.getOriginalFilename().equals("")) {
			if(!vo.getPhoto().equals("noimage.jpg")) projectProvide.fileDelete(vo.getPhoto(), "member");
			vo.setPhoto(projectProvide.fileUpload(fName, vo.getMid(), "member"));
		}
		
		int res = memberService.setMemberUpdateOk(vo);
		if(res != 0) {
			session.setAttribute("sNickName", vo.getNickName());
			return "redirect:/message/memberUpdateOk?mid="+vo.getMid();
		}
		else return "redirect:/message/memberUpdateNo?mid="+vo.getMid();
	}

	// 회원 탈퇴 신청...
	@ResponseBody
	@PostMapping("/userDelete")
	public String userDeletePost(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		int res = memberService.setUserDelete(mid);
		
		if(res != 0) {
			session.invalidate();
			return "1";
		}
		else return "0";
	}

	// 회원 리스트보기
	@GetMapping("/memberList")
	public String memberListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="level", defaultValue = "99", required = false) int level
		) {
		List<MemberVO> vos = memberService.getMemberList(0, 100, level);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("level", level);
		
		return "member/memberList";
	}
	
	
}
