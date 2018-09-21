package com.ktds.member.service;


import com.ktds.member.vo.MemberVo;

public interface MemberService {
	
	public boolean createNewMember(MemberVo memberVo);
	public MemberVo readOneMember(MemberVo memberVo);
	
	public boolean isBlockUser(String email);
	public boolean unblockUser(String email);
	
	public boolean iscreaseLoginFailCount(String email);
	
	
}
