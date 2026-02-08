package dao;

import java.sql.*;
import java.util.ArrayList;

import model.DienNuoc;
import model.HoaDon;
import util.DBConnection;

public class HoaDonDAO {

	/* ================== TẠO / LẤY HÓA ĐƠN THÁNG ================== */
	private static int getOrCreateHoaDon(String maPhong, int thang, int nam) {

		String sqlSelect = """
				    SELECT MaHoaDon
				    FROM HOA_DON
				    WHERE MaPhong = ?
				      AND MONTH(NgayLap) = ?
				      AND YEAR(NgayLap) = ?
				""";

		String sqlInsert = """
				    INSERT INTO HOA_DON (MaPhong, NgayLap, TrangThaiThanhToan)
				    VALUES (?, GETDATE(), 0)
				""";

		try (Connection c = DBConnection.getConnection()) {

			// 1️⃣ kiểm tra tồn tại
			try (PreparedStatement ps = c.prepareStatement(sqlSelect)) {
				ps.setString(1, maPhong);
				ps.setInt(2, thang);
				ps.setInt(3, nam);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt("MaHoaDon");
				}
			}

			// 2️⃣ tạo mới
			try (PreparedStatement ps = c.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, maPhong);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/* ================== TÍNH HÓA ĐƠN THÁNG ================== */
	public static void tinhHoaDonThang(String maPhong, int thang, int nam) {

		int maHD = getOrCreateHoaDon(maPhong, thang, nam);
		if (maHD == -1)
			return;

		// 1️⃣ DỊCH VỤ CỐ ĐỊNH (đã duyệt)
		tinhDichVuCoDinh(maHD, maPhong);

		// 2️⃣ ĐIỆN + NƯỚC THEO THÁNG
		DienNuoc dn = DienNuocDAO.get(maPhong, thang, nam);
		if (dn != null) {
			congTien(maHD, "DIEN", dn.getSoDien());
			congTien(maHD, "NUOC", dn.getSoNuoc());
		}
	}

	/* ================== DỊCH VỤ CỐ ĐỊNH ================== */
	private static void tinhDichVuCoDinh(int maHD, String maPhong) {

		String sql = """
				    INSERT INTO CT_DICHVU (MaHoaDon, MaDichVu, DonViSuDung)
				    SELECT ?, dv.MaDichVu, 1
				    FROM YEU_CAU_DICH_VU yc
				    JOIN DICH_VU dv ON yc.MaDichVu = dv.MaDichVu
				    WHERE yc.MaPhong = ?
				      AND yc.TrangThai = 1
				      AND dv.MaLoaiDichVu = 'CD'
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maHD);
			ps.setString(2, maPhong);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ================== CỘNG TIỀN ĐIỆN / NƯỚC ================== */
	public static void congTien(int maHD, String maDV, int soLuong) {

		String sql = """
				    INSERT INTO CT_DICHVU (MaHoaDon, MaDichVu, DonViSuDung)
				    VALUES (?, ?, ?)
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maHD);
			ps.setString(2, maDV);
			ps.setInt(3, soLuong);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ================== ADMIN DUYỆT DỊCH VỤ ================== */
	public static void congTienDichVu(int maYC) {
		// Dịch vụ cố định đã được tính qua tinhDichVuCoDinh
		// Giữ hàm này để KHỚP luồng admin
	}

	/* ================== USER XEM HÓA ĐƠN ================== */
	public ArrayList<HoaDon> getHoaDonByMaKhach(int maKhach) {

		ArrayList<HoaDon> list = new ArrayList<>();

		String sql = """
				    SELECT hd.MaHoaDon, hd.MaPhong, hd.NgayLap,
				           hd.TrangThaiThanhToan,
				           ISNULL(SUM(ct.DonViSuDung * dv.GiaDichVu),0) AS TongTien
				    FROM HOA_DON hd
				    JOIN PHONG_TRO p ON hd.MaPhong = p.MaPhong
				    JOIN CT_KHACH_THUE ctkt ON p.MaPhong = ctkt.MaPhong
				    LEFT JOIN CT_DICHVU ct ON hd.MaHoaDon = ct.MaHoaDon
				    LEFT JOIN DICH_VU dv ON ct.MaDichVu = dv.MaDichVu
				    WHERE ctkt.MaKhach = ?
				    GROUP BY hd.MaHoaDon, hd.MaPhong, hd.NgayLap, hd.TrangThaiThanhToan
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maKhach);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				HoaDon h = new HoaDon();
				h.setMaHoaDon(rs.getInt("MaHoaDon"));
				h.setMaPhong(rs.getString("MaPhong"));
				h.setNgayLap(rs.getDate("NgayLap"));
				h.setTongTien(rs.getDouble("TongTien"));
				h.setTrangThai(rs.getInt("TrangThaiThanhToan"));
				list.add(h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/* ================== THANH TOÁN ================== */
	public boolean thanhToanHoaDon(int maHoaDon) {

		String sql = """
				    UPDATE HOA_DON
				    SET TrangThaiThanhToan = 1,
				        NgayThanhToan = GETDATE()
				    WHERE MaHoaDon = ?
				""";

		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, maHoaDon);
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
