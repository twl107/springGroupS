package com.spring.springGroupS.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springGroupS.vo.PageVO;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model, HttpSession session, PageVO pageVO,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx
			//@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			//@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		
		if(msgFlag.equals("hoewonInputOk")) {
			model.addAttribute("message", mid + "님 회원에 정상적으로 가입되었습니다.");
			model.addAttribute("url", "/study1/mapping/test35?mid="+mid);
		}
		else if(msgFlag.equals("hoewonInputNo")) {
			model.addAttribute("message", "회원 가입 실패");
			model.addAttribute("url", "/study1/mapping/menu");
		}
		else if(msgFlag.equals("userInputOk")) {
			model.addAttribute("message", "회원 가입 성공");
			model.addAttribute("url", "/user2/userList");
		}
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("message", "회원 가입 실패~~");
			model.addAttribute("url", "/user2/userInput");
		}
		else if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("message", "회원 삭제 성공");
			model.addAttribute("url", "/user2/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("message", "회원 삭제 실패~~");
			model.addAttribute("url", "/user2/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("message", "회원 정보를 수정하였습니다.");
			model.addAttribute("url", "/user2/userUpdate?idx="+idx);
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("message", "회원 정보 수정 실패~~");
			model.addAttribute("url", "/user2/userUpdate?idx="+idx);
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("message", "방명록 글등록 실패~~");
			model.addAttribute("url", "/guest/guestInput");
		}
		else if(msgFlag.equals("adminOk")) {
			model.addAttribute("message", "관리자 인증 성공");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("message", "관리자 인증 실패~~");
			model.addAttribute("url", "/guest/admin");
		}
		else if(msgFlag.equals("adminOut")) {
			model.addAttribute("message", "관리자 로그아웃");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("message", "방명록 게시글이 삭제되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("message", "방명록 게시글 삭제실패~~");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("message", "회원에 가입되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("message", "회원 가입 실패~~");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("message", mid+"님 로그인 되셨습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("message", "로그인 실패~~");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("message", mid + "님 로그아웃 되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("message", "메일이 전송되었습니다.");
			model.addAttribute("url", "/study1/mail/mailForm");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("message", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/study1/fileUpload/fileUploadForm");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("message", "파일 업로드 실패~~");
			model.addAttribute("url", "/study1/fileUpload/fileUploadForm");
		}
		else if(msgFlag.equals("multiFileUploadOk")) {
			model.addAttribute("message", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/study1/fileUpload/multiFileUpload");
		}
		else if(msgFlag.equals("multiFileUploadNo")) {
			model.addAttribute("message", "파일 업로드 실패~~");
			model.addAttribute("url", "/study1/fileUpload/multiFileUpload");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("message", "이미 사용중인 아이디가 있습니다.\\n아이디를 확인후 다시 회원가입하세요.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("nickNameCheckNo")) {
			model.addAttribute("message", "이미 사용중인 닉네임이 있습니다.\\n닉네임을 확인후 다시 회원가입하세요.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("message", "이미 사용중인 닉네임입니다.\\n닉네임을 확인하세요.");
			model.addAttribute("url", "/member/memberUpdate?mid="+mid);
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("message", "회원에 가입되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("message", "회원 가입 실패~~");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("loginError")) {
			model.addAttribute("message", "로그인후 사용하세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("levelNo")) {
			model.addAttribute("message", "회원 등급을 확인하세요.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("passwordChangeOk")) {
			session.invalidate();
			model.addAttribute("message", "비밀번호를 변경했습니다. 다시 로그인후 사용하세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("passwordChangeNo")) {
			model.addAttribute("message", "비밀번호 변경 실패~~");
			model.addAttribute("url", "/member/memberPwdCheck");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("message", "회원 정보를 수정하였습니다.");
			model.addAttribute("url", "/member/memberUpdate?mid="+mid);
		}
		else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("message", "회원 정보 수정실패~~");
			model.addAttribute("url", "/member/memberUpdate?mid="+mid);
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "게시글이 등록되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("message", "게시글 등록 실패~~");
			model.addAttribute("url", "/board/boardInput");
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("message", "게시글을 수정 하였습니다.");
			if(pageVO.getSearch() == null) model.addAttribute("url", "/board/boardList?pag="+pageVO.getPag()+"&pageSize="+pageVO.getPageSize());
			else model.addAttribute("url", "/board/boardSearchList?pag="+pageVO.getPag()+"&pageSize="+pageVO.getPageSize()+"&search="+pageVO.getSearch()+"&searchString="+pageVO.getSearchString());
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("message", "게시글 수정 실패~~");
			model.addAttribute("url", "/board/boardUpdate?idx="+idx+"&pag="+pageVO.getPag()+"&pageSize="+pageVO.getPageSize()+"&search="+pageVO.getSearch()+"&searchString="+pageVO.getSearchString());
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("message", "게시글을 삭제 하였습니다.");
			model.addAttribute("url", "/board/boardList?pag="+pageVO.getPag()+"&pageSize="+pageVO.getPageSize());
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("message", "게시글 삭제 실패~~");
			model.addAttribute("url", "/board/boardContent?idx="+idx+"&pag="+pageVO.getPag()+"&pageSize="+pageVO.getPageSize());
		}
		else if(msgFlag.equals("pdsInputOk")) {
			model.addAttribute("message", "자료실에 자료가 등록되었습니다.");
			model.addAttribute("url", "/pds/pdsList");
		}
		else if(msgFlag.equals("pdsInputNo")) {
			model.addAttribute("message", "자료실에 자료 등록 실패~~");
			model.addAttribute("url", "/pds/pdsInput");
		}
		else if(msgFlag.equals("transactionUserInputOk")) {
			model.addAttribute("message", "회원 가입 완료...");
			model.addAttribute("url", "/study2/transaction/transactionForm");
		}

		
		return "include/message";
	}
	
}
