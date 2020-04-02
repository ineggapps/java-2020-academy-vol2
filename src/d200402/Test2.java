package d200402;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

public class Test2 {
	public static void main(String[] args) {
		//스크롤 예제
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql;

		Scanner sc = new Scanner(System.in);
		int ch;
		try {
			sql = "SELECT hak, name, birth, kor, eng, mat FROM score";
			// 순방향만 가능
//			stmt = conn.createStatement(); //순방향만 가능하다
			//순/역방향 가능 변경된 결과가 바로 반영됨. 단, 데이터는 수정 불가능
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//결과집합유형 (첫 번째 인자)
//			TYPE_FORWARD_ONLY:기본, 순방향만
//			TYPE_SCROLL_SENSITIVE: 순/역방향 가능, 데이터 변경사항 감지
//			TYPE_SCROLL_INSENSITIVE: 순/역방향 가능, 데이터 변경사항을 감지하지 않음.
			
			//동시성 유형 (두 번째 인자)
//			CONCUR_READ_ONLY: 읽기만 가능
//			CONCUR_UPDATEABLE: 수정도 가능 
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
					if (rs.first()) {
						System.out.println("처음: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 2:
					if (rs.previous()) {
						System.out.println("이전: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 3:
					if (rs.next()) {
						System.out.println("다음: " + rs.getString(1) + ", " + rs.getString(2));
					}
					break;
				case 4:
					if (rs.last()) {
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
