package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;
import model.YeuCauDichVu;

public class CTDichVuDAO {

	public static void themDichVuVaoHoaDon(int maHoaDon, YeuCauDichVu yc) {

		String sql = """
				    INSERT INTO CT_DICHVU (MaHoaDon, MaDichVu, DonViSuDung)
				    VALUES (?, ?, ?)
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maHoaDon);
			ps.setString(2, yc.getMaDichVu());
			ps.setInt(3, yc.getSoLuong());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
