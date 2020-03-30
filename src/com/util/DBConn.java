package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton Pattern
public class DBConn {
	private static Connection conn;
	private static final String URL = "jdbc:oracle:thin:@sist.sannim.com:1521:xe"; // 11g����
//	private static final String URL="jdbc:oracle:thin:@//sist.sannim.com:1521/xe";//12c�̻�
	private static final String USER = "sky";// username�� ��ҹ��ڸ� ���� �������� ����
	private static final String PASSWORD = "java$!";

	private DBConn() {
	}

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver"); // JDK6���� ���� ������ ����.
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void close() {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		conn = null;
	}

	
}
