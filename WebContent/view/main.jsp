<%@page import="com.ssafy.happyhouse.model.dto.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
	UserInfo ui = (UserInfo)session.getAttribute("userinfo");
%>

<h1>메인페이지 입니다</h1>
<% if(ui == null){ 
	%>
	<%if (request.getAttribute("msg") != null){ %>
	
	<h2><%=request.getAttribute("msg")%></h2>
	<%} %>
	<%@ include file="/view/login.jsp"%>
	<%
	    } else {// 로그인 결과
	%>
		<%=ui.getId() %> 님 로그인 되었습니다.<br>
		<%@ include file="/view/userInfo.jsp"%>
<a href="${pageContext.request.contextPath }/main.do?act=search_deal">거래 검색</a> 
	<%
	    }
	%>
 
</body>
</html>