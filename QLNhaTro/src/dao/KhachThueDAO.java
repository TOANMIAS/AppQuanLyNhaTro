package dao;

import model.KhachThue;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachThueDAO {

	public List<KhachThue> getAll() {
		List<KhachThue> list = new ArrayList<>();
		String sql = "SELECT * FROM KHACH_THUE";

		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				KhachThue k = new KhachThue();
				k.setMaKhach(rs.getInt("MaKhach"));
				k.setTenKhach(rs.getString("TenKhach"));
				k.setPhai(rs.getString("Phai"));
				k.setCmnd(rs.getString("CMND"));
				k.setQueQuan(rs.getString("QueQuan"));
				k.setNgheNghiep(rs.getString("NgheNghiep"));
				list.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// LẤY THÔNG TIN KHÁCH THUÊ
	// LẤY KHÁCH THUÊ THEO MÃ
	public static KhachThue getById(int maKhach) {

		String sql = "SELECT * FROM KHACH_THUE WHERE MaKhach = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, maKhach);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				KhachThue k = new KhachThue();
				k.setMaKhach(rs.getInt("MaKhach"));
				k.setTenKhach(rs.getString("TenKhach"));
				k.setCmnd(rs.getString("CMND"));
				k.setQueQuan(rs.getString("QueQuan"));
				k.setNgheNghiep(rs.getString("NgheNghiep"));
				return k;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean insert(KhachThue k) {
		String sql = "{CALL ThemKhachThue(?,?,?,?,?,NULL,NULL)}";
		try (Connection c = DBConnection.getConnection(); CallableStatement cs = c.prepareCall(sql)) {

			cs.setString(1, k.getTenKhach());
			cs.setString(2, k.getPhai());
			cs.setString(3, k.getCmnd());
			cs.setString(4, k.getQueQuan());
			cs.setString(5, k.getNgheNghiep());
			cs.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(KhachThue k) {
		String sql = "{CALL SuaKhachThue(?,?,?,?,?,?)}";
		try (Connection c = DBConnection.getConnection(); CallableStatement cs = c.prepareCall(sql)) {

			cs.setInt(1, k.getMaKhach());
			cs.setString(2, k.getTenKhach());
			cs.setString(3, k.getPhai());
			cs.setString(4, k.getCmnd());
			cs.setString(5, k.getQueQuan());
			cs.setString(6, k.getNgheNghiep());
			cs.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int maKhach) {
		String sql = "{CALL XoaKhach(?)}";
		try (Connection c = DBConnection.getConnection(); CallableStatement cs = c.prepareCall(sql)) {

			cs.setInt(1, maKhach);
			cs.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
