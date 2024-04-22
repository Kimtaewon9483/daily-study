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
<title>삭제하기</title>
</head>
<body>
<h3>회원 삭제</h3>
번호 >> <%= vo.getNum() %><br>
아이디 >> <%= vo.getMemberId() %><br>
<form action="delete.jsp">
	해당 회원을 삭제하시겠습니까?<br>
	<input type="hidden" value="<%= vo.getNum() %>" name="num">
	<input type="submit" value="삭제">
	<a href="<%=request.getContextPath()%>/dbTest/member/detail.jsp?num=<%= vo.getNum() %>">취소</a>
</form>
</body>
</html>