<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.*, java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="dao" class="board.BoardDao"/>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	dao.delete(num);
%>
<c:redirect url="list.jsp"></c:redirect>