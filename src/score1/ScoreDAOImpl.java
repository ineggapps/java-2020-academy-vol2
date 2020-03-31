package score1;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteScore(String hak) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ScoreDTO readScore(String hak) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScoreDTO> listScore(String name) {
		// TODO Auto-generated method stub
		return null;
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
				list.add(dto);//ArrayList에 저장
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
