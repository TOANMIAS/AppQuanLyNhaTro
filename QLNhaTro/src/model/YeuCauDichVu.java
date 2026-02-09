package model;

import java.util.Date;

public class YeuCauDichVu {
	private int maYeuCau;
	private int maKhach;
	private String maPhong;
	private String maDichVu;
	private String tenDichVu;
	private double giaTien;
	private int soLuong;
	private int trangThai;
	private Date ngayYeuCau;
	private Date ngayDuyet;

	public int getMaYeuCau() {
		return maYeuCau;
	}

	public void setMaYeuCau(int maYeuCau) {
		this.maYeuCau = maYeuCau;
	}

	public int getMaKhach() {
		return maKhach;
	}

	public void setMaKhach(int maKhach) {
		this.maKhach = maKhach;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public double getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(double giaTien) {
		this.giaTien = giaTien;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public Date getNgayYeuCau() {
		return ngayYeuCau;
	}

	public void setNgayYeuCau(Date ngayYeuCau) {
		this.ngayYeuCau = ngayYeuCau;
	}

	public Date getNgayDuyet() {
		return ngayDuyet;
	}

	public void setNgayDuyet(Date ngayDuyet) {
		this.ngayDuyet = ngayDuyet;
	}
}
