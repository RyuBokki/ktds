<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/WEB-INF/view/common/header.jsp"/>
	<!-- action에게 post방식으로 요청 -->
	<form:form	modelAttribute = "boardVo"
				method = "post" 
				action="/HelloSpring/board/write" 
				enctype = "multipart/form-data">
				<!-- 교재에 없는 부분 error를 나타내기 위해 만들었음 여기 클래스는 의미없음 단순 style을 위해 -->
				<div	class = "errors">
						<ul>
							<li><form:errors path = "subject"/></li>
							<li><form:errors path = "content"/></li>
						</ul>
				
				</div>
				
				<div>
					<input type="text" name="subject" placeholder = "제목을 입력하세요." value = "${boardVo.subject}"/>
				</div>
				<div>
					<textarea name="content" placeholder = "내용을 입력하세요." value = "${boardVo.content}"></textarea>
				</div>
				<div>
					<input type = "file" name = "file" placeholder = "파일을 선택하세요."/>
				</div>
				<%-- <div>
					<input	type="email" 
							name="email" 
							placeholder = "이메일을 입력하세요." 
							value= "${sessionScope._USER_.email}"/>
							
				</div> --%>
				<div>
					<input type="submit" value = "등록" />
					<a href = "/HelloSpring/board/list">목록</a>
				</div>
	</form:form>
</body>
</html>