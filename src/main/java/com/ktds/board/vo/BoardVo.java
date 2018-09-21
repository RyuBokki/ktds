package com.ktds.board.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.ktds.common.dao.support.Types;
import com.ktds.member.vo.MemberVo;
import com.ktds.reply.vo.ReplyVO;

// multifileResolver가 객체를 받아서 multipartFile로 돌려줌.
public class BoardVo {
	
	@Types
	private int id;
	
	@Types
	@NotEmpty(message = "제목은 필수 입력 값입니다.")
	private String subject;
	
	@Types
	@NotEmpty(message = "내용은 필수 입력 값입니다.")
	private String content;
	// 들어오는 COLUMN명은 B_EMAIL
	@Types(alias="B_EMAIL")
	private String email;
	@Types
	private String crtDt;
	@Types
	private String mdfyDt;
	@Types
	private String fileName;
	@Types
	private String originFileName;
	
	private MultipartFile file;
	
	// 조인되는 결과를 보여주고 싶을때
	// 게시물의 작성자를 보여주고 싶은데 얘는 memberVo에 있음.
	private MemberVo memberVo;
	
	private String token;
	
	// replyVo를 BoardVo안에 담기 위해
	private List<ReplyVO> replyList;
	
	public BoardVo() {
		this.fileName="";
		this.originFileName="";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCrtDt() {
		return crtDt;
	}
	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}
	public String getMdfyDt() {
		return mdfyDt;
	}
	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String orginFileName) {
		this.originFileName = orginFileName;
	}
	
	// file 업로더를 위한 getter와 setter
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	// memberVo의 getter와 setter
	public MemberVo getMemberVo() {
		return memberVo;
	}
	public void setMemberVo(MemberVo memberVo) {
		this.memberVo = memberVo;
	}
	
	// 이제 member의 이름과 이메일을 조회가능
	
	public List<ReplyVO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyVO> replyList) {
		this.replyList = replyList;
	}
	

	@Override
	public String toString() {
		// 출력을 하면 내부적으로 안에서 toString이 실행된다.
		String format = "BoardVo [Id : %d, Subject : %s, Content : %s, Email : %s, CrtDt : %s, MdfyDt : %s, originFileName : %s, MemberVo : %s]";
		return String.format(format
				, this.id
				, this.subject
				, this.content
				, this.email
				, this.crtDt
				, this.mdfyDt
				, this.originFileName
				, this.memberVo.toString());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	

}
