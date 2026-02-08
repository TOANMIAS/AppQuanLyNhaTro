package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QUANLYPHONGTRO;encrypt=true;trustServerCertificate=true";
	private static final String USER = "sa";
	private static final String PASS = "123456";

	public static Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Connection conn = getConnection();

		if (conn != null) {
			System.out.println("✅ KẾT NỐI SQL SERVER THÀNH CÔNG!");
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("❌ KẾT NỐI SQL SERVER THẤT BẠI!");
		}
	}
}
