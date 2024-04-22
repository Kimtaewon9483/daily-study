package test.examjdbc02_test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
	//db역할
	private JdbcConnectionUtil util;
	
	//생성자
	public MemberDao() {
		util = JdbcConnectionUtil.getInstance();
	}
	//c
	public int insertMember(MemberVo vo) {
			Connection conn = null;
			
			PreparedStatement pstmt = null; 
			int result = 0; 
			try {
				conn = util.getConnection();
				System.out.println("접속 성공");
				
				StringBuffer query = new StringBuffer();
				query.append("insert into \"MEMBER\" ");
				query.append("(\"NUM\", \"MEMBERID\", \"MEMBERPW\", \"NICKNAME\", \"REGDATE\") ");
				query.append("values (\"MEMBER_SEQ\".nextval, ?,?,?,sysdate)");
	
				pstmt = conn.prepareStatement(query.toString()); 
				pstmt.setString(1, vo.getMemberId());
				pstmt.setString(2, vo.getMemberPw());
				pstmt.setString(3, vo.getNickName());
				
				result = pstmt.executeUpdate();
				System.out.println(result + "행이 삽입되었습니다");			
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				util.close(pstmt);
				util.close(conn);
			}
			return result;
	}
	
	//R
	public MemberVo selectMember(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		MemberVo result = null;
		
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			pstmt = conn.prepareStatement("select * from \"MEMBER\" where \"NUM\"=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result= new MemberVo(
					rs.getInt(1),
					rs.getString(2),
					rs.getString("MEMBERPW"),
					rs.getString(4));
				result.setRegDate(rs.getDate("REGDATE"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}
	
	public List<MemberVo> selectMemverAll(){
		Connection conn = null;
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		List<MemberVo> result = new ArrayList<>();
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			pstmt = conn.prepareStatement("select * from \"MEMBER\"");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVo vo = new MemberVo(
					rs.getInt(1),
					rs.getString(2),
					rs.getString("MEMBERPW"),
					rs.getString(4));
				vo.setRegDate(rs.getDate("REGDATE"));
				result.add(vo);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}
	
	//U
	public int updateMamber(MemberVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null; 
		int result = 0; 
		
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			StringBuffer query = new StringBuffer();
			query.append("update \"MEMBER\" ");
			query.append("set \"MEMBERPW\"=?, \"NICKNAME\"=? ");
			query.append("where \"NUM\"=?");
			
			System.out.println(query.toString());
			
			pstmt = conn.prepareStatement(query.toString()); 
			pstmt.setString(1, vo.getMemberPw());
			pstmt.setString(2, vo.getNickName());
			pstmt.setInt(3, vo.getNum());
			
			result = pstmt.executeUpdate(); 
			//System.out.println(rs + "행이 수정되었습니다");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}
	
	//D
	public int deleteMember(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null; 
		int result = 0; 
		
		try {
			conn = util.getConnection();
			System.out.println("접속 성공");
			
			StringBuffer query = new StringBuffer();
			query.append("delete from \"MEMBER\" ");
			query.append("where \"NUM\"=?");
			
			System.out.println(query.toString());
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate(); 
			//System.out.println(result + "행이 삭제되었습니다");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}
}
