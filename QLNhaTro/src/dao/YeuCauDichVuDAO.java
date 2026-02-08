package dao;

import java.sql.*;
import java.util.*;
import model.YeuCauDichVu;
import util.DBConnection;

public class YeuCauDichVuDAO {
	public static void guiYeuCau(YeuCauDichVu yc) {

		String sql = """
				    INSERT INTO YEU_CAU_DICH_VU
				    (MaPhong, MaDV, SoLuong, TrangThai)
				    VALUES (?, ?, ?, 0)
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, yc.getMaPhong());
			ps.setString(2, yc.getMaDichVu()); // MaDV
			ps.setInt(3, yc.getSoLuong());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<YeuCauDichVu> getByPhong(String maPhong) {
		List<YeuCauDichVu> list = new ArrayList<>();
		String sql = "SELECT * FROM YEU_CAU_DICH_VU WHERE MaPhong=?";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaYC(rs.getInt("MaYC"));
				yc.setMaPhong(rs.getString("MaPhong"));
				yc.setMaDichVu(rs.getString("MaDichVu"));
				yc.setSoLuong(rs.getInt("SoLuong"));
				yc.setTrangThai(rs.getInt("TrangThai"));
				list.add(yc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<YeuCauDichVu> getChoDuyet() {
		List<YeuCauDichVu> list = new ArrayList<>();
		String sql = "SELECT * FROM YEU_CAU_DICH_VU WHERE TrangThai=0";

		try (Connection c = DBConnection.getConnection();
				Statement st = c.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaYC(rs.getInt("MaYC"));
				yc.setMaPhong(rs.getString("MaPhong"));
				yc.setMaDichVu(rs.getString("MaDichVu"));
				yc.setSoLuong(rs.getInt("SoLuong"));
				list.add(yc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static YeuCauDichVu getById(int maYC) {

		String sql = "SELECT * FROM YEU_CAU_DICH_VU WHERE MaYC = ?";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maYC);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaYC(rs.getInt("MaYC"));
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

}
