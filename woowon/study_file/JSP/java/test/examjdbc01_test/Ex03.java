package test.examjdbc01_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ex03 {
	public static void main(String[] args) {
		Connection conn = null;
		
		PreparedStatement pstmt = null; //
		ResultSet rs = null; //select의 결과는 ResultSet으로 온다
		
		try {
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"jsptest",
					"1234");
			System.out.println("접속 성공");
			
			pstmt = conn.prepareStatement("select * from \"MEMBER\" where \"NUM\"=?"); // ?는 바인딩변수, 사용자가 전달한 값으로 세팅된다
			pstmt.setInt(1, 2);// 첫번째 물음표가 2로 세팅
			rs = pstmt.executeQuery();// 쿼리를 전송의 결과를 rs에 저장
			
			if(rs.next()) {
				// NUM,MEMBERID,MEMBERPW,NICKNAME,REGDATE
				MemberVo vo = new MemberVo(
					rs.getInt(1),
					rs.getString(2),
					rs.getString("MEMBERPW"),
					rs.getString(4));
				vo.setRegDate(rs.getDate("REGDATE"));
				
				System.out.println(vo.toString());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try { rs.close();}
				catch (SQLException e) { e.printStackTrace();}
			}
			if(pstmt != null) {
				try { pstmt.close();}
				catch(SQLException e) { e.printStackTrace();}
			}
			if(conn != null) {
				try { conn.close(); }
				catch(SQLException e) { e.printStackTrace();}
			}
		}
	}
}
