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
		alert("등록되었습니다");
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<form action="<%=request.getContextPath() %>/user.do" method="post">
		<input type="hidden" name="act" value="register">
		<input type="text" name="id" placeholder="아이디"><br>
		<input type="text" name="pass" placeholder="비밀번호"><br>
		<input type="text" name="name" placeholder="이름"><br>
		<input type="text" name="address" placeholder="주소"><br>
		<input type="text" name="phone" placeholder="전화번호"><br>
		<input type="submit" value="입력" onclick="javascript:btn()">
	</form>
</body>
</html>