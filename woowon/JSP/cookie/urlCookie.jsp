<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키 경로확인</title>
</head>
<body>
<%
	Cookie cookie = new Cookie("cat","고양이");
	cookie.setPath(request.getContextPath()+"/path");//이쿠키는 최상위에 path라는 경로로 요청할때 사용
	response.addCookie(cookie);
%>
</body>
</html>