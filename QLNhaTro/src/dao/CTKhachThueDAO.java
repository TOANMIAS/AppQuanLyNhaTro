package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class CTKhachThueDAO {

	// GÁN KHÁCH VÀO PHÒNG (KHI ADMIN DUYỆT)
	public static void themKhachVaoPhong(int maKhach, String maPhong) {
		String sql = """
				    IF NOT EXISTS (
				        SELECT 1 FROM CT_KHACH_THUE
				        WHERE MaKhach=? AND MaPhong=?
				    )
				    INSERT INTO CT_KHACH_THUE (MaKhach, MaPhong, NgayVaoPhong)
				    VALUES (?, ?, GETDATE())
				""";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, maKhach);
			ps.setString(2, maPhong);
			ps.setInt(3, maKhach);
			ps.setString(4, maPhong);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getMaPhongByUser(int maKhach) {
		String sql = """
				    SELECT TOP 1 MaPhong
				    FROM CT_KHACH_THUE
				    WHERE MaKhach = ?
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maKhach);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getString("MaPhong");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
