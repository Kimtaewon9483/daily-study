package test.examjdbc02_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ex04 {
	public static void main(String[] args) {
		Connection conn = null;
		
		PreparedStatement pstmt = null; 
		int rs = 0; 
		JdbcConnectionUtil util = JdbcConnectionUtil.getInstance();
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			StringBuffer query = new StringBuffer();//문자열을 관리하기 위함
			query.append("insert into \"MEMBER\" ");
			query.append("(\"NUM\", \"MEMBERID\", \"MEMBERPW\", \"NICKNAME\", \"REGDATE\") ");
			query.append("values (\"MEMBER_SEQ\".nextval, ?,?,?,sysdate)");
			
			System.out.println(query.toString());
			
			pstmt = conn.prepareStatement(query.toString()); 
			pstmt.setString(1, "tester1");
			pstmt.setString(2, "1234");
			pstmt.setString(3, "testnick1");
			
			rs = pstmt.executeUpdate(); // insert는 반환값이 숫자이기떄문에
			System.out.println(rs + "행이 삽입되었습니다");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(pstmt);
			util.close(conn);
		}
	}
}
