package dao;

import java.sql.*;
import java.util.ArrayList;
import model.DichVu;
import util.DBConnection;

public class DichVuDAO {

	// KHÁCH CHỈ THẤY DỊCH VỤ KHÔNG CỐ ĐỊNH
	public static ArrayList<DichVu> getDichVuChoKhach() {
		ArrayList<DichVu> list = new ArrayList<>();

		String sql = """
				    SELECT MaDichVu, DichVu, GiaDichVu, LaCoDinh
				    FROM DICH_VU
				    WHERE LaCoDinh = 0
				""";

		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				DichVu dv = new DichVu();
				dv.setMaDichVu(rs.getString("MaDichVu"));
				dv.setDichVu(rs.getString("DichVu"));
				dv.setGiaDichVu(rs.getDouble("GiaDichVu"));
				dv.setLaCoDinh(rs.getBoolean("LaCoDinh"));
				list.add(dv);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
