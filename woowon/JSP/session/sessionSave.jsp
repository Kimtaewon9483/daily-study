<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String data = request.getParameter("data");
	session.setAttribute("saveData", data); // 세션에 저장
	response.sendRedirect("sessionDataSave.jsp");
%>