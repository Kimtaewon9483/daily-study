package test.examjdbc01_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//싱글톤(Singleton) 적용
//메모리상에서 객체가 반드시 하나만 만들어지도록 하기위해 사용하는 디자인패턴
public class JdbcConnectionUtil {
	private static JdbcConnectionUtil instance; // 자기자신과 동일한걸 참조 할수있는 변수
	
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "jsptest";
	private String password = "1234";
	
	// 생성자, 다른클래스에서 호출할수 없음
	private JdbcConnectionUtil() {
		
	}
	
	// instance가 만약 null이라면 instance에 JdbcConnectionUtil을 생성하게함
	// 다른 파일이 아니라서 private에 접근이 가능
	// 어떤 쓰레드가 동기화 내부를 처리하는 중이라면 대기상태로 만든다
	public static JdbcConnectionUtil getInstance() {
		synchronized (JdbcConnectionUtil.class) {
			if(instance == null) {
				instance = new JdbcConnectionUtil();
			}
		}
		return instance;
	}
	
	// connection을 리턴해준다
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
}
