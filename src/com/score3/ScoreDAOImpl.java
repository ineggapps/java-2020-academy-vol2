package com.score3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

//CallableStatement: ���ν��� ����
public class ScoreDAOImpl implements ScoreDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertScore(ScoreDTO dto) {
		int result = 0;
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{CALL insertScore(?,?,?,?,?,?)}";
			cstmt = conn.prepareCall(sql);
			// IN �Ķ���� �� ����
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			// ���� ����
			cstmt.executeUpdate(/* ���� �������� �ʴ´� */);
			//CallableStatement�� executeUpdate() ���� ���� 
			//INSERT�� ���� ���� �� ����� �� ���� ��ȯ���� �ʰ�
			//���ν��� ���࿩�θ� ��ȯ�Ѵ�.
			result=1;
		} catch (SQLException e) {
			e.printStackTrace();
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

	/*
	 * �̸� ��? ���� ----- -------- ------------ HAK NOT NULL VARCHAR2(30) NAME NOT NULL
	 * VARCHAR2(30) BIRTH NOT NULL DATE KOR NOT NULL NUMBER(3) MAT NOT NULL
	 * NUMBER(3) ENG NOT NULL NUMBER(3)
	 */

	@Override
	public int updateScore(ScoreDTO dto) {
		int result = 0;
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{CALL updateScore(?,?,?,?,?,?)}";
			cstmt = conn.prepareCall(sql);
			// IN �Ķ����
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			// ���� ����
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
	public int deleteScore(String hak) {
		int result = 0;
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "DELETE FROM score WHERE hak=?";
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, hak);
			cstmt.executeUpdate();
			result =1;
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
	public ScoreDTO readScore(String hak) {
		ScoreDTO dto = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "{CALL findByHakScore(?,?)}";
			cstmt = conn.prepareCall(sql);
			//OUT �Ķ���ʹ� JDBC Ÿ���� ����
			//SYS_REFCURSOR=> OracleTypes.CURSOR (oracle.jdbc.OracleTypes)
			//������ => OracleTypes.INTEGER
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, hak);
			//��� ���ν����� executeUpdate()�� �����Ѵ�
			cstmt.executeUpdate();
			//OUT�Ķ���� ���� �Ѱ� �ޱ�
			rs = (ResultSet) cstmt.getObject(1);
			if (rs.next()) {
				dto = new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
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
	public List<ScoreDTO> listScore(String name) {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "{CALL findByNameScore(?,?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, name);
			cstmt.executeUpdate();
			rs = (ResultSet) cstmt.getObject(1);
			while (rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(cstmt!=null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	@Override
	public List<ScoreDTO> listScore() {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "{CALL listScore(?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeUpdate();
			rs = (ResultSet) cstmt.getObject(1);
			while (rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				dto.setRank(rs.getInt("rank"));
				list.add(dto);// ArrayList�� ����
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
