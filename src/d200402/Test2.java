package d200402;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

public class Test2 {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql;

		Scanner sc = new Scanner(System.in);
		int ch;
		try {
			sql = "SELECT hak, name, birth, kor, eng, mat FROM score";
			// 순방향만 가능
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (true) {
				do {
					System.out.print("1.처음 2.이전 3.다음 4.끝 5.종료 =>");
					ch = sc.nextInt();
				} while (ch < 1 || ch > 5);
				if (ch == 5) {
					break;
				}
				switch (ch) {
				case 1:
					if (rs.first()) {//java.sql.SQLException: 전방향 전용 결과 집합에 부적합한 작업이 수행되었습니다 : first
						System.out.println("처음: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 2:
					if (rs.previous()) {//java.sql.SQLException: 전방향 전용 결과 집합에 부적합한 작업이 수행되었습니다 : previous
						System.out.println("이전: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 3:
					if (rs.next()) {
						System.out.println("다음: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 4:
					if (rs.last()) {//java.sql.SQLException: 전방향 전용 결과 집합에 부적합한 작업이 수행되었습니다 : last
						System.out.println("끝: " + rs.getString(1) + ", " + rs.getString(2));
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
