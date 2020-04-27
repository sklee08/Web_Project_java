<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script>
	function btn(){
		alert("삭제되었습니다.");
	}

</script>
<title>Insert title here</title>
</head>
<body>
	<h1>비밀번호 확인</h1>
	<c:set var="root" value="${pageContext.request.contextPath }"></c:set>
	<c:choose>
		<c:when test="${userinfo eq null }">
			<!-- null 인 경우 -->
			<a href="<%=request.getContextPath() %>/user.do?act=mvfindpw"> 잘못된 정보입니다. 다시 입력하세요. </a>
		</c:when>
		<c:otherwise>
			<table border='1'>
	<tr><th>ID</th><th>PW</th><th>이름</th><th>주소</th><th>전화번호</th></tr>
		<tr>
			<td>${userinfo.id }</td>
			<td>${userinfo.pw }</td>
			<td>${userinfo.name }</td>
			<td>${userinfo.address }</td>
			<td>${userinfo.phone }</td>
		</tr>				
	</table>
	<a href="<%=request.getContextPath() %>/view/main.jsp">메인화면</a>
	<br>
		</c:otherwise>
	</c:choose>
	
</body>
</html>