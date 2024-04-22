<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키수정</title>
</head>
<body>
<%
	Cookie[] cookies = request.getCookies();
	// 쿠키가 있다면 동작
	if(cookies != null && cookies.length > 0){
		for(int i=0; i<cookies.length;i++){
			if(cookies[i].getName().equals("name")){
				Cookie cookie = new Cookie("name","Lee");
				response.addCookie(cookie);
			}
		}
	}
%>
쿠키가 수정되었습니다
</body>
</html>