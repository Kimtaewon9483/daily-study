<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ex03</title>
</head>
<body>
<h2>JSP Test</h2>
<%
	//Scriptlet : java코드를 작성하는 영역(연산,처리)
	String str = "Scriptlet";
	String comment = "comment";
%>

declaration >> <%= declaration %> <br> <!-- 표현식: 값을 출력(처리,연산코드는 불가능) -->
scriptlet >> <%= str %>
<!-- <%= comment%> HTML주석 -->
<%-- <%= comment%> JSP주석--%>
<%!
	//declaration(member field, member method)
	String declaration = "declaration";
%>
</body>
</html>