package dao;

import java.sql.*;
import java.util.ArrayList;
import model.HoaDon;
import util.DBConnection;

public class HoaDonDAO {

	// KHÁCH XEM HÓA ĐƠN
	public static ArrayList<HoaDon> getHoaDonByPhong(String maPhong) {
		ArrayList<HoaDon> list = new ArrayList<>();

		String sql = """
				    SELECT hd.MaHoaDon, hd.NgayLap, hd.MaPhong, hd.TrangThaiThanhToan,
				           ISNULL(SUM(ct.DonViSuDung * dv.GiaDichVu),0)
				           + pt.GiaThue AS TongTien
				    FROM HOA_DON hd
				    JOIN PHONG_TRO pt ON hd.MaPhong = pt.MaPhong
				    LEFT JOIN CT_DICHVU ct ON hd.MaHoaDon = ct.MaHoaDon
				    LEFT JOIN DICH_VU dv ON ct.MaDichVu = dv.MaDichVu
				    WHERE hd.MaPhong = ?
				    GROUP BY hd.MaHoaDon, hd.NgayLap, hd.MaPhong,
				             hd.TrangThaiThanhToan, pt.GiaThue
				    ORDER BY hd.NgayLap DESC
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				HoaDon hd = new HoaDon();
				hd.setMaHoaDon(rs.getInt("MaHoaDon"));
				hd.setNgayLap(rs.getDate("NgayLap"));
				hd.setMaPhong(rs.getString("MaPhong"));
				hd.setTrangThaiThanhToan(rs.getBoolean("TrangThaiThanhToan"));
				hd.setTongTien(rs.getDouble("TongTien"));
				list.add(hd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int getMaHoaDonChuaThanhToan(String maPhong) {

		String sql = """
				    SELECT TOP 1 MaHoaDon
				    FROM HOA_DON
				    WHERE MaPhong = ? AND TrangThaiThanhToan = 0
				    ORDER BY NgayLap DESC
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// THANH TOÁN
	public static void thanhToan(int maHoaDon) {
		String sql = """
				    UPDATE HOA_DON
				    SET TrangThaiThanhToan = 1,
				        NgayThanhToan = GETDATE()
				    WHERE MaHoaDon = ?
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maHoaDon);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
