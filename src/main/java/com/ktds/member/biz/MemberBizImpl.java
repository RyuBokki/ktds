package com.ktds.member.biz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ktds.member.dao.MemberDao;

// Service, Controller 쓸 수 없을 때 Component --> Controller, Service, Repository의 부모
@Component
public class MemberBizImpl implements MemberBiz {
	
	@Autowired
	@Qualifier("memberDaoImplMybatis")
	private MemberDao memberDao;
	
	@Override
	public int updatePoint(String email, int point) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("email", email);
		param.put("point", point);

// 나중에 고침		
//		return memberDao.updatePoint(param);
		return memberDao.updatePoint(param);
	}

}
