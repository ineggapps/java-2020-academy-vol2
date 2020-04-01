package db.member2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		PreparedStatement pstmt = null;
		String sql1 = "INSERT INTO " + TABLE_MEMBER1 + "(id, pwd, name) VALUES (?, ?, ?)";
		String sql2 = "INSERT INTO " + TABLE_MEMBER2 + "(id, birth, email, tel) VALUES (?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getName());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getTel());
			pstmt.executeUpdate();
			/*
			 * sql = "INSERT ALL "; sql += " INTO member1(id,pwd,name) VALUES(?,?,?) "; sql
			 * += " INTO member2(id,birth,email,tel) VALUES(?,?,?,?) "; sql +=
			 * " SELECT * FROM dual";
			 * 
			 * pstmt = conn.prepareStatement(sql); pstmt.setString(1, dto.getId());
			 * pstmt.setString(2, dto.getPwd()); pstmt.setString(3, dto.getName());
			 * pstmt.setString(4, dto.getId()); pstmt.setString(5, dto.getBirth());
			 * pstmt.setString(6, dto.getEmail()); pstmt.setString(7, dto.getTel()); result
			 * = pstmt.executeUpdate();
			 */
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int updateMember(MemberDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql1 = "UPDATE " + TABLE_MEMBER1 + " SET pwd=? WHERE id=?";
		String sql2 = "UPDATE " + TABLE_MEMBER2 + " SET birth=?, email=?, tel=? WHERE id=?"; 
		try {
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getId());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, dto.getBirth());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getId());
			pstmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int deleteMember(String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql1 = "DELETE FROM " + TABLE_MEMBER2 + " WHERE id = ?";
		String sql2 = "DELETE FROM " + TABLE_MEMBER1 + " WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.id, name, TO_CHAR(birth,'YYYY-MM-DD') birth, email, tel ");
		sql.append("FROM " + TABLE_MEMBER1 + " m1 ");
		sql.append("LEFT OUTER JOIN " + TABLE_MEMBER2 + " m2 ON m2.id=m1.id ");
		sql.append("WHERE m1.id=?");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}

	@Override
	public List<MemberDTO> listMember(String name) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.id, name, TO_CHAR(birth,'YYYY-MM-DD') birth, email, tel FROM " + TABLE_MEMBER1 + " m1 ");
		sql.append("LEFT OUTER JOIN " + TABLE_MEMBER2 + " m2 ON m1.id=m2.id ");
		sql.append("WHERE INSTR(name,?) >= 1");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	@Override
	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.id, name, TO_CHAR(birth,'YYYY-MM-DD') birth, email, tel ");
		sql.append("FROM " + TABLE_MEMBER1 + " m1 ");
		sql.append("LEFT OUTER JOIN " + TABLE_MEMBER2 + " m2 ON m2.id=m1.id ");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
}
