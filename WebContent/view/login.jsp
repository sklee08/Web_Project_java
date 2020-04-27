<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<h1>로그인</h1>
	<form action="<%=request.getContextPath() %>/user.do" method="post">
		<input type = "hidden" name="act" value="login">
		<input type = "text" placeholder="아이디" name ="id">
		<input type = "password" placeholder="비밀번호" name ="pass">
		<input type="submit" value="로그인">
	</form>
	
	<a href="<%=request.getContextPath() %>/user.do?act=mvreguser">회원가입</a>
	<a href="<%=request.getContextPath() %>/user.do?act=mvfindpw">비밀번호 찾기</a>
	
</body>
</html>