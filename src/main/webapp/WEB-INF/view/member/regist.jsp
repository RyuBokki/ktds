<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/HelloSpring/js/jquery-3.3.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$().ready(function() {
		
		$("#email").keyup(function() {
			// AJAX 요청
			// 요청 파라미터는 항상 객체 리터럴 방식
			
			// email이라는 key로 현재까지 적은 val을 보내주겟다.
			$.post("/HelloSpring/member/check/duplicate" // URL
					
					, { // REQUEST parameter
				"email":$(this).val()
			}
			// json으로 받았지만 실제 데이터는 객체 리터럴, 객체 리터럴 쓰는 것처럼 사용하면 됨
			, function(response){ // Response Call back
				
				if ( response.duplicated ) {
					$("#email-error").slideDown(100);
				}
				else{
					$("#email-error").slideUp(100);
					
				}
				
			})
		})
		
		$("#password").keyup(function() {
		$.post("/HelloSpring1/member/check/passwordPolicy"
	               
	            ,{
	            
	               "password":$(this).val()
	            
	            }
	            , function(response){
	               	                  
	                  if ( !response.violate ) {
	                     alert("패스워드 정책을 위반하였습니다.");
	                  }
	                  else {
	                     alert("정상적으로 회원가입 되었습니다.");
	                  }
	            }
	         )
         })

	})
</script>
</head>
<body>
   <h1>회원가입</h1>
   <hr>
   <form:form modelAttribute="memberVo" 
   			  method="post" 
   			  action="/HelloSpring/member/regist"
   			  autocomplete="false">
   	  <div	class="erros">
   	  	<ul>
   	  		<li><form:errors path="email"/></li>
   	  		<li><form:errors path="name"/></li>
   	  		<li><form:errors path="password"/></li>
   	  	</ul>
   	  </div>
      <div>
         <input type="email" name="email" id="email" placeholder="이메일 주소를 입력하세요." value = "${memberVo.email}"/>
         <div id="email-error" style="display:none;">
         	이 이메일은 사용할 수 없습니다.
	     </div>
      </div>
      <div>
         <input type="text" name="name" placeholder="이름을 입력하세요." value = "${memberVo.name}"/>
      </div>
      <div>
         <input type="password" name="password" placeholder="비밀번호를 입력하세요" value = "${memberVo.password}"/>
      </div>
      <div>
         <input type="submit" value="등록" />
      </div>
   </form:form>
</body>
</html>