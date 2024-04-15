<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="dbTestManager.*"%>
<%
	MemberService memberService = MemberService.getInstance();
	int num = Integer.parseInt(request.getParameter("num"));
	if(memberService.remove(num)){
		response.sendRedirect(request.getContextPath()+"/dbTest/member/list.jsp");
	}else{
		response.sendRedirect(request.getContextPath() + "/dbTest/member/detail.jsp?num="+num);
	}
%>