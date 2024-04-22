package test.examjdbc02_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ex05 {
	public static void main(String[] args) {
		Connection conn = null;
		
		PreparedStatement pstmt = null; 
		int rs = 0; 
		JdbcConnectionUtil util = JdbcConnectionUtil.getInstance();
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			StringBuffer query = new StringBuffer();
			query.append("update \"MEMBER\" ");
			query.append("set \"MEMBERPW\"=?, \"NICKNAME\"=? ");
			query.append("where \"NUM\"=?");
			
			System.out.println(query.toString());
			
			pstmt = conn.prepareStatement(query.toString()); 
			pstmt.setString(1, "4321");
			pstmt.setString(2, "nnnn");
			pstmt.setInt(3, 5);
			
			rs = pstmt.executeUpdate(); 
			System.out.println(rs + "행이 수정되었습니다");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(pstmt);
			util.close(conn);
		}
	}
}
