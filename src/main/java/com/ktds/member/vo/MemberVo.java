package com.ktds.member.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.ktds.common.dao.support.Types;

import validator.MemberValidator;

public class MemberVo {
	
	// 컬럼의 내용을 그대로 가져오겠다.
	@Types(alias="M_EMAIL")
	@NotEmpty(message = "이메일은 필수 입력 값입니다."
	// validation class
			, groups= {MemberValidator.Regist.class, MemberValidator.Login.class})
	@Email(message = "이메일 형식으로 작성해주세요.")
	private String email;
	
	@Types
	@NotEmpty(message = "이름은 필수 입력 값입니다."
			, groups= {MemberValidator.Regist.class})
	private String name;
	
	@Types
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다."
			, groups= {MemberValidator.Regist.class, MemberValidator.Login.class})
	// 해커에게 경우의 수를 더 많이 주기 위해서(login 할 때 말해주지 않음)
	@Length(min=10, max=20, message = "비밀번호는 10~20글자 사이로 입력해 주세요."
			, groups= {MemberValidator.Regist.class})
	private String password;
	
	private int loginFailCount;
	
	private String latestLogin;
	
	@Types
	private int point;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public String toString() {
		// 출력을 하면 내부적으로 안에서 toString이 실행된다.
		String format = "MemberVo [Email : %s, Name : %s, Password : %s, Point : %d]";
		return String.format(format
				, this.email
				, this.name
				, this.password
				, this.point);
	}

	public int getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public String getLatestLogin() {
		return latestLogin;
	}

	public void setLatestLogin(String latestLogin) {
		this.latestLogin = latestLogin;
	}
	
}
