<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
</head>
<body>
<%= session.getAttribute("loginId") %>로그인 성공<br>
<a href="logout.jsp"><button>로그아웃</button></a>
</body>
</html>