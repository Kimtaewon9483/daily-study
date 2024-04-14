<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 헤더 정보</title>
</head>
<body>
<!-- 헤더안에 들어있는 정보들 -->
<%
	Enumeration enumData = request.getHeaderNames();
	while(enumData.hasMoreElements()){
		String headerName = (String)enumData.nextElement();
		String headerValue = request.getHeader(headerName);
		System.out.println(headerName + ">> " + headerValue);
	}
%>
</body>
</html>