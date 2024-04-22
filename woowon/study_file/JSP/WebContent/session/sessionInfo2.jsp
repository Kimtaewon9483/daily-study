<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>  
<%
	// 세션 유효시간 설정(초단위)
	session.setMaxInactiveInterval(10);

	Date creationtime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	creationtime.setTime(session.getCreationTime()); //세션이 최초로 생성된 시간	
	
	Date lastAccessTime = new Date();
	lastAccessTime.setTime(session.getLastAccessedTime()); //마지막 접근 시간
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션 확인2</title>
</head>
<body>
세션 ID >> <%= session.getId() %><br>
최초 생성 시간 >> <%= formatter.format(creationtime) %><br>
마지막 접근 시간 >> <%= formatter.format(lastAccessTime)%>
</body>
</html>