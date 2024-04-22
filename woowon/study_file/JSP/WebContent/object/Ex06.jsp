<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리다이렉트</title>
</head>
<body>
구글로 이동
<%
	response.sendRedirect("https://www.google.com/");
%>
</body>
</html>