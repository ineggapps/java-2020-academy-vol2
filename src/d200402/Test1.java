package d200402;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class Test1 {
	public static void main(String[] args) {
		// Ʈ����� ����
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;

		String sql;
		String id, name, birth, tel;
		try {
			System.out.print("���̵� ? ");
			id = br.readLine();
			System.out.print("�̸� ? ");
			name = br.readLine();
			System.out.print("���� ? ");
			birth = br.readLine();
			System.out.print("��ȭ��ȣ ? ");
			tel = br.readLine();

			// ������
			// �ڵ� COMMIT ���� �ʵ��� ����(�⺻: �ڵ� commit)
			conn.setAutoCommit(false);

			// test1 ���̺� ����
			sql = "INSERT INTO test1(id, name) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			pstmt.close();

			// test2 ���̺� ����
			sql = "INSERT INTO test2(id, birth) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, birth);
			pstmt.executeUpdate();
			pstmt.close();

			// test3 ���̺� ����
			sql = "INSERT INTO test3(id, tel) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, tel);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(sql);

			// ������ COMMIT ������
			conn.commit();
			System.out.println("�߰� ����!");

		} catch (SQLException e) {
			// ������ ROLLBACK ������
			// Ʈ����� ó�� ���õ� �κ��� �ݵ�� SQLException���� ���ܸ� ����־�� �Ѵ�.
			try {
				conn.rollback();// ���� �� �ѹ鵵 �������� ó���ؾ� �Ѵ�.
			} catch (Exception e2) {
			}
//			e.printStackTrace();
			System.out.println("�߰� ���� " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {// ������
				conn.setAutoCommit(true); // autocommit������ ������� ������ �־�� �Ѵ�.
			} catch (Exception e2) {
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
