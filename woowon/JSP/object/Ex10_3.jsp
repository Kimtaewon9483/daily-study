<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>3</title>
</head>
<body>
하나의 페이지 범위 >> <%= pageContext.getAttribute("name") %><br>
하나의 요청 범위 >> <%= request.getAttribute("name") %><br>
하나의 세션 범위 >> <%= session.getAttribute("name") %><br>
하나의 애플리케이션 범위 >> <%= application.getAttribute("name") %><br>
<a href="<%= request.getContextPath()%>">처음으로</a>
</body>
</html>