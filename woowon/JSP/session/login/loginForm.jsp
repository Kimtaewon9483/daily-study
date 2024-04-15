<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인폼</title>
</head>
<body>
<%
	String loginId = (String)session.getAttribute("loginId"); // Object가 반환되기 때문에 형변환
	if(loginId != null){
		System.out.println("로그인 상태");
		response.sendRedirect("loginSuccess.jsp");
	}else{
	String checked = "";
	String userid = "";
	Cookie[] cookies = request.getCookies();
	if(cookies != null && cookies.length > 0){
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("remember")){
				checked = "checked";
				userid = cookies[i].getValue();
			}
		}
	}
%>
<form action="LoginProc.jsp" method="post">
	<input type="text" name="userid" placeholder="ID" value="<%= userid%>"><br>
	<input type="password" name="userpw" placeholder="PW"><br>
	아이디 기억하기<input type="checkbox" name="remember" value="chk" <%= checked %>><br>
	<input type="submit" value="로그인">
</form>
</body>
</html>
<%
	}
%>