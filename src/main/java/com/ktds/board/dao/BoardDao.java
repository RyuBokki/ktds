package com.ktds.board.dao;

import java.util.List;

import com.ktds.board.vo.BoardSearchVO;
import com.ktds.board.vo.BoardVo;

public interface BoardDao {

	public int insertBoard(BoardVo boardVo);
	public int updateBoard(BoardVo boardVo);
	
	// int id는 사용자가 요청한 id
	public BoardVo selectOneBoard( int id );
	
	public int deleteOneBoard(int id);
	
	public int selectAllBoardsCount(BoardSearchVO boardSearchVO);
	public List<BoardVo> selectAllBoards(BoardSearchVO boardSearchVO);
	
	
}
