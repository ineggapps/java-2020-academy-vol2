package db.member1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MemberDAOImpl implements MemberDAO {
	private final static String TABLE_MEMBER1 = "member1";
	private final static String TABLE_MEMBER2 = "member2";
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertMember(MemberDTO dto) {
		int result = 0;
		Statement stmt = null;
		StringBuilder sql1 = new StringBuilder();
		sql1.append("INSERT INTO " + TABLE_MEMBER1 + "(id, pwd, name) VALUES (");
		sql1.append("'" + dto.getId() + "', ");
		sql1.append("'" + dto.getPwd() + "', ");
		sql1.append("'" + dto.getName() + "' ");
		sql1.append(") ");
		StringBuilder sql2 = new StringBuilder();
		sql2.append("INSERT INTO " + TABLE_MEMBER2 + "(id, birth, email, tel) VALUES (");
		sql2.append("'" + dto.getId() + "', ");
		sql2.append("'" + dto.getBirth() + "', ");
		sql2.append("'" + dto.getEmail() + "', ");
		sql2.append("'" + dto.getTel() + "' ");
		sql2.append(") ");
		
		
		try {
			stmt = conn.createStatement();
			if (stmt.executeUpdate(sql1.toString()) > 0 && stmt.executeUpdate(sql2.toString()) > 0) {
				result = 1;
			}
			
			/*
			 한 번에 insert작업 수행하기
			StringBuilder sb=new StringBuilder();
			sb.append("INSERT ALL ");
			sb.append(" INTO member1(id,pwd,name) VALUES(");
			sb.append(" '"+dto.getId()+"'");
			sb.append(" ,'"+dto.getPwd()+"'");
			sb.append(" ,'"+dto.getName()+"'");
			sb.append(" )");
			sb.append(" INTO member2(id,birth,email,tel) VALUES(");
			sb.append(" '"+dto.getId()+"'");
			sb.append(" ,'"+dto.getBirth()+"'");
			sb.append(" ,'"+dto.getEmail()+"'");
			sb.append(" ,'"+dto.getTel()+"'");
			sb.append(" )");
			sb.append(" SELECT * FROM dual");
			
			result=stmt.executeUpdate(sb.toString());
			 */
		} catch (Exception e) {
			e.printStackTrace();
			// 만약에 member1에선 성공했지만 member2테이블에서 결괏값을 넣기를 실패했다면?
			// member1테이블의 값도 지워줘야겠지?
			deleteMember(dto.getId());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int updateMember(MemberDTO dto) {
		int result = 0;
		Statement stmt = null;
		StringBuilder sql1 = new StringBuilder();
		sql1.append("UPDATE " + TABLE_MEMBER1 + " SET ");
		sql1.append("pwd='" + dto.getPwd() + "' ");
		sql1.append("WHERE id='" + dto.getId() + "'");
		StringBuilder sql2 = new StringBuilder();
		sql2.append("UPDATE " + TABLE_MEMBER2 + " SET ");
		sql2.append("birth='" + dto.getBirth() + "', ");
		sql2.append("email='" + dto.getEmail() + "', ");
		sql2.append("tel='" + dto.getTel() + "' ");
		sql2.append("WHERE id='" + dto.getId() + "'");
		try {
			stmt = conn.createStatement();
			if (stmt.executeUpdate(sql1.toString()) > 0 && stmt.executeUpdate(sql2.toString()) > 0) {
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int deleteMember(String id) {
		int result = 0;
		Statement stmt = null;
		StringBuilder sql1 = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		sql1.append("DELETE FROM " + TABLE_MEMBER2);
		sql1.append("WHERE id='" + id + "'");
		sql2.append("DELETE FROM " + TABLE_MEMBER1);
		sql2.append("WHERE id='" + id + "'");
		try {
			stmt = conn.createStatement();
			if (stmt.executeUpdate(sql1.toString()) > 0 && stmt.executeUpdate(sql2.toString()) > 0) {
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.id, name, TO_CHAR(birth,'YYYY-MM-DD') birth, email, tel ");
		sql.append("FROM " + TABLE_MEMBER1 + " m1 ");
		sql.append("LEFT OUTER JOIN " + TABLE_MEMBER2 + " m2 ON m2.id=m1.id ");
		sql.append("WHERE m1.id='" + id + "'");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}

	@Override
	public List<MemberDTO> listMember(String name) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.id, name, TO_CHAR(birth,'YYYY-MM-DD') birth, email, tel FROM " + TABLE_MEMBER1 + " m1 ");
		sql.append("LEFT OUTER JOIN " + TABLE_MEMBER2 + " m2 ON m1.id=m2.id ");
		sql.append("WHERE INSTR(name,'" + name + "') >= 1");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	@Override
	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.id, name, TO_CHAR(birth,'YYYY-MM-DD') birth, email, tel ");
		sql.append("FROM " + TABLE_MEMBER1 + " m1 ");
		sql.append("LEFT OUTER JOIN " + TABLE_MEMBER2 + " m2 ON m2.id=m1.id ");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
}
