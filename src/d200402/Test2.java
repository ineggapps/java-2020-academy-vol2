package d200402;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

public class Test2 {
	public static void main(String[] args) {
		//��ũ�� ����
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql;

		Scanner sc = new Scanner(System.in);
		int ch;
		try {
			sql = "SELECT hak, name, birth, kor, eng, mat FROM score";
			// �����⸸ ����
//			stmt = conn.createStatement(); //�����⸸ �����ϴ�
			//��/������ ���� ����� ����� �ٷ� �ݿ���. ��, �����ʹ� ���� �Ұ���
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//����������� (ù ��° ����)
//			TYPE_FORWARD_ONLY:�⺻, �����⸸
//			TYPE_SCROLL_SENSITIVE: ��/������ ����, ������ ������� ����
//			TYPE_SCROLL_INSENSITIVE: ��/������ ����, ������ ��������� �������� ����.
			
			//���ü� ���� (�� ��° ����)
//			CONCUR_READ_ONLY: �б⸸ ����
//			CONCUR_UPDATEABLE: ������ ���� 
			rs = stmt.executeQuery(sql);
			while (true) {
				do {
					System.out.print("1.ó�� 2.���� 3.���� 4.�� 5.���� =>");
					ch = sc.nextInt();
				} while (ch < 1 || ch > 5);
				if (ch == 5) {
					break;
				}
				switch (ch) {
				case 1:
					if (rs.first()) {
						System.out.println("ó��: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 2:
					if (rs.previous()) {
						System.out.println("����: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 3:
					if (rs.next()) {
						System.out.println("����: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 4:
					if (rs.last()) {
						System.out.println("��: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.close();
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
	}
}
