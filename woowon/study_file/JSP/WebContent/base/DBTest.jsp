<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		Connection conn = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
			out.print("�����ͺ��̽� ������ �����߽��ϴ�.");
		} catch(SQLException ex) {
			out.print("�����ͺ��̽� ������ �����߽��ϴ�.");
			out.print("SQLException : " + ex.getMessage());
		}finally{
			if(conn != null) {
				conn.close();
			}
		}
	%>
</body>
</html>