package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.JdbcUtil;

// 데이터베이스에 접근하는 클래스
public class BoardDao {
	private JdbcUtil ju;
	public BoardDao() {
		ju = JdbcUtil.getInstance();
	}
	
	// 삽입(C)
	public int insert(BoardVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query="insert into \"BOARD\" (\"NUM\",\"TITLE\",\"WRITER\",\"CONTENT\",\"REGDATE\",\"CONT\") values (\"BOARD_SEQ\".nextval,?,?,?,sysdate,0)";
		int ret = -1;
		try {
			con = ju.getConnection();
			pstmt = con.prepareCall(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			ret = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	// 조회(R)
	public List<BoardVo> selectAll(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select \"NUM\",\"TITLE\",\"WRITER\",\"CONTENT\",\"REGDATE\",\"CONT\" from \"BOARD\" order by \"NUM\" desc";
		ArrayList<BoardVo> ls = new ArrayList<BoardVo>();
		try {
			con = ju.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				BoardVo vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				ls.add(vo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ls;
	}
	
	public BoardVo selectOne(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select \"NUM\",\"TITLE\",\"WRITER\",\"CONTENT\",\"REGDATE\",\"CONT\" from \"BOARD\" where \"NUM\"=?";
		BoardVo vo = null;
		try {
			con = ju.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				updateCNT(num);
				vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6)+1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return vo;
	}
	
	// 수정(U)
	public int update(BoardVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query="update \"BOARD\" set \"TITLE\"=?,\"CONTENT\"=? where \"NUM\"=?";
		int ret = -1;
		try {
			con = ju.getConnection();
			pstmt = con.prepareCall(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNum());
			ret = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	// 조회수 증가
	public int updateCNT(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query="update \"BOARD\" set \"CONT\"=\"CONT\"+1 where \"NUM\"=?";
		int ret = -1;
		try {
			con = ju.getConnection();
			pstmt = con.prepareCall(query);
			pstmt.setInt(1, num);
			ret = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	// 삭제(D)
	public int delete(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query="delete from \"BOARD\" where \"NUM\"=?";
		int ret = -1;
		try {
			con = ju.getConnection();
			pstmt = con.prepareCall(query);
			pstmt.setInt(1, num);
			ret = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
}
