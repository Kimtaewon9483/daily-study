<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="dbTestManager.*"%>
<%request.setCharacterEncoding("utf-8");%>
<jsp:useBean id="modifyRequest" class="dbTestManager.ModifyRequest" />
<jsp:setProperty name="modifyRequest" property="*" />

<%
	MemberService memberService = MemberService.getInstance();
	
	MemberVo vo = new MemberVo(
			modifyRequest.getNum(),
			modifyRequest.getMemberId(),
			modifyRequest.getMemberPwNew(),
			modifyRequest.getNickName());
	if(memberService.edit(vo, modifyRequest.getMemberPwOld())){
		response.sendRedirect(request.getContextPath() + "/dbTest/member/detail.jsp?num="+modifyRequest.getNum());
	}else{
		response.sendRedirect(request.getContextPath() + "/dbTest/member/modifyForm.jsp?num="+modifyRequest.getNum());
	}
%>