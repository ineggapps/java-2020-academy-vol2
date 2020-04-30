package d200330;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test4SelectQuery {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			stmt = conn.createStatement();
//			sql = "SELECT name, TO_CHAR(birth,'YYYY-MM-DD') birth, kor, eng, mat, kor+eng+mat tot, hak FROM score";
			sql = "SELECT name, birth, kor, eng, mat, kor+eng+mat tot, hak FROM score";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {//결괏값 복수 개 출력하기 (커서의 개념과 유사하다)
				String hak = rs.getString("hak");
				String name = rs.getString("name");//또는 rs.getString(1);
				String birth = rs.getDate("birth").toString();//날짜이지만 String형으로도 변환되어 저장이 가능하다. rs.getString(2);
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int mat = rs.getInt("mat");
				int tot = rs.getInt("tot");
				System.out.println(String.format("%s, %s, %s, %d, %d, %d, %d",hak, name,birth,kor,eng,mat,tot));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
