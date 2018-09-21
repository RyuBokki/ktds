package com.ktds.member.dao;


import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktds.member.vo.MemberVo;

@Repository
public class MemberDaoImplMybatis extends SqlSessionDaoSupport
									implements MemberDao {
	
	private Logger logger = LoggerFactory.getLogger(MemberDaoImplMybatis.class);
	
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		logger.debug("Initiate MyBatis");
		super.setSqlSessionTemplate(sqlSessionTemplate);
	  }	
	
	@Override
	public int insertNewMember(MemberVo memberVo) {
		return getSqlSession().insert("MemberDao.insertNewMember", memberVo);
	}

	@Override
	// 한 개만 가져오겠다.
	public MemberVo selectOneMember(MemberVo memberVo) {
		return getSqlSession().selectOne("MemberDao.selectOneMember", memberVo);
	}

	@Override
	public int updatePoint(Map<String, Object> param) {
//		하나의 parameter만 전달가능
//		두개 이상하면 오류
//		
//		따라서 전달하기 위해 map사용
//		return getSqlSession().update("MemberDao.updatePoint", email, point );
		
		
//		이렇게 쓰는 것도 좋지 못함 -> 코드 한 줄 이상이 되므로
//		Map<String, Object> param = new HashMap<>();
//		param.put("email", email);
//		param.put("point", point);
//		
//		return getSqlSession().update("MemberDao.updatePoint", param );
		return getSqlSession().update("MemberDao.updatePoint", param );
	}

	@Override
	public int isBlockUser(String email) {
		return getSqlSession().selectOne("MemberDao.isBlockUser", email);
	}

	@Override
	public int unblockUser(String email) {
		return getSqlSession().update("MemberDao.unblockUser", email);
	}

	@Override
	public int iscreaseLoginFailCount(String email) {
		return getSqlSession().update("MemberDao.iscreaseLoginFailCount", email);
	}


}
