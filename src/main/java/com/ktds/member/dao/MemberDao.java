package com.ktds.member.dao;

import java.util.Map;

import com.ktds.member.vo.MemberVo;

public interface MemberDao {
	
	public int insertNewMember(MemberVo memberVo);
	public MemberVo selectOneMember(MemberVo memberVo);
	
	public int updatePoint(Map<String, Object> param);
	
	public int isBlockUser(String email);
	public int unblockUser(String email);
	
	public int iscreaseLoginFailCount(String email);
}
