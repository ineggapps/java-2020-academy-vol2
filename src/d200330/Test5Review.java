package d200330;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.util.DBConn;

/*
CREATE TABLE score_rv(
    hak VARCHAR2(30) PRIMARY KEY,
    name VARCHAR2(30) NOT NULL,
    birth DATE NOT NULL,
    kor NUMBER(3) NOT NULL,
    mat NUMBER(3) NOT NULL,
    eng NUMBER(3) NOT NULL
);
 
 이름    널?       유형           
----- -------- ------------ 
HAK   NOT NULL VARCHAR2(30) 
NAME  NOT NULL VARCHAR2(30) 
BIRTH NOT NULL DATE         
KOR   NOT NULL NUMBER(3)    
MAT   NOT NULL NUMBER(3)    
ENG   NOT NULL NUMBER(3)
*/

public class Test5Review {
	public static void main(String[] args) {
		DAO dao = new DAO();
		dao.resetTable();
		dao.print();
		dao.insert("1111", "홍길동", "2020-01-01", 80, 90, 90);
		dao.insert("2222", "심심해", "2020-04-03", 99, 80, 100);
		dao.insert("3333", "박사장", "2020-02-22", 94, 48, 77);
		dao.print();
	}
}

class DAO{
	public void print() {
		Connection conn = DBConn.getConnection();
		String sql = "SELECT hak, name, birth, kor, eng, mat, kor+eng+mat tot FROM score_rv";
		
		try(Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(sql)){
			while(result.next()) {
				String hak = result.getString("hak");
				String name = result.getString("name");
				String birth = result.getDate("birth").toString();
				int kor = result.getInt("kor");
				int eng = result.getInt("eng");
				int mat = result.getInt("mat");
				int tot = result.getInt("tot");
				System.out.println(String.format("%s %s %s %d %d %d %d", hak, name, birth, kor, eng, mat, tot));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public void insert(String hak, String name, String birth, int kor, int eng, int mat) {
		Connection conn = DBConn.getConnection();
		String sql = String.format("INSERT INTO score_rv(hak, name, birth, kor, eng, mat) VALUES('%s','%s','%s',%d,%d,%d)",
				hak, name, birth, kor, eng, mat);
		//JDK7이상 try catch구문
		try(Statement stmt = conn.createStatement()) {
			int result = stmt.executeUpdate(sql);
			System.out.println(result + "행 업데이트가 완료되었습니다.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetTable() {
		Connection conn = DBConn.getConnection();
		String sql = "truncate table score_rv";
		try (Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
			System.out.println("테이블을 초기화하였습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}