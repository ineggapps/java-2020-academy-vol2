package com.score1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ScoreDAOImpl implements ScoreDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertScore(ScoreDTO dto) {
		int result = 0;
		Statement stmt = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("INSERT INTO score(hak, name, birth, kor, eng, mat) VALUES(");
			sb.append("'" + dto.getHak() + "', ");
			sb.append("'" + dto.getName() + "', ");
			sb.append("'" + dto.getBirth() + "', ");
			sb.append(dto.getKor() + ", ");
			sb.append(dto.getEng() + ", ");
			sb.append(dto.getMat() + ")");
			stmt = conn.createStatement(); // 쿼리문을 실행하는 객체
			result = stmt.executeUpdate(sb.toString()); // 실행을 요청하고 반영된 결괏값(int형)을 가져온다.
			// executeUpdate(sql); 실행 => insert, update, delete, create, alter, drop 등
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}

		return result;
	}

	@Override
	public int updateScore(ScoreDTO dto) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE score SET ");
		sb.append("name = '" + dto.getName() + "', ");
		sb.append("birth = '" + dto.getBirth() + "', ");
		sb.append("kor  = " + dto.getKor() + ", ");
		sb.append("eng = " + dto.getEng() + ", ");
		sb.append("mat = " + dto.getMat());
		sb.append("WHERE hak='" + dto.getHak() + "' ");
		int result = 0;
		try (Statement stmt = conn.createStatement()) {
			result = stmt.executeUpdate(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int deleteScore(String hak) {
		String sql;
		int result = 0;
		try (Statement stmt = conn.createStatement()) {
			sql = "DELETE FROM score WHERE hak='" + hak + "' ";
			result = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ScoreDTO readScore(String hak) {
		ScoreDTO dto = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT hak, name,");
		sb.append("TO_CHAR(birth,'YYYY-MM-DD') birth, kor, eng, mat, ");
		sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave ");
		sb.append("FROM score ");
		sb.append("WHERE hak='" + hak + "'");
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sb.toString())) {
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
		}
		return dto;
	}

	@Override
	public List<ScoreDTO> listScore(String name) {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT hak, name, birth, kor, eng, mat, ");
		sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave ");
		sb.append("FROM score ");
		sb.append("WHERE INSTR(name, '"+name+"')=1");
//		sb.append("WHERE name like '"+name+"%'");
//		sb.append("WHERE name like '%' || '"+name+"' || '%' "); // 이렇게 작성할 수도 있음.
		try(Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sb.toString())) {
			while(rs.next()) {
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
		}
		return list;
	}	

	@Override
	public List<ScoreDTO> listScore() {
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("SELECT hak, name, birth, kor, eng, mat, ");
			sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave, ");
			sb.append("RANK() OVER(ORDER BY kor+eng+mat DESC) rank ");
			sb.append("FROM score ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
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
