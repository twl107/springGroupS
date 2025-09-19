package com.spring.springGroupS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.springGroupS.dao.BoardDAO;
import com.spring.springGroupS.vo.Board2ReplyVO;
import com.spring.springGroupS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize, String search, String searchString) {
		return boardDAO.getBoardList(startIndexNo, pageSize, search, searchString);
	}

	@Override
	public int getTotRecCnt(String search, String searchString) {
		return boardDAO.getTotRecCnt(search, searchString);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public void imgCheck(String content) {
		// ckeditor폴더의 그림을 board폴더로 복사처리...
		//             0         1         2         3         4         5
		//             012345678901234567890123456789012345678901234567890
		// <img alt="" src="/springGroupS/data/ckeditor/250916121142_4.jpg" style="height:402px; width:600px" />
		// <img alt="" src="/springGroupS/data/board/250916121142_4.jpg" style="height:402px; width:600px" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 33;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "ckeditor/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	private void fileCopyCheck(String origFilePath, String copyFilePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(origFilePath));
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			byte[] bytes = new byte[2048];
			int cnt = 0;
			while((cnt = fis.read(bytes)) != -1) {
				fos.write(bytes, 0, cnt);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setReadNumPlus(int idx) {
		boardDAO.setReadNumPlus(idx);
	}

	@Override
	public void setGoodReadNumPlus(int idx) {
		boardDAO.setGoodReadNumPlus(idx);
	}

	@Override
	public BoardVO getPreNextSearch(int idx, String str) {
		return boardDAO.getPreNextSearch(idx, str);
	}

	@Override
	public void imgBackup(String content) {
	// ckeditor폴더의 그림을 board폴더로 복사처리...
		//             0         1         2         3         4         5
		//             012345678901234567890123456789012345678901234567890
		// <img alt="" src="/springGroupS/data/board/250916121142_4.jpg" style="height:402px; width:600px" />
	  // <img alt="" src="/springGroupS/data/ckeditor/250916121142_4.jpg" style="height:402px; width:600px" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 30;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			String copyFilePath = realPath + "ckeditor/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	@Override
	public int setBoardUpdate(BoardVO vo) {
		return boardDAO.setBoardUpdate(vo);
	}

	@Override
	public void imgDelete(String content) {
		//             0         1         2         3         4         5
		//             012345678901234567890123456789012345678901234567890
		// <img alt="" src="/springGroupS/data/board/250916121142_4.jpg" style="height:402px; width:600px" />
		// <img alt="" src="/springGroupS/data/ckeditor/250916121142_4.jpg" style="height:402px; width:600px" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 30;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			
			fileDelete(origFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 파일 삭제처리
	private void fileDelete(String origFilePath) {
		File delFile = new File(origFilePath);
		if(delFile.exists()) delFile.delete();
	}

	@Override
	public int setBoardDelete(int idx) {
		return boardDAO.setBoardDelete(idx);
	}

	@Override
	public List<Board2ReplyVO> getBoardReply(int idx) {
		return boardDAO.getBoardReply(idx);
	}

	@Override
	public Board2ReplyVO getBoardParentReplyCheck(int board2Idx) {
		return boardDAO.getBoardParentReplyCheck(board2Idx);
	}

	@Override
	public int setBoardReplyInput(Board2ReplyVO replyVO) {
		return boardDAO.setBoardReplyInput(replyVO);
	}

	@Override
	public void setReplyOrderUpdate(int board2Idx, int re_order) {
		boardDAO.setReplyOrderUpdate(board2Idx, re_order);
	}

	@Override
	public int setBoardReplyDelete(int idx) {
		return boardDAO.setBoardReplyDelete(idx);
	}

	@Override
	public int setBoardReplyUpdateOk(Board2ReplyVO vo) {
		return boardDAO.setBoardReplyUpdateOk(vo);
	}
	
}
