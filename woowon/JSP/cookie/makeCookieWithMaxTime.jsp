<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유효시간이 있는 쿠키</title>
</head>
<body>
<%
	Cookie cookie4 = new Cookie("timeCookie","timecookie");
	cookie4.setMaxAge(20);
	response.addCookie(cookie4);
%>
유효시간이 1시간인 쿠키
</body>
</html>