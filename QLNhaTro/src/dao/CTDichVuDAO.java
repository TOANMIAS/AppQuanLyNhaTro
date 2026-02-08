package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.CTDichVu;
import util.DBConnection;

public class CTDichVuDAO {

	public void themDichVu(CTDichVu ct) {
		String sql = """
				    INSERT INTO CT_DICHVU (MaHoaDon, MaDichVu, DonViSuDung)
				    VALUES (?, ?, ?)
				""";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, ct.getMaHoaDon());
			ps.setString(2, ct.getMaDichVu()); // ✅ STRING
			ps.setInt(3, ct.getDonViSuDung());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void capNhatTrangThai(int maYC, int trangThai) {

		String sql = "UPDATE YEU_CAU_DICH_VU SET TrangThai=? WHERE MaYC=?";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, trangThai);
			ps.setInt(2, maYC);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void themDichVuTuYeuCau(int maYC) {

		String sql = """
				    INSERT INTO CT_DICHVU (MaHoaDon, MaDichVu, DonViSuDung)
				    SELECT HD.MaHoaDon, YC.MaDichVu, YC.SoLuong
				    FROM YEU_CAU_DICH_VU YC
				    JOIN HOA_DON HD ON HD.MaPhong = YC.MaPhong
				    WHERE YC.MaYC = ?
				      AND MONTH(HD.NgayLap) = MONTH(GETDATE())
				      AND YEAR(HD.NgayLap) = YEAR(GETDATE())
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maYC);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
