<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script>
	function btn(){
		alert("수정되었습니다");
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<h1>회원 정보 수정</h1>
	<c:set var="root" value="${pageContext.request.contextPath }"></c:set>
	<form action="${root }/user.do" method="post">
		<input type= "hidden" name="act" value="update">
		<input type= "text" name="id" value="${userinfo.id }" readonly="readonly"><br>
		<input type= "text" name="pass" value="${userinfo.pw }" required="required"><br>
		<input type= "text" name="name" value="${userinfo.name }" required="required"><br>
		<input type= "text" name="address" value="${userinfo.address }" required="required"><br>
		<input type= "text" name="phone" value="${userinfo.phone }" required="required"><br>
		<input type= "submit" value="수정" onclick="javascript:btn()">		
	</form>
	<br>
	
</body>
</html>