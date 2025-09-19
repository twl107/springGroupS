package com.spring.springGroupS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS.common.Pagination;
import com.spring.springGroupS.service.AdminService;
import com.spring.springGroupS.service.BoardService;
import com.spring.springGroupS.vo.Board2ReplyVO;
import com.spring.springGroupS.vo.BoardVO;
import com.spring.springGroupS.vo.ComplaintVO;
import com.spring.springGroupS.vo.PageVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	Pagination pagination;
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/boardList")
	public String boardListGet(Model model, PageVO pageVO) {
		pageVO.setSection("board");
		pageVO = pagination.pagination(pageVO);
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageVO.getPageSize(), pageVO.getSearch(), pageVO.getSearchString());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "board/boardList";
	}
	
	// 게시글 등록 폼보기
	@GetMapping("/boardInput")
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	// 게시글 DB에 등록하기
	@PostMapping("/boardInput")
	public String boardInputPost(BoardVO vo) {
		// 제목에 대하여 html태그를 사용할수 없도록처리....
		String title = vo.getTitle();
		title = title.replace("<", "&lt;");
		title = title.replace(">", "&gt;");
		vo.setTitle(title);		
		
		// 1.만약 content에 이미지를 등록하여 서버 파일시스템에 해당 그림이 저장되어 있다면, 저장된 그림(DB에 저장된 content필드)된그림만 board폴더에 따로 보관('/data/ckeditor'폴더에서 '/data/board'폴더로 복사)한다.
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
		
		// 2.이미지 작업(복사작업)을 모두 마치면, ckeditor폴더경로를 board폴더로 변경시킨다.
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		// 3.content안의 그림에 대한 정리가 모두 끝나면 변경된 내용을 vo에 담아서 DB에 저장한다.
		int res = boardService.setBoardInput(vo);
		
		if(res != 0) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	// 글 내용 보기(조회수증가:중복방지, '이전글/다음글')
	/*
	@SuppressWarnings("unchecked")
	@GetMapping("/boardContent")
	public String boardContentGet(Model model, HttpSession session, 
			int idx, String boardFlag,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		// 조회수 증가하기
		//boardService.setReadNumPlus(idx);
		
		// 게시글 조회수 증가 중복방지
	  List<String> contentReadNum = (List<String>) session.getAttribute("sContentIdx");
		if(contentReadNum == null) contentReadNum = new ArrayList<String>();
		String imsiContentReadNum = "board" + idx;
		if(!contentReadNum.contains(imsiContentReadNum)) {
			boardService.setReadNumPlus(idx);
			contentReadNum.add(imsiContentReadNum);
		}
		session.setAttribute("sContentIdx", contentReadNum);
		
		BoardVO vo = boardService.getBoardContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("boardFlag", boardFlag);
		
		// '이전글/다음글' 처리
		BoardVO preVO  = boardService.getPreNextSearch(idx, "preVO");
		BoardVO nextVO = boardService.getPreNextSearch(idx, "nextVO");
		model.addAttribute("preVO", preVO);
		model.addAttribute("nextVO", nextVO);
		
		// 댓글(대댓글) 가져오기
		List<Board2ReplyVO> replyVos = boardService.getBoardReply(idx);
		model.addAttribute("replyVos", replyVos);
		
		return "board/boardContent";
	}
	*/
	@SuppressWarnings("unchecked")
	@GetMapping("/boardContent")
	public String boardContentGet(Model model, HttpSession session, int idx, PageVO pageVO) {
		// 조회수 증가하기
		//boardService.setReadNumPlus(idx);
		
		// 게시글 조회수 증가 중복방지
	  List<String> contentReadNum = (List<String>) session.getAttribute("sContentIdx");
		if(contentReadNum == null) contentReadNum = new ArrayList<String>();
		String imsiContentReadNum = "board" + idx;
		if(!contentReadNum.contains(imsiContentReadNum)) {
			boardService.setReadNumPlus(idx);
			contentReadNum.add(imsiContentReadNum);
		}
		session.setAttribute("sContentIdx", contentReadNum);
		
		BoardVO vo = boardService.getBoardContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pageVO", pageVO);
		
		// '이전글/다음글' 처리
		BoardVO preVO  = boardService.getPreNextSearch(idx, "preVO");
		BoardVO nextVO = boardService.getPreNextSearch(idx, "nextVO");
		model.addAttribute("preVO", preVO);
		model.addAttribute("nextVO", nextVO);
		
		// 댓글(대댓글) 가져오기
		List<Board2ReplyVO> replyVos = boardService.getBoardReply(idx);
		model.addAttribute("replyVos", replyVos);
		
		return "board/boardContent";
	}
	
	// 좋아요 횟수 증가처리(중복 불허)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping("/boardGoodCheck")
	public int boardGoodCheckPost(int idx, HttpSession session) {
		List<String> contentReadNum = (List<String>) session.getAttribute("sContentIdx");
		if(contentReadNum == null) contentReadNum = new ArrayList<String>();
		String imsiContentReadNum = "boardGood" + idx;
		int res = 0;
		if(!contentReadNum.contains(imsiContentReadNum)) {
			boardService.setGoodReadNumPlus(idx);
			contentReadNum.add(imsiContentReadNum);
			res = 1;
		}
		session.setAttribute("sContentIdx", contentReadNum);
		
		return res;
	}
	
	// 게시글 수정 폼보기
	@GetMapping("/boardUpdate")
	public String boardUpdateGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		BoardVO vo = boardService.getBoardContent(idx);
		
		// 수정화면으로 들어갈때, 기존 원본파일에 그림파일이 존재한다면, 현재폴더(board)에서 그림파일만 ckeditor폴더로 복사한다.
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackup(vo.getContent());
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardUpdate";
	}
	
	// 게시글 수정처리하기
	/*
	@PostMapping("/boardUpdate")
	public String boardUpdatePost(Model model, BoardVO vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
			) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없다.(DB자료와 수정자료를 비교한다.)
		BoardVO origVO = boardService.getBoardContent(vo.getIdx());
		
		// content내용중에서 조금이라도 수정한 것이 있다면 사진에 대한 처리를 한다.
		if(!origVO.getContent().equals(vo.getContent())) {
			// 1. 기존 board폴더(origVO를 확인)에 그림파일이 존재했다면 원본 그림파일(origVO)삭제처리
			if(origVO.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVO.getContent());
			
			// content필드내용중 이미지 경로는 현재 board로 설정되어 있기에, board폴더를 ckeditor로 변경처리한다.
			// 왜냐하면, 처음 입력시 사진은 ckeditor폴더에 있어야 하고, 수정처리를 했던, 하지않았던 원본그림도 ckeditor폴더에 복사해 두었다.
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
			
			// 2. board폴더의 그림파일 삭제 완료후(그림파일이 존재시), 입력시 작업과 같은 작업(ckeditor폴더에서 board폴더로 복사)을 수행처리한다.
			if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
			
			// 3.이미지 작업(복사작업)을 모두 마치면, ckeditor폴더경로를 board폴더로 변경시킨다.
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		// 수정된 내용들을 DB에 업데이트 처리한다.
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		//if(res != 0) return "redirect:/message/boardUpdateOk?pag="+pag+"&pageSize="+pageSize;
		//else return "redirect:/message/boardUpdateNo?idx="+vo.getIdx()+"&pag="+pag+"&pageSize="+pageSize;
		if(res != 0) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	*/
	@PostMapping("/boardUpdate")
	public String boardUpdatePost(Model model, BoardVO vo, PageVO pageVO) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없다.(DB자료와 수정자료를 비교한다.)
		BoardVO origVO = boardService.getBoardContent(vo.getIdx());
		
		// content내용중에서 조금이라도 수정한 것이 있다면 사진에 대한 처리를 한다.
		if(!origVO.getContent().equals(vo.getContent())) {
			// 1. 기존 board폴더(origVO를 확인)에 그림파일이 존재했다면 원본 그림파일(origVO)삭제처리
			if(origVO.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVO.getContent());
			
			// content필드내용중 이미지 경로는 현재 board로 설정되어 있기에, board폴더를 ckeditor로 변경처리한다.
			// 왜냐하면, 처음 입력시 사진은 ckeditor폴더에 있어야 하고, 수정처리를 했던, 하지않았던 원본그림도 ckeditor폴더에 복사해 두었다.
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
			
			// 2. board폴더의 그림파일 삭제 완료후(그림파일이 존재시), 입력시 작업과 같은 작업(ckeditor폴더에서 board폴더로 복사)을 수행처리한다.
			if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
			
			// 3.이미지 작업(복사작업)을 모두 마치면, ckeditor폴더경로를 board폴더로 변경시킨다.
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		// 수정된 내용들을 DB에 업데이트 처리한다.
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("idx", vo.getIdx());
		//model.addAttribute("pageVO", pageVO);		// redirect는 객체는 온전하게 넘어가지 않는다. 즉, 아래처럼 풀어서 넘겨야 한다.
		model.addAttribute("pag", pageVO.getPag());
		model.addAttribute("pageSize", pageVO.getPageSize());
		model.addAttribute("search", pageVO.getSearch());
		model.addAttribute("searchString", pageVO.getSearchString());
		
		//if(res != 0) return "redirect:/message/boardUpdateOk?pag="+pag+"&pageSize="+pageSize;
		//else return "redirect:/message/boardUpdateNo?idx="+vo.getIdx()+"&pag="+pag+"&pageSize="+pageSize;
		if(res != 0) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	
	// 게시글 삭제 처리
	@GetMapping("/boardDelete")
	public String boardDeleteGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		// 게시글에 사진이 존재한다면 서버에 저장된 사진을 먼저 삭제처리한다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// 사진작업처리를 마치고 DB에 저장된 실제 정보레코드를 삭제처리한다.
		int res = boardService.setBoardDelete(idx);
		
		model.addAttribute("idx", idx);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		if(res != 0) return "redirect:/message/boardDeleteOk";
		else return "redirect:/message/boardDeleteNo";
	}
	
	// 부모댓글 입력처리(원본글에 대한 댓글) - 일반댓글창에서 글올린경우만 이곳을 수행처리한다.
	@ResponseBody
	@PostMapping("/boardReplyParentInput")
	public int boardReplyParentInputPost(Board2ReplyVO replyVO) {
		// 부모댓글은 ref는 원본글의 idx(현재 별다른용도로 사용하지 않음)-확장고려, re_step=1, re_order=1, 단, 부모댓글이 아닌 '대댓글인경우'는 마지막 부모댓글의 re_step+1, re_order+1처리

		// 원본글의 댓글(부모댓글)이 존재하는지 알아보고 있다면, 가장 나중에 올린 원본글의 댓글을 1개만 가져온다. - 새로 올리는 원본글의 댓글의 re_order을 결정하기위함이다.
		Board2ReplyVO replyParentVO = boardService.getBoardParentReplyCheck(replyVO.getBoard2Idx());
		replyVO.setRef(replyVO.getBoard2Idx());	// ref값은 원본글의 고유번호를 넣었다.(여기서는 사용하지 않았지만, 확장성을 위해서 만들어두었다)
		
		// 원본글의 댓글은 모두 're_step=1' 로 셋팅한다.
		replyVO.setRe_step(1);
		
		// 처음 올라가는 글이라면 자신의 re_order=1 로 셋팅, 그렇지 않으면 're_order'는 기존 원본글의 모든 댓글중 가장 마지막댓글의 're_order+1'로 지정한다.
		if(replyParentVO == null) replyVO.setRe_order(1);
		else replyVO.setRe_order(replyParentVO.getRe_order()+1);
		
		return boardService.setBoardReplyInput(replyVO);
	}
	
	// 대댓글 입력처리(부모댓글에 대한 댓글) - 원본글 댓글에대한 답변글이나, 기타 대댓글, 대대댓글.. 들을 입력시에 모두 이곳을 통과한다.
	@ResponseBody
	@PostMapping("/boardReplyInput")
	public int boardReplyInputPost(Board2ReplyVO replyVO) {
		// 대댓글(답변글)처리 ①자신의 re_step은 부모re_step+1 시켜주고, 
		// ②부모댓글 re_order값보다 큰re_order은 re_order+1시켜주고,
		// ③자신의 re_order는 부모re_order+1처리한다.
		replyVO.setRe_step(replyVO.getRe_step()+1);	// 1번처리
		boardService.setReplyOrderUpdate(replyVO.getBoard2Idx(), replyVO.getRe_order());	// 2번처리
		replyVO.setRe_order(replyVO.getRe_order() + 1);	// 3번처리
		
		int res = boardService.setBoardReplyInput(replyVO);	// 모든 작업이 처리된 replyVO를 DB에 저장한다.
		
		return res;
	}
	
	// 댓글 삭제처리
	@ResponseBody
	@PostMapping("/boardReplyDelete")
	public int boardReplyDeletePost(int idx) {
		return boardService.setBoardReplyDelete(idx);
	}
	
	// 검색기처리
	@GetMapping("/boardSearchList")
	public String boardSearchListGet(Model model, PageVO pageVO) {
		pageVO.setSection("board");
		pageVO = pagination.pagination(pageVO);
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageVO.getPageSize(), pageVO.getSearch(), pageVO.getSearchString());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "board/boardSearchList";
	}
	
	// 댓글 수정처리
	@ResponseBody
	@PostMapping("/boardReplyUpdateOk")
	public int boardReplyUpdateOkPost(Board2ReplyVO vo) {
		return boardService.setBoardReplyUpdateOk(vo);
	}
	
	// 신고글 처리하기
	@ResponseBody
	@PostMapping("/boardComplaintInput")
	public int boardComplaintInputPost(ComplaintVO vo) {
		int res = 0;
		res = adminService.setBoardComplaintInput(vo);
		if(res != 0) adminService.setBoardTableComplaintOk(vo.getPartIdx());
		return res;
	}
	
}
