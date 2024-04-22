<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>버퍼</title>
</head>
<body>
<!-- -->
<%
	int bufferSize = out.getRemaining();
	int remainSize = out.getRemaining();
	int usedSize = bufferSize - remainSize;
	System.out.println("전체 버퍼의 크기 >> " + bufferSize + "byte");
	System.out.println("사용한 버퍼의 크기 >> " + usedSize + "byte");
	System.out.println("남은 버퍼의 크기 >> " + remainSize + "byte");
%>
버퍼예제<br>
남은 버퍼 : <% out.println(remainSize); %>byte
</body>
</html>