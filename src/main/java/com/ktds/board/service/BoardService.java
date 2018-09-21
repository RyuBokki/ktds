package com.ktds.board.service;

import com.ktds.board.vo.BoardSearchVO;
import com.ktds.board.vo.BoardVo;
import com.ktds.member.vo.MemberVo;

import io.github.seccoding.web.pager.explorer.PageExplorer;

public interface BoardService {
	

	
	public boolean createBoard(BoardVo boardVo, MemberVo memberVo);
	public boolean updateBoard(BoardVo boardVo);
	
	public BoardVo readOneBoard(int id, MemberVo memberVo);
	
	// file을 download할 때 point를 뺏기지 않게 하려고 overloading
	public BoardVo readOneBoard(int id);
	
	public boolean deleteBoard(int id);
	
	public PageExplorer readAllBoards(BoardSearchVO boardSearchVO);
}
