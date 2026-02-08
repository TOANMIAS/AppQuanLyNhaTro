package dao;

import java.sql.*;
import java.util.ArrayList;

import model.DatPhong;
import util.DBConnection;

public class DatPhongDAO {

	/* ================= USER ĐẶT PHÒNG ================= */
	public static boolean datPhong(String maPhong, int maKhach, String noiDungHopDong) {

		String sql = """
				    INSERT INTO DAT_PHONG(
				        MaPhong,
				        MaKhach,
				        ThoiGian,
				        DaDongYDieuKhoan,
				        NoiDungHopDong,
				        TrangThai
				    )
				    VALUES (?, ?, GETDATE(), 1, ?, 0)
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ps.setInt(2, maKhach);
			ps.setString(3, noiDungHopDong);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/* ================= ADMIN ================= */

	public static ArrayList<DatPhong> getAllChoDuyet() {
		ArrayList<DatPhong> list = new ArrayList<>();

		String sql = "SELECT * FROM DAT_PHONG WHERE TrangThai = 0";

		try (Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				list.add(new DatPhong(rs.getInt("MaDatPhong"), rs.getString("MaPhong"), rs.getInt("MaKhach"),
						rs.getTimestamp("ThoiGian")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static boolean daDongYDieuKhoan(int maDatPhong) {
		String sql = "SELECT DaDongYDieuKhoan FROM DAT_PHONG WHERE MaDatPhong=?";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maDatPhong);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getBoolean(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getNoiDungHopDong(int maDatPhong) {
		String sql = "SELECT NoiDungHopDong FROM DAT_PHONG WHERE MaDatPhong=?";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maDatPhong);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void capNhatTrangThai(int maDat, int trangThai) {
		String sql = "UPDATE DAT_PHONG SET TrangThai=? WHERE MaDatPhong=? AND TrangThai=0";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, trangThai);
			ps.setInt(2, maDat);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
