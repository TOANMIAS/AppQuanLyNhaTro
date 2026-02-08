package dao;

import java.sql.*;
import model.User;
import util.DBConnection;

public class UserDAO {

	// ĐĂNG NHẬP
	public User login(String username, String password) {
		String sql = """
				    SELECT * FROM USER_KHACHTHUE
				    WHERE Username=? AND Pwd=? AND TinhTrang=1
				""";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = new User();
				u.setUserID(rs.getInt("UserID"));
				u.setUsername(rs.getString("Username"));
				u.setAdmin(rs.getBoolean("IsAdmin"));
				u.setTinhTrang(rs.getBoolean("TinhTrang"));
				u.setMaKhach(rs.getInt("MaKhach"));
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ĐĂNG KÝ
	public boolean register(String tenKhach, String cmnd, String username, String password) {

		String sql = "{CALL ThemKhachThue(?,?,?,?,?, ?,?)}";

		try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

			cs.setString(1, tenKhach);
			cs.setString(2, null);
			cs.setString(3, cmnd);
			cs.setString(4, null);
			cs.setString(5, null);
			cs.setString(6, username);
			cs.setString(7, password);

			cs.execute();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean doiMatKhau(int userId, String oldPwd, String newPwd) {
		String sql = """
				    UPDATE USER_KHACHTHUE
				    SET Pwd = ?
				    WHERE UserID = ? AND Pwd = ?
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, newPwd);
			ps.setInt(2, userId);
			ps.setString(3, oldPwd);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
