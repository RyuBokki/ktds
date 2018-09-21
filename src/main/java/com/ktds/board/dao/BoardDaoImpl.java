package com.ktds.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktds.board.vo.BoardSearchVO;
import com.ktds.board.vo.BoardVo;
import com.ktds.common.dao.support.BindData;
import com.ktds.member.vo.MemberVo;

@Repository
public class BoardDaoImpl implements BoardDao{

	// bean Container에 있는 객체를 autowired를 통해 가지고 옴
	
	// 상수는 interface에다 만듦
//	private final int INSERT_QUERY = 0;
//	private final int SELECT_QUERY = 1;
//	private final int DELETE_QUERY = 2;
	
	
	// 클래스 내에 인터페이스 만듦
	// 정석적인 방법
	private interface Query {
		int INSERT_QUERY = 0;
		int SELECT_QUERY = 1;
		int DELETE_QUERY = 2;
		int SELECT_ALL_QUERY = 3;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="boardQueries")
	private List<String> boardQueries;
	
	public int insertBoard(BoardVo boardVo) {
//		System.out.println("Call insertBoard();");
//		System.out.println(boardQueries.get(0));
//		return 0;
		
		//update는 transaction이 필요한 것들에만 사용
		return this.jdbcTemplate.update(
					this.boardQueries.get(Query.INSERT_QUERY) // insert query
					, boardVo.getSubject()
					, boardVo.getContent()
					, boardVo.getEmail()
					, boardVo.getOriginFileName()
					, boardVo.getFileName()
				);	
	}
	
	public int updateBoard(BoardVo boardVo) {
		
//		System.out.println("Call updateBoard();");
		return 0;
	}

	@Override
	public BoardVo selectOneBoard(int id) {
		// 한개만 가져오고 싶을때 queryForObject(sql, args, rowMapper)
		// args는 applicationContext에 있는 query의 binding parameter
		// type이 object배열
		
		/**
		 * 배열을 초기화 하는 방법
		 * 
		 * new Object[]{}
		 * 
		 * 굳이 여기서 Object라고 적은이유는
		 * queryForObject가 원하는 값이 Object임
		 */
		
		/**
		 * row mapper
		 * 형태가 Sql.java과 동일
		 * 
		 * 
		 * new RowMapper<BoardVo>(){}
		 * general 안의 값은 리턴 타입과 같아야함
		 */
		
		return this.jdbcTemplate.queryForObject(this.boardQueries.get(Query.SELECT_QUERY)
				, new Object[]{id}
		, new RowMapper<BoardVo>(){
			
			/**
			 * rowNum data row의 번호
			 */
			
			@Override
			public BoardVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//				BoardVo boardVo = new BoardVo();
//				boardVo.setId(rs.getInt("ID"));
//				boardVo.setSubject(rs.getString("SUBJECT"));
//				boardVo.setContent(rs.getString("CONTENT"));
//				boardVo.setEmail(rs.getString("EMAIL"));
//				boardVo.setCrtDt(rs.getString("CRT_DT"));
//				boardVo.setMdfyDt(rs.getString("MDFY_DT"));
//				boardVo.setFileName(rs.getString("FILE_NAME"));
//				boardVo.setOriginFileName(rs.getString("ORIGIN_FILE_NAME"));
//				
				
				MemberVo memberVo = BindData.bindData(rs, new MemberVo());
				BoardVo boardVo = BindData.bindData(rs, new BoardVo());
				// 가져온 memberVo을 setter로 이용해 가져옴
				boardVo.setMemberVo(memberVo);
				
				
				return boardVo;
			}});
	}

	@Override
	public int deleteOneBoard(int id) {
		return this.jdbcTemplate.update(
				this.boardQueries.get(Query.DELETE_QUERY)
				, id);
	}

	@Override
	public List<BoardVo> selectAllBoards(BoardSearchVO boardSearchVO) {
		
		//query list를 리턴
		return this.jdbcTemplate.query(this.boardQueries.get(Query.SELECT_ALL_QUERY)
				// BoardVo는 list의 generic
				, new RowMapper<BoardVo>(){

					@Override
					public BoardVo mapRow(ResultSet rs, int rowNum) throws SQLException {
						MemberVo memberVo = BindData.bindData(rs, new MemberVo());
						BoardVo boardVo = BindData.bindData(rs, new BoardVo());
						// 가져온 memberVo을 setter로 이용해 가져옴
						boardVo.setMemberVo(memberVo);
						
						return boardVo;
					}
				});
	}

	@Override
	public int selectAllBoardsCount(BoardSearchVO boardSearchVO) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
