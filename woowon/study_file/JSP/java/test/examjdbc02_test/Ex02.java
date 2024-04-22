package test.examjdbc02_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ex02 {
	public static void main(String[] args) {
		Connection conn = null;
		
		PreparedStatement pstmt = null; //
		ResultSet rs = null; //select의 결과는 ResultSet으로 온다
		JdbcConnectionUtil util = JdbcConnectionUtil.getInstance();
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			pstmt = conn.prepareStatement("select * from \"MEMBER\""); //쿼리를 pstmt에 넣어준다
			rs = pstmt.executeQuery();// 쿼리를 전송의 결과를 rs에 저장
			
			while(rs.next()) {
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
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
	}
}
