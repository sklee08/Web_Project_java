<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>상세 페이지 입니다.</h1>
	<table>
		<tr>
			<td><b>주택명</b></td>
			<td>${house_detail.aptName }</td>
		</tr> 
		<tr>
			<td><b>건축연도</b></td>
			<td>${house_detail.buildYear }</td>
		</tr>
		<tr>
			<td><b>법정동</b></td>
			<td>${house_detail.dong }</td>
		</tr>
		<tr>
			<td><b>지번</b></td>
			<td>${house_detail.jibun }</td>
		</tr>
	</table>


	<b>이미지</b>  
	<br>
	<img src="img/${house_detail.img }" width="100" height="100"/>
	<br>
</body>
</html>