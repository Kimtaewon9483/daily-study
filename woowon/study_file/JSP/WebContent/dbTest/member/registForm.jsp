<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록</title>
</head>
<body>
	<h3>회원 등록 하기</h3>
	<form method="post" action="<%= request.getContextPath() %>/dbTest/member/regist.jsp">
		<input type="text" name="memberId" value="" placeholder="ID"><br>
		<input type="password" name="memberPw" value="" placeholder="PW"><br>
		<input type="text" name="nickName" value="" placeholder="Nickname"><br>
		<input type="submit" value="등록">
	</form>
</body>
</html>