package com.ktds.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktds.board.vo.BoardSearchVO;
import com.ktds.board.vo.BoardVo;

@Repository
public class BoardDaoImplMyBatis extends SqlSessionDaoSupport implements BoardDao{
	
	private Logger logger = LoggerFactory.getLogger(BoardDaoImplMyBatis.class);
	
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		logger.debug("Initiate MyBatis");
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	
	@Override
	public int insertBoard(BoardVo boardVo) {
		return getSqlSession().insert("BoardDao.insertBoard", boardVo);
	}

	@Override
	public int updateBoard(BoardVo boardVo) {
		return getSqlSession().update("BoardDao.updateBoard", boardVo);
	}

	@Override
	public BoardVo selectOneBoard(int id) {
		return getSqlSession().selectOne("BoardDao.selectOneBoard", id);
	}

	@Override
	public int deleteOneBoard(int id) {
		return getSqlSession().delete("BoardDao.deleteOneBoard", id);
	}

	@Override
	public List<BoardVo> selectAllBoards(BoardSearchVO boardSearchVO) {
		return getSqlSession().selectList("BoardDao.selectAllBoards", boardSearchVO);
	}


	@Override
	public int selectAllBoardsCount(BoardSearchVO boardSearchVO) {
		return getSqlSession().selectOne("BoardDao.selectAllBoardsCount", boardSearchVO);
	}

}
