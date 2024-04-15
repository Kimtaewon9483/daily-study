<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>redirect 결과</title>
</head>
<body>
<%
	String name = request.getParameter("name");
	String age = request.getParameter("age");
%>
Redirect 결과<br>
브라이저에서 새로 요청한 것이므로<br>
요청 url부분이 변경되고 request갹채도 새로 생성(값유지안됨)<br>
<%= "redirect_ret.jsp에서 request파라미터 확인" %><br>
<%= "이름 >> " + name %><br>
<%= "나이 >> " + age %><br>
</body>
</html>