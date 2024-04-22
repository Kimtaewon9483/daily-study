<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="dbTestManager.*"%>    
<%request.setCharacterEncoding("utf-8");%>

<jsp:useBean id="vo" class="dbTestManager.MemberVo"/>
<jsp:setProperty name="vo" property="*"/>

<%	
	MemberService memberService = MemberService.getInstance();
	
	if(memberService.regist(vo)){
		response.sendRedirect(request.getContextPath()+"/dbTest/member/index.jsp");
	}else{
		response.sendRedirect(request.getContextPath()+"/dbTest/member/registForm.jsp");
	}
%>