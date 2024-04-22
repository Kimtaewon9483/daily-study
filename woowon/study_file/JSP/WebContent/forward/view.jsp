<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String code = request.getParameter("code");
	String url = "";
	
	if(code.equals("A")){
		url = "/forward/a.jsp";	
	}else if(code.equals("B")){
		url = "/forward/b.jsp";		
	}else if(code.equals("C")){
		url = "/forward/c.jsp";
	}
	
	// forward
	//방법1
	//RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	//dispatcher.forward(request, response);
	
	//방법2
	//request.getRequestDispatcher(url).forward(request,response);
%>
<jsp:forward page="<%= url %>"/>