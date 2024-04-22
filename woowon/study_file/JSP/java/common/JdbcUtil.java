package common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {
	private static JdbcUtil instance = new JdbcUtil();
	private static DataSource ds;
	
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 성공!");
			
			InitialContext ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDB");
			System.out.println("Connection Poll 생성!");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	// 생성자
	private JdbcUtil() {}
	
	public static JdbcUtil getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		return ds.getConnection(); // 풀에서 커넥션을 반환
	}
}
