<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("name", "page scope");
	request.setAttribute("name", "request scope");
	session.setAttribute("name", "session scope");
	application.setAttribute("name", "application scope");
	System.out.println("첫번째 페이지");
	System.out.println("페이지범위>> " + pageContext.getAttribute("name"));
	System.out.println("요청범위>> " + request.getAttribute("name"));
	System.out.println("세션범위>> " + session.getAttribute("name"));
	System.out.println("애플리케이션범위>> " + application.getAttribute("name"));
	
	request.getRequestDispatcher("Ex10_2.jsp").forward(request, response);
%>