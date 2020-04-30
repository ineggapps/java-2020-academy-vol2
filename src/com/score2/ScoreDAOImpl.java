package com.score2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ScoreDAOImpl implements ScoreDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertScore(ScoreDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO score(hak, name, birth, kor, eng, mat) VALUES(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			// IN 파라미터 값 설정
			pstmt.setString(1, dto.getHak());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getBirth());
			pstmt.setInt(4, dto.getKor());
			pstmt.setInt(5, dto.getEng());
			pstmt.setInt(6, dto.getMat());
			// 쿼리 실행
			result = pstmt.executeUpdate(/* 쿼리 삽입하지 않는다 */);
		} catch (SQLException e) {
			e.printStackTrace();
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

	/*
	 * 이름 널? 유형 ----- -------- ------------ HAK NOT NULL VARCHAR2(30) NAME NOT NULL
	 * VARCHAR2(30) BIRTH NOT NULL DATE KOR NOT NULL NUMBER(3) MAT NOT NULL
	 * NUMBER(3) ENG NOT NULL NUMBER(3)
	 */

	@Override
	public int updateScore(ScoreDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "UPDATE score SET name=?, birth=?, kor=?, eng=?, mat=? WHERE hak=?";
			pstmt = conn.prepareStatement(sql);
			// IN 파라미터
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getBirth());
			pstmt.setInt(3, dto.getKor());
			pstmt.setInt(4, dto.getEng());
			pstmt.setInt(5, dto.getMat());
			pstmt.setString(6, dto.getHak());
			// 쿼리 실행
			result = pstmt.executeUpdate();
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
	public int deleteScore(String hak) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "DELETE FROM score WHERE hak=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hak);
			result = pstmt.executeUpdate();
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
	public ScoreDTO readScore(String hak) {
		ScoreDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT hak, name,");
		sb.append("TO_CHAR(birth,'YYYY-MM-DD') birth, kor, eng, mat, ");
		sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave ");
		sb.append("FROM score ");
		sb.append("WHERE hak=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, hak);
			rs = pstmt.executeQuery();
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
	public List<ScoreDTO> listScore(String name) {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT hak, name, birth, kor, eng, mat, ");
		sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave ");
		sb.append("FROM score ");
		sb.append("WHERE INSTR(name, ?)=1");
//		sb.append("WHERE name like ? || '%'");
//		sb.append("WHERE name like '%' || ? || '%' "); // 이렇게 작성할 수도 있음.
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
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
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	@Override
	public List<ScoreDTO> listScore() {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("SELECT hak, name, birth, kor, eng, mat, ");
			sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave, ");
			sb.append("RANK() OVER(ORDER BY kor+eng+mat DESC) rank ");
			sb.append("FROM score ");
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
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
