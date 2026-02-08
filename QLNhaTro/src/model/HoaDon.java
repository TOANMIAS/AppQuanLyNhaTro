package model;

import java.util.Date;

public class HoaDon {
	private int maHoaDon;
	private String maPhong;
	private Date ngayLap;
	private double tienPhong;
	private double tienDichVu;
	private double tongTien;
	private int trangThai; // 0: chưa thanh toán | 1: đã thanh toán

	// ===== Constructor =====
	public HoaDon() {
	}

	public HoaDon(int maHoaDon, String maPhong, Date ngayLap, double tienPhong, double tienDichVu, double tongTien,
			int trangThai) {
		this.maHoaDon = maHoaDon;
		this.maPhong = maPhong;
		this.ngayLap = ngayLap;
		this.tienPhong = tienPhong;
		this.tienDichVu = tienDichVu;
		this.tongTien = tongTien;
		this.trangThai = trangThai;
	}

	// ===== Getter & Setter =====
	public int getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(int maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public double getTienPhong() {
		return tienPhong;
	}

	public void setTienPhong(double tienPhong) {
		this.tienPhong = tienPhong;
	}

	public double getTienDichVu() {
		return tienDichVu;
	}

	public void setTienDichVu(double tienDichVu) {
		this.tienDichVu = tienDichVu;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
}
