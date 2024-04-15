<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 확인</title>
</head>
<body>
세션에 저장된 데이터 >> <%= session.getAttribute("saveData") %>
</body>
</html>