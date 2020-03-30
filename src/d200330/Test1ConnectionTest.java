package d200330;

import java.sql.Connection;

import com.util.DBConn;

public class Test1ConnectionTest {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		System.out.println(conn);
	}
}
