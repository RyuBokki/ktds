<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/WEB-INF/view/common/header.jsp"/>
	<h1>
		${boardVo.subject}
		<span style = "font-size: 12pt;">${boardVo.crtDt}</span>
	</h1>
	<h3>${boardVo.memberVo.name}</h3>
	
	<c:if test="${not empty boardVo.originFileName}">
		<p>
			<a href = "/HelloSpring/board/download/${boardVo.id}">
			${boardVo.originFileName}
			</a>
		</p>
	</c:if>
	<div>
		${boardVo.content}
	</div>
	
	<div>
		<a href = "/HelloSpring/board/delete/${boardVo.id}">삭제</a>
		<a href = "/HelloSpring/board/list/">목록</a>
	</div>
	
	<hr/>
	<div>
		<c:forEach items="${boardVo.replyList}" var="reply">
			<div style="margin-left: ${(reply.level - 1 ) * 30}px;">
				<div>${reply.memberVo.name} ${reply.memberVo.email}</div>
				<div>${reply.crtDt}</div>
				<div>${reply.reply}</div>
			</div>
		</c:forEach>
	</div>
	
	
	<form action="/HelloSpring/reply/write" method="post">
		<input type="hidden" name="boardId" value="${boardVo.id}">
		<input type="hidden" name="parentReplyId" value="0"/>
		<textarea name="reply"></textarea>
		<input type="submit" value="등록">
	</form>
	
</body>
</html>