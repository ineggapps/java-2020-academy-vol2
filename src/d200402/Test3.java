package d200402;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.util.DBConn;

public class Test3 {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT hak, name, birth, kor, eng, mat FROM score";
			// SELECT * FROM score 처럼 사요앟면 수정이 불가.
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			//첫 번째 데이터
			if(rs.next()) {
				System.out.println(rs.getString(1)+"\t\t");
				System.out.println(rs.getString(2)+"\t\t");
				System.out.println(rs.getString(3)+"\t\t");
				System.out.println(rs.getString(4)+"\t\t");
				System.out.println(rs.getString(5)+"\t\t");
				System.out.println(rs.getString(6)+"\t\t");
				rs.updateString("name", "고고고");
				rs.updateInt("kor",100);
				rs.updateInt("eng",100);
				rs.updateInt("mat",100);
				rs.updateRow();
			}
			
			if(rs.absolute(1)) {
				System.out.println(rs.getString(1)+"\t\t");
				System.out.println(rs.getString(2)+"\t\t");
				System.out.println(rs.getString(3)+"\t\t");
				System.out.println(rs.getString(4)+"\t\t");
				System.out.println(rs.getString(5)+"\t\t");
				System.out.println(rs.getString(6)+"\t\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
