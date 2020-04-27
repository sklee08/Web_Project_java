<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<style type = "text/css">
	body{
		text-align = cneter;
	}
	
	#paging{
		font-size = 22pt;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<div id= "paging">
		<c:url var ="action" value="/search?act=search_deal"/>
		<c:if test="${param.prev }">
			<a href="${action }&page=${param.start-1}">prev</a>
		</c:if>
		<c:forEach begin="${param.start }" end="${param.end }" step="1" var="index">
			<c:choose>
				<c:when test="${param.pageNo == index }">
					${index }
				</c:when>
				<c:otherwise>
					<a href="${action }&page=${index}">${index }</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${param.next }">
			<a href="${action }&page=${param.end+1}">next</a>
		</c:if>
	</div>
</body>
</html>