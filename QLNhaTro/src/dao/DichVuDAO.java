package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.DichVu;
import util.DBConnection;

public class DichVuDAO {

	public static ArrayList<DichVu> getAllChoUser() {
		ArrayList<DichVu> list = new ArrayList<>();

		String sql = """
				    SELECT MaDV, TenDichVu, GiaDichVu
				    FROM DICH_VU
				    WHERE MaLoaiDichVu <> 'DIEN'
				      AND MaLoaiDichVu <> 'NUOC'
				""";

		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				DichVu dv = new DichVu();
				dv.setMaDichVu(rs.getString("MaDV")); // ✅
				dv.setTenDichVu(rs.getString("TenDichVu"));
				dv.setGiaDichVu(rs.getDouble("GiaDichVu"));
				list.add(dv);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// CHỈ LẤY DỊCH VỤ CỐ ĐỊNH (USER)
	public ArrayList<DichVu> getDichVuCoDinh() {

		ArrayList<DichVu> list = new ArrayList<>();

		String sql = """
				    SELECT MaDichVu, DichVu, GiaDichVu
				    FROM DICH_VU
				    WHERE MaLoaiDichVu = 'CD'
				""";

		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new DichVu(rs.getString("MaDV"), rs.getString("TenDichVu"), // ✅ SỬA Ở ĐÂY
						rs.getDouble("GiaDichVu")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getTenByMa(String maDV) {
		String sql = "SELECT DichVu FROM DICH_VU WHERE MaDichVu=?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, maDV);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
