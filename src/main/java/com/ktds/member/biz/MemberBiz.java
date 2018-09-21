package com.ktds.member.biz;

public interface MemberBiz {
	
	// 원래 대로 하려면 서비스와 같은 interface
	// 하지만 시간이 걸리므로 update만
	
	public int updatePoint(String email, int point);
	
	
}
