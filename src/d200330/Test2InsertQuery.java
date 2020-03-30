package d200330;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test2InsertQuery {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection(); //sql developer�� sqlplus���� �α����ϴ� ���� - DriverManager���� �����´�.
		Statement stmt = null; //������ �����ϴ� ��ü
		String sql = "SELECT * FROM score";
		int result = 0;
		try {
			stmt = conn.createStatement();//Connection��ü�� Statement�� ������ ����.
			// sqlplus���� �����ϵ��� ���ڿ��� ��� Ȭ����ǥ('')�� �����־�� �Ѵ�.
			sql = "INSERT INTO score(hak,name,birth,kor,eng,mat) VALUES ";
			sql += "('3333','�ɽ���','1999-01-10',80,90,70)"; //���� ���� ';'�� ���� �ʴ´�.
			// �����ݷ��� sqlplus�� sql developer���� ����� ���� �����ϱ� ���ؼ� ����ϴ� ���̴�.
			// �����ݷ��� �����ϸ� ORA-00933: SQL ��ɾ �ùٸ��� ������� �ʾҽ��ϴ� �޽����� ���.
			result = stmt.executeUpdate(sql); //������ ��� ������ �����ϴ� ���� 
			System.out.println(result + "���� �߰��Ǿ����ϴ�.");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
