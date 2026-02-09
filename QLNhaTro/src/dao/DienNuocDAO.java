package dao;

import java.sql.*;
import model.DienNuoc;
import util.DBConnection;

public class DienNuocDAO {

	// KHÁCH NHẬP ĐIỆN NƯỚC
	public static void luuDienNuoc(DienNuoc dn) {

		String sql = """
				    INSERT INTO DIEN_NUOC(MaPhong, Thang, Nam, SoDien, SoNuoc)
				    VALUES (?, ?, ?, ?, ?)
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, dn.getMaPhong());
			ps.setInt(2, dn.getThang());
			ps.setInt(3, dn.getNam());
			ps.setInt(4, dn.getSoDien());
			ps.setInt(5, dn.getSoNuoc());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
