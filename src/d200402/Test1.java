package d200402;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class Test1 {
	public static void main(String[] args) {
		// 트랜잭션 예제
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;

		String sql;
		String id, name, birth, tel;
		try {
			System.out.print("아이디 ? ");
			id = br.readLine();
			System.out.print("이름 ? ");
			name = br.readLine();
			System.out.print("생일 ? ");
			birth = br.readLine();
			System.out.print("전화번호 ? ");
			tel = br.readLine();

			// ■■■■■
			// 자동 COMMIT 되지 않도록 설정(기본: 자동 commit)
			conn.setAutoCommit(false);

			// test1 테이블 삽입
			sql = "INSERT INTO test1(id, name) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			pstmt.close();

			// test2 테이블 삽입
			sql = "INSERT INTO test2(id, birth) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, birth);
			pstmt.executeUpdate();
			pstmt.close();

			// test3 테이블 삽입
			sql = "INSERT INTO test3(id, tel) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, tel);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(sql);

			// ■■■■■ COMMIT ■■■■■
			conn.commit();
			System.out.println("추가 성공!");

		} catch (SQLException e) {
			// ■■■■■ ROLLBACK ■■■■■
			// 트랜잭션 처리 관련된 부분은 반드시 SQLException으로 예외를 잡아주어야 한다.
			try {
				conn.rollback();// 실패 시 롤백도 수동으로 처리해야 한다.
			} catch (Exception e2) {
			}
//			e.printStackTrace();
			System.out.println("추가 실패 " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {// ■■■■■
				conn.setAutoCommit(true); // autocommit설정을 원래대로 복구해 주어야 한다.
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
