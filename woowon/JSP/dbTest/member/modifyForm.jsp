<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="dbTestManager.*"%>
<%
	MemberService memberService = MemberService.getInstance();
	int num = Integer.parseInt(request.getParameter("num"));
	MemberVo vo = memberService.read(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
</head>
<body>
	<h3>회원 정보 수정 하기</h3>
	<form method="post" action="<%= request.getContextPath() %>/dbTest/member/modify.jsp">
		<input type="hidden" name="num" value="<%= vo.getNum() %>">
		<input type="text" name="memberId" value="<%= vo.getMemberId() %>" placeholder="ID" disabled><br>
		<input type="password" name="memberPwOld" value="" placeholder="기존 비밀번호"><br>
		<input type="password" name="memberPwNew" value="" placeholder="새로운 비밀번호"><br>
		<input type="text" name="nickName" value="<%= vo.getNickName() %>" placeholder="Nickname"><br>
		<input type="submit" value="수정">
	</form>
</body>
</html>