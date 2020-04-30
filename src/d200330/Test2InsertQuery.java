package d200330;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test2InsertQuery {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection(); //sql developer나 sqlplus에서 로그인하는 과정 - DriverManager에서 가져온다.
		Statement stmt = null; //쿼리를 실행하는 객체
		String sql = "SELECT * FROM score";
		int result = 0;
		try {
			stmt = conn.createStatement();//Connection객체에 Statement를 가지고 있음.
			// sqlplus에서 삽입하듯이 문자열의 경우 홑따옴표('')로 감싸주어야 한다.
			sql = "INSERT INTO score(hak,name,birth,kor,eng,mat) VALUES ";
			sql += "('3333','심심해','1999-01-10',80,90,70)"; //구문 내에 ';'은 넣지 않는다.
			// 세미콜론은 sqlplus나 sql developer에서 명령의 끝을 구분하기 위해서 사용하는 것이다.
			// 세미콜론을 삽입하면 ORA-00933: SQL 명령어가 올바르게 종료되지 않았습니다 메시지가 출력.
			result = stmt.executeUpdate(sql); //범위를 잡고 쿼리를 실행하는 과정 
			System.out.println(result + "행이 추가되었습니다.");
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
