package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import util.DBConnection;

public class HopDongDAO {

	public static boolean taoHopDong(String maPhong, int maDatPhong, String noiDung) {
		String sql = """
				    INSERT INTO HOP_DONG(NgayLap, MaPhong, MaDatPhong, NoiDung)
				    VALUES (GETDATE(), ?, ?, ?)
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ps.setInt(2, maDatPhong);
			ps.setString(3, noiDung);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
