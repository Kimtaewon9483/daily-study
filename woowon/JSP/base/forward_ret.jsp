<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>forward 결과</title>
</head>
<body>
<%
	String name = request.getParameter("name");
	String age = request.getParameter("age");
%>
forward 결과<br>
요청 url부분이 변경되지 않고 request객체도 유지<br>
<%= "forward_ret.jsp에서 request 파라미터 확인" %><br>
<%= "이름 >> " + name %>
<%= "나이 >> " + age %>
</body>
</html>