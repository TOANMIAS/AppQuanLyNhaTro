package model;

import java.util.Date;

public class DatPhong {

	private int maDatPhong;
	private String maPhong;
	private int maKhach;
	private Date thoiGian;

	public DatPhong(int maDatPhong, String maPhong, int maKhach, Date thoiGian) {
		this.maDatPhong = maDatPhong;
		this.maPhong = maPhong;
		this.maKhach = maKhach;
		this.thoiGian = thoiGian;
	}

	public DatPhong(String maPhong, int maKhach, Date thoiGian) {
		this.maPhong = maPhong;
		this.maKhach = maKhach;
		this.thoiGian = thoiGian;
	}

	public int getMaDatPhong() {
		return maDatPhong;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public int getMaKhach() {
		return maKhach;
	}

	public Date getThoiGian() {
		return thoiGian;
	}
}
