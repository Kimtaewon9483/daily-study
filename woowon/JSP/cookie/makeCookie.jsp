<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/*
	쿠키는 서버에서 생성하고 브라우저에 응답하여 저장하도록 하는 데이터 조각이다
	브라우저는 저장된 쿠키를 해당 사이트에 자원 요청시 포함하여 전송한다
	브라우저가 사이트를 이용하면서 유지할 값이 있다면 사용한다
*/
	Cookie cookie1 = new Cookie("name","LWW");//쿠키의 이름,값과 같이 맵형식으로 데이터를 저장한다
	Cookie cookie2 = new Cookie("age","27");
	System.out.println("name >> " + cookie1.getValue());
	System.out.println("age >> " + cookie2.getValue());
	
	response.addCookie(cookie1);
	response.addCookie(cookie2);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키 생성</title>
</head>
<body>
쿠키가 생성되었습니다.
</body>
</html>