package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.Board2ReplyVO;
import com.spring.springGroupS.vo.BoardVO;

public interface BoardService {

	List<BoardVO> getBoardList(int startIndexNo, int pageSize, String search, String searchString);

	int getTotRecCnt(String search, String searchString);

	int setBoardInput(BoardVO vo);

	BoardVO getBoardContent(int idx);

	void imgCheck(String content);

	void setReadNumPlus(int idx);

	void setGoodReadNumPlus(int idx);

	BoardVO getPreNextSearch(int idx, String str);

	void imgBackup(String content);

	int setBoardUpdate(BoardVO vo);

	void imgDelete(String content);

	int setBoardDelete(int idx);

	List<Board2ReplyVO> getBoardReply(int idx);

	Board2ReplyVO getBoardParentReplyCheck(int board2Idx);

	int setBoardReplyInput(Board2ReplyVO replyVO);

	void setReplyOrderUpdate(int board2Idx, int re_order);

	int setBoardReplyDelete(int idx);

	int setBoardReplyUpdateOk(Board2ReplyVO vo);

}
