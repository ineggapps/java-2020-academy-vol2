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
			sql = "SELECT name, TO_CHAR(birth,'YYYY-MM-DD') birth, kor, eng, mat, kor+eng+mat tot, hak FROM score";
//			sql = "SELECT name, birth, kor, eng, mat, kor+eng+mat tot FROM score";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {//�ᱣ�� ���� �� ����ϱ�
				String hak = rs.getString("hak");
				String name = rs.getString("name");//�Ǵ� rs.getString(1);
				String birth = rs.getString("birth");//��¥������ String�����ε� ��ȯ�Ǿ� ������ �����ϴ�. rs.getString(2);
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