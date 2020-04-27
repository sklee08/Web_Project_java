<%@page import="com.ssafy.happyhouse.model.dto.UserInfo"%>
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
<script>
	function btn(){
		alert("삭제되었습니다.");
	}
	

</script>
<title>Insert title here</title>
</head>
<body>
	<%
		UserInfo userInfo = (UserInfo)session.getAttribute("userinfo");
	%>
	<a href="<%=request.getContextPath() %>/user.do?act=logout">로그아웃</a>
	<a href="<%=request.getContextPath() %>/user.do?act=mvlist&id=<%=userInfo.getId()%>">회원정보 조회</a>
	<a href="<%=request.getContextPath() %>/user.do?act=mvupdate&id=<%=userInfo.getId()%>">회원정보 수정</a>
	<a href="<%=request.getContextPath() %>/user.do?act=delete&id=${userinfo.id}" onclick="javascript:btn()">회원정보 삭제</a>
	<br>
</body>
</html>