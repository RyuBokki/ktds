<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel ="stylesheet", type="text/css", href="/HelloSpring/css/list.css" />		
	<!-- javaScript -->
	<script type = "text/javascript">
		var message = "${param.message}";
		if ( message != "" ) {
			alert(message);
		}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/view/common/header.jsp"/>
	<div id = "wrapper">
		<div id = "headerWrapper">
			<div class = "number header box">글 번호</div><!--
			  --><div class = "subject header box">제목</div><!-- 
			 --><div class = "writer header box">작성자</div><!-- 
			 --><div class = "create-date header box">작성일</div>
		</div>
		<c:choose>
			<c:when test="${not empty boardVoList}">
				<!-- items는 list로 전달받은 EL -->
				<c:forEach items = "${boardVoList}" var = "board">
				<div class = "contentWrapper">
						<div class = "number  box">${board.id}</div><!--
					 --><div class = "subject  box">
					 	<a href = "/HelloSpring/board/detail/${board.id}">
					 	${board.subject}
					 	</a>
					 </div><!-- 
					 --><div class = "writer  box">${board.memberVo.name}</div><!-- 
					 --><div class = "create-date  box">${board.crtDt}</div>
				</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div id = "no-articles">
				등록된 게시글이 없습니다.
				</div>
			</c:otherwise>
		</c:choose>
		<div class="padded">
			<!-- 항상 id는 searchForm -->
			<!-- method지정 따로 할 필요없음 movePage가 자동으로 지정해줌. -->
			<!-- form element가 하나 밖에 없을 때 enter를 누르면 submit이 됨 -->
			<!-- submit을 클릭할 때마다 movepage라는 기능이 동작됨. -->
			<form id="searchForm" onsubmit="javascript:movePage(0);">
			
				${pagenation}
				<div>
					<input type="text" name="searchKeyword" value="${boardSearchVO.searchKeyword}">
					<a href="/HelloSpring/board/list/init">검색초기화</a>
				</div> 
			</form>
		</div>
		<div class = "padded">
			<a href="/HelloSpring/board/write">글 작성</a>
		</div>
	</div>
</body>
</html>