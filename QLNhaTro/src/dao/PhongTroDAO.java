package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.PhongTro;
import util.DBConnection;

public class PhongTroDAO {

	// ================== LẤY TẤT CẢ PHÒNG ==================
	public static ArrayList<PhongTro> getAll() {
		ArrayList<PhongTro> list = new ArrayList<>();
		String sql = "SELECT * FROM PHONG_TRO";

		try (Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				list.add(new PhongTro(rs.getString("MaPhong"), rs.getInt("TrangThai"), rs.getString("ThongTinPhong"),
						rs.getInt("SoNguoi"), rs.getDouble("GiaThue") // ✅ GIÁ THUÊ TRONG PHONG_TRO
				));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ================== PHÒNG TRỐNG ==================
	public static ArrayList<PhongTro> getPhongTrong() {
		ArrayList<PhongTro> list = new ArrayList<>();
		String sql = "SELECT * FROM PHONG_TRO WHERE TrangThai = 0";

		try (Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				list.add(new PhongTro(rs.getString("MaPhong"), rs.getInt("TrangThai"), rs.getString("ThongTinPhong"),
						rs.getInt("SoNguoi"), rs.getDouble("GiaThue")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ================== CHO USER XEM (CÓ GIÁ) ==================
	public static ArrayList<Object[]> getPhongChoUser() {
		ArrayList<Object[]> list = new ArrayList<>();
		String sql = "SELECT MaPhong, SoNguoi, GiaThue, TrangThai FROM PHONG_TRO";

		try (Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				list.add(new Object[] { rs.getString("MaPhong"), rs.getInt("SoNguoi"), rs.getDouble("GiaThue"),
						rs.getInt("TrangThai") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// LẤY PHÒNG USER ĐANG THUÊ
	public static PhongTro getPhongDangThue(int maKhach) {
		String sql = """
				    SELECT p.*
				    FROM PHONG_TRO p
				    JOIN CT_KHACH_THUE ct ON p.MaPhong = ct.MaPhong
				    WHERE ct.MaKhach = ?
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, maKhach);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new PhongTro(rs.getString("MaPhong"), rs.getInt("TrangThai"), rs.getString("ThongTinPhong"),
						rs.getInt("SoNguoi"), rs.getDouble("GiaThue"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ================== THÊM PHÒNG ==================
	public static void insert(PhongTro p) {
		String sql = "INSERT INTO PHONG_TRO VALUES (?,?,?,?,?)";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, p.getMaPhong());
			ps.setInt(2, p.getTrangThai());
			ps.setString(3, p.getThongTinPhong());
			ps.setInt(4, p.getSoNguoi());
			ps.setDouble(5, p.getGiaThue());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================== CẬP NHẬT PHÒNG ==================
	public static void update(PhongTro p) {
		String sql = """
					UPDATE PHONG_TRO
					SET TrangThai=?, ThongTinPhong=?, SoNguoi=?, GiaThue=?
					WHERE MaPhong=?
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, p.getTrangThai());
			ps.setString(2, p.getThongTinPhong());
			ps.setInt(3, p.getSoNguoi());
			ps.setDouble(4, p.getGiaThue());
			ps.setString(5, p.getMaPhong());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================== XÓA PHÒNG ==================
	public static void delete(String maPhong) {
		String sql = "DELETE FROM PHONG_TRO WHERE MaPhong=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================== ĐỔI TRẠNG THÁI ==================
	public static boolean capNhatTrangThai(String maPhong, int trangThai) {
		String sql = "UPDATE PHONG_TRO SET TrangThai=? WHERE MaPhong=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, trangThai);
			ps.setString(2, maPhong);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// ================== SINH MÃ PHÒNG ==================
	public static String taoMaPhong() {
		String sql = "SELECT MAX(MaPhong) FROM PHONG_TRO";
		try (Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			if (rs.next() && rs.getString(1) != null) {
				String max = rs.getString(1); // P007
				int num = Integer.parseInt(max.substring(1));
				return String.format("P%03d", num + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "P001";
	}

	// LẤY PHÒNG THEO MÃ PHÒNG
	public static PhongTro getByMaPhong(String maPhong) {

		String sql = "SELECT * FROM PHONG_TRO WHERE MaPhong = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, maPhong);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new PhongTro(rs.getString("MaPhong"), rs.getInt("TrangThai"), rs.getString("ThongTinPhong"),
						rs.getInt("SoNguoi"), rs.getDouble("GiaThue"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
