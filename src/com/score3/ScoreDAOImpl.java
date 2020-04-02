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

//CallableStatement: 프로시저 실행
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
			// IN 파라미터 값 설정
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			// 쿼리 실행
			cstmt.executeUpdate(/* 쿼리 삽입하지 않는다 */);
			//CallableStatement의 executeUpdate() 리턴 값은 
			//INSERT문 등을 실행 후 실행된 행 수를 반환하지 않고
			//프로시저 실행여부를 반환한다.
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
	 * 이름 널? 유형 ----- -------- ------------ HAK NOT NULL VARCHAR2(30) NAME NOT NULL
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
			// IN 파라미터
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			// 쿼리 실행
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
			//OUT 파라미터는 JDBC 타입을 설정
			//SYS_REFCURSOR=> OracleTypes.CURSOR (oracle.jdbc.OracleTypes)
			//정수형 => OracleTypes.INTEGER
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, hak);
			//모든 프로시저는 executeUpdate()로 실행한다
			cstmt.executeUpdate();
			//OUT파라미터 값을 넘겨 받기
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
				list.add(dto);// ArrayList에 저장
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
