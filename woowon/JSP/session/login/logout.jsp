<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println(session.getAttribute("loginId") + "로그아웃");
	session.invalidate();
	response.sendRedirect("loginForm.jsp");
%>