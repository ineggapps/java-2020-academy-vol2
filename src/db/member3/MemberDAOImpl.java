package db.member3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

public class MemberDAOImpl implements MemberDAO {
//	private final static String TABLE_MEMBER1 = "member1";
//	private final static String TABLE_MEMBER2 = "member2";
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertMember(MemberDTO dto) {
		int result = 0;
		CallableStatement cstmt = null;
		String sql = "{CALL insertMember(?,?,?,?,?,?)}";
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, dto.getId());
			cstmt.setString(2, dto.getPwd());
			cstmt.setString(3, dto.getName());
			cstmt.setString(4, dto.getBirth());
			cstmt.setString(5, dto.getEmail());
			cstmt.setString(6, dto.getTel());
			cstmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int updateMember(MemberDTO dto) {
		int result = 0;
		CallableStatement cstmt = null;
		String sql = "{CALL updateMember(?,?,?,?,?,?)}";
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, dto.getId());
			cstmt.setString(2, dto.getPwd());
			cstmt.setString(3, dto.getName());
			cstmt.setString(4, dto.getBirth());
			cstmt.setString(5, dto.getEmail());
			cstmt.setString(6, dto.getTel());
			cstmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int deleteMember(String id) {
		int result = 0;
		CallableStatement cstmt = null;
		String sql = "{CALL deleteMember(?)}";
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, id);
			cstmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "{CALL findByIdMember(?,?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, id);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1);
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
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
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}

	@Override
	public List<MemberDTO> listMember(String name) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "{CALL findByNameMember(?,?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, name);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1);
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
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
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	@Override
	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "{CALL listMember(?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet)cstmt.getObject(1);
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
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
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
}
