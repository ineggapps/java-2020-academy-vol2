package d200330;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test3SelectQuery {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			stmt = conn.createStatement();
			sql = "SELECT name, TO_CHAR(birth,'YYYY-MM-DD') birth, kor, eng, mat, kor+eng+mat tot FROM score WHERE hak = '1111'";
//			sql = "SELECT name, birth, kor, eng, mat, kor+eng+mat tot FROM score WHERE hak = '1111'";
			rs = stmt.executeQuery(sql);

			if(rs.next()) {//결괏값이 2개 이상이어도 1개만 반환된다.
				String name = rs.getString("name");//또는 rs.getString(1);
				String birth = rs.getString("birth");//날짜이지만 String형으로도 변환되어 저장이 가능하다. rs.getString(2);
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int mat = rs.getInt("mat");
				int tot = rs.getInt("tot");
				System.out.println(String.format("%s, %s, %d, %d, %d, %d",name,birth,kor,eng,mat,tot));
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
