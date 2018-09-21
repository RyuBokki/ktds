package com.ktds.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ktds.member.biz.MemberBiz;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVo;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	@Qualifier("memberDaoImplMybatis")
	private MemberDao memberDao;
	
	// memberBiz 호출, 원래 이렇게 하면 안됨. 원래는 biz에서 repository 호출
	// memberVo.update를 memberBiz.update로 바꿔주었음
	@Autowired
	private MemberBiz memberBiz;

	@Override
	public boolean createNewMember(MemberVo memberVo) {
		return this.memberDao.insertNewMember(memberVo) > 0;
	}

	// login
	@Override
	public MemberVo readOneMember(MemberVo memberVo) {
		
		MemberVo loginMemberVo = this.memberDao.selectOneMember(memberVo);
		
		if ( loginMemberVo != null ) {
			// db에 포인트 주기
			
// member의 point랑 수정하는 point는 다름
// 			MemberBizImpl로 이동
//			Map<String, Object> param = new HashMap<>();
//			param.put("email", memberVo.getEmail());
//			param.put("point", +2);
//			return memberDao.updatePoint(param);

// 			억지스러운 점을 수정하는 것
			this.memberBiz.updatePoint(memberVo.getEmail(), +2);
			
			// 가지고 온 객체의 포인트를 올려줘서 db와 맞춰주기
			int point = loginMemberVo.getPoint();
			point += 2;
			loginMemberVo.setPoint(point);
		}
		
		return this.memberDao.selectOneMember(memberVo);
	}

	@Override
	public boolean isBlockUser(String email) {
		return this.memberDao.isBlockUser(email) >= 3;
	}

	@Override
	public boolean unblockUser(String email) {
		return this.memberDao.unblockUser(email) > 0;
	}

	@Override
	public boolean iscreaseLoginFailCount(String email) {
		return this.memberDao.iscreaseLoginFailCount(email)>0;
	}
	
	
	
}
