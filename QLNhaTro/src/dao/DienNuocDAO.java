package dao;

import java.sql.*;
import model.DienNuoc;
import util.DBConnection;

public class DienNuocDAO {

	// ================== NHẬP ĐIỆN NƯỚC ĐẦU THÁNG ==================
	public static void nhapChiSo(DienNuoc dn) {

		String sql = """
				    MERGE DIEN_NUOC AS T
				    USING (SELECT ? AS MaPhong, ? AS Thang, ? AS Nam) S
				    ON (T.MaPhong = S.MaPhong AND T.Thang = S.Thang AND T.Nam = S.Nam)
				    WHEN MATCHED THEN
				        UPDATE SET SoDien = ?, SoNuoc = ?
				    WHEN NOT MATCHED THEN
				        INSERT (MaPhong, Thang, Nam, SoDien, SoNuoc)
				        VALUES (?, ?, ?, ?, ?)
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			// KEY
			ps.setString(1, dn.getMaPhong());
			ps.setInt(2, dn.getThang());
			ps.setInt(3, dn.getNam());

			// UPDATE
			ps.setInt(4, dn.getSoDien());
			ps.setInt(5, dn.getSoNuoc());

			// INSERT
			ps.setString(6, dn.getMaPhong());
			ps.setInt(7, dn.getThang());
			ps.setInt(8, dn.getNam());
			ps.setInt(9, dn.getSoDien());
			ps.setInt(10, dn.getSoNuoc());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================== LẤY ĐIỆN NƯỚC THEO THÁNG ==================
	public static DienNuoc get(String maPhong, int thang, int nam) {

		String sql = """
				    SELECT * FROM DIEN_NUOC
				    WHERE MaPhong = ? AND Thang = ? AND Nam = ?
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ps.setInt(2, thang);
			ps.setInt(3, nam);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				DienNuoc dn = new DienNuoc();
				dn.setMaPhong(rs.getString("MaPhong"));
				dn.setThang(rs.getInt("Thang"));
				dn.setNam(rs.getInt("Nam"));
				dn.setSoDien(rs.getInt("SoDien"));
				dn.setSoNuoc(rs.getInt("SoNuoc"));
				return dn;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
