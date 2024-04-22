<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userid = request.getParameter("userid");
	String userpw = request.getParameter("userpw");
	
	if(userid.equals(userpw)){
		System.out.println(userid + "Login");
		response.sendRedirect(request.getContextPath());
	}else{
		System.out.println(userid + "Fail");
		response.sendRedirect("Ex06_LoginForm.jsp");
	}
%>