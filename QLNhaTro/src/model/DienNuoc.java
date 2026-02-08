package model;

public class DienNuoc {

	private String maPhong;
	private int thang;
	private int nam;
	private int soDien;
	private int soNuoc;

	// ===== GET =====
	public String getMaPhong() {
		return maPhong;
	}

	public int getThang() {
		return thang;
	}

	public int getNam() {
		return nam;
	}

	public int getSoDien() {
		return soDien;
	}

	public int getSoNuoc() {
		return soNuoc;
	}

	// ===== SET =====
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public void setThang(int thang) {
		this.thang = thang;
	}

	public void setNam(int nam) {
		this.nam = nam;
	}

	public void setSoDien(int soDien) {
		this.soDien = soDien;
	}

	public void setSoNuoc(int soNuoc) {
		this.soNuoc = soNuoc;
	}
}
