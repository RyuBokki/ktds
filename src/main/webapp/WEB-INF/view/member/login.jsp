<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- javaScript -->
	<script src="/WEB-INF/static/jquery-3.3.1.min.js"></script>
	<script type = "text/javascript">
		var message = "${param.message}";
		if ( message != "" ) {
			alert(message);
		}
		
		$(document).ready.(function(){
			
			
			
		})
	</script>
</head>
<body>
   <h1>로그인</h1>
   <hr>
   <form:form modelAttribute="memberVo" method="post" action="/HelloSpring/member/login">
   	  <div>
   	  	<ul>
   	  		<li><form:errors path="email"/></li>
   	  		<li><form:errors path="password"/></li>
   	  	</ul>
   	  </div>
      <div>
         <input type="email" name="email" placeholder="이메일 주소를 입력하세요." value = "${memberVo.email}"/>
         
      </div>
      <div>
         <input type="password" name="password" placeholder="비밀번호를 입력하세요" value = "${memberVo.password}"/>
      </div>
      <div>
         <input type="submit" id="submit-button" value="로그인" />
      </div>
   </form:form>
</body>
</html>