<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Result</title>
</head>
<body>
<%
	String name = request.getParameter("name");
	String pw = request.getParameter("pw");
	System.out.println("확인 >> " + name + ", " + pw);
%>
결과>> <%= name %>, <%= pw %>
</body>
</html>