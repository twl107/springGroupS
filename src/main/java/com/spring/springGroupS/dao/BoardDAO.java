package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.Board2ReplyVO;
import com.spring.springGroupS.vo.BoardVO;

public interface BoardDAO {

	List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchString") String searchString);

	int getTotRecCnt(@Param("search") String search, @Param("searchString") String searchString);

	int setBoardInput(@Param("vo") BoardVO vo);

	BoardVO getBoardContent(@Param("idx") int idx);

	void setReadNumPlus(@Param("idx") int idx);

	void setGoodReadNumPlus(@Param("idx") int idx);

	BoardVO getPreNextSearch(@Param("idx") int idx, @Param("str") String str);

	int setBoardUpdate(@Param("vo") BoardVO vo);

	int setBoardDelete(@Param("idx") int idx);

	List<Board2ReplyVO> getBoardReply(@Param("idx") int idx);

	Board2ReplyVO getBoardParentReplyCheck(@Param("board2Idx") int board2Idx);

	int setBoardReplyInput(@Param("replyVO") Board2ReplyVO replyVO);

	void setReplyOrderUpdate(@Param("board2Idx") int board2Idx, @Param("re_order") int re_order);

	int setBoardReplyDelete(@Param("idx") int idx);

	int setBoardReplyUpdateOk(@Param("vo") Board2ReplyVO vo);

}
