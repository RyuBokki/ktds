package com.ktds.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktds.common.dao.support.BindData;
import com.ktds.member.vo.MemberVo;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	
	private interface Query {
		int REGIST_QUERY = 0;
		int LOGIN_QUERY = 1;
		int POINT_UPDATE_QUERY = 2;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="memberQueries")
	private List<String> memberQueries;
	
	
	

	@Override
	public int insertNewMember(MemberVo memberVo) {
		return this.jdbcTemplate.update(
				this.memberQueries.get(Query.REGIST_QUERY) // insert query
				, memberVo.getEmail()
				, memberVo.getName()
				, memberVo.getPassword()
			);	
	}

	@Override
	public MemberVo selectOneMember(MemberVo memberVo) {
		return jdbcTemplate.queryForObject(
	            this.memberQueries.get(Query.LOGIN_QUERY)
	            , new Object[] {memberVo.getEmail(),  memberVo.getPassword()}
	            , new RowMapper<MemberVo>() {

	               @Override
	               public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
	                  return BindData.bindData(rs, new MemberVo());
	               }
	            });
	}

	@Override
	public int updatePoint( Map<String, Object> param) {
		// login +2
		// read -2
		// write +10
		// 파일첨부하고 write +20
		return jdbcTemplate.update(this.memberQueries.get(Query.POINT_UPDATE_QUERY)
				, param.get("point")
				, param.get("email"));
	}

	@Override
	public int isBlockUser(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unblockUser(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int iscreaseLoginFailCount(String email) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
