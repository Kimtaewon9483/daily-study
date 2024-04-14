<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	Enumeration enumData = application.getInitParameterNames();
	while(enumData.hasMoreElements()){
		String initParamName = (String)enumData.nextElement();
		String initParamValue = application.getInitParameter(initParamName);
		System.out.println(initParamName + ":" + initParamValue);
	}
%>
application 객체 예제<br>
서버정보 >> <%= application.getServerInfo()%><br>
서블릿 메이저 버전 >> <%= application.getMajorVersion()%><br>
서블릿 마이너 버전 >> <%= application.getMinorVersion()%>
</body>
</html>