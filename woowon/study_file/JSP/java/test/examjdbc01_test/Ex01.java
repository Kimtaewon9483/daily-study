package test.examjdbc01_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex01 {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");// 드라이버 확인
			//oracle.jdbc.driver.OracleDriver
			System.out.println("클래스 로딩");
			
			//DriverManager를 사용해 연결
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"jsptest",
					"1234");
			System.out.println("접속 성공");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
