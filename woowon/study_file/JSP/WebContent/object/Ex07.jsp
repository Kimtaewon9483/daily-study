<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pageContext</title>
</head>
<body>
<%
	HttpServletRequest httpRequest = (HttpServletRequest)pageContext.getRequest();
	if(request == httpRequest){
		System.out.println("같다");
	}
	pageContext.getOut().println("브라우저로 내보내는 데이터");
%>
</body>
</html>