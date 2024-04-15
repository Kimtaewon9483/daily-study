<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 여부</title>
</head>
<body>
<%
	//1. 요청에서 파라미터 가져오기
	String userid = request.getParameter("userid");
	String userpw = request.getParameter("userpw");
	String remember = request.getParameter("remember");
	System.out.println("ID >> " + userid);
	System.out.println("PW >> " + userpw);
	System.out.println("remember >> " + remember);
	//2. 아이디와 비밀번호 같으면 로그인 성공으로 처리
		// 아이디 기억하기 여부에 따라 쿠키생성응답(1시간 유지)
	if(userid.equals(userpw)){
		System.out.println("로그인 성공");
		if(remember != null){
			Cookie rememberCookie = new Cookie("remember",userid);
			rememberCookie.setMaxAge(60*60);
			response.addCookie(rememberCookie);
			System.out.println("아이디 쿠키 저장");
		}else{
			Cookie rememberCookie = new Cookie("remember",userid);
			rememberCookie.setMaxAge(0);
			response.addCookie(rememberCookie);
			System.out.println("아이디 쿠키 삭제");
		}
		response.sendRedirect("loginSuccess.jsp");
	}
	//3. 아이디와 비밀번호가 같지 않으면 다시 로그인 페이지로
	else{
		System.out.println("로그인 실패");
		response.sendRedirect("loginForm.jsp");
	}
	//로그인페이지에 remember쿠키가 존재하면 체크상태로 보여지도록
		//아이디가 userid텍스트 상자에 표시되어야함
	
%>
</body>
</html>