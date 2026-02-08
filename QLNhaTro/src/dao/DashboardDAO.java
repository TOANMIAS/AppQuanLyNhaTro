package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

	public static int countPhong() {
		return getCount("SELECT COUNT(*) FROM PHONG_TRO");
	}

	public static int countPhongDangThue() {
		return getCount("SELECT COUNT(*) FROM PHONG_TRO WHERE TrangThai = 1");
	}

	public static int countPhongTrong() {
		return getCount("SELECT COUNT(*) FROM PHONG_TRO WHERE TrangThai = 0");
	}

	public static int countKhach() {
		return getCount("SELECT COUNT(*) FROM KHACH_THUE");
	}

	public static int countHoaDon() {
		return getCount("SELECT COUNT(*) FROM HOA_DON");
	}

	private static int getCount(String sql) {
		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next())
				return rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
