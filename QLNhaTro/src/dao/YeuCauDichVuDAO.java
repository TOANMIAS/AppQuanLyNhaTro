package dao;

import java.sql.*;
import java.util.ArrayList;
import model.YeuCauDichVu;
import util.DBConnection;

public class YeuCauDichVuDAO {

	// KHÁCH GỬI YÊU CẦU
	public static void guiYeuCau(YeuCauDichVu yc) {

		String sql = """
				    INSERT INTO YEU_CAU_DICH_VU
				    (MaKhach, MaPhong, MaDichVu, TenDichVu, GiaTien, SoLuong, TrangThai, NgayYeuCau)
				    VALUES (?, ?, ?, ?, ?, ?, 0, GETDATE())
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, yc.getMaKhach());
			ps.setString(2, yc.getMaPhong());
			ps.setString(3, yc.getMaDichVu());
			ps.setString(4, yc.getTenDichVu());
			ps.setDouble(5, yc.getGiaTien());
			ps.setInt(6, yc.getSoLuong());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ADMIN XEM CHỜ DUYỆT
	public static ArrayList<YeuCauDichVu> getChoDuyet() {

		ArrayList<YeuCauDichVu> list = new ArrayList<>();

		String sql = "SELECT * FROM YEU_CAU_DICH_VU WHERE TrangThai = 0";

		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaYeuCau(rs.getInt("MaYeuCau"));
				yc.setMaPhong(rs.getString("MaPhong"));
				yc.setMaDichVu(rs.getString("MaDichVu"));
				yc.setTenDichVu(rs.getString("TenDichVu"));
				yc.setSoLuong(rs.getInt("SoLuong"));
				list.add(yc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static YeuCauDichVu getById(int maYC) {

		String sql = "SELECT * FROM YEU_CAU_DICH_VU WHERE MaYeuCau = ?";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maYC);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaYeuCau(maYC);
				yc.setMaPhong(rs.getString("MaPhong"));
				yc.setMaDichVu(rs.getString("MaDichVu"));
				yc.setSoLuong(rs.getInt("SoLuong"));
				return yc;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void capNhatTrangThai(int maYC, int trangThai) {

		String sql = """
				    UPDATE YEU_CAU_DICH_VU
				    SET TrangThai = ?, NgayDuyet = GETDATE()
				    WHERE MaYeuCau = ?
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, trangThai);
			ps.setInt(2, maYC);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
