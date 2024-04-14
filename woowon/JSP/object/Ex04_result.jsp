<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ex04_result</title>
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String address = request.getParameter("address");
	String[] pets = request.getParameterValues("pet");// 전송하는데이터가 여러개일때
	
	System.out.println("name >> " + name);
	System.out.println("address >> " + address);
	for(String pet : pets){
		System.out.println("pet >> " + pet);
	}
%>
</body>
</html>