package model;

import java.util.Date;

public class HopDong {
	private int maHopDong;
	private Date ngayLap;
	private String maPhong;

	public HopDong(int maHopDong, Date ngayLap, String maPhong) {
		this.maHopDong = maHopDong;
		this.ngayLap = ngayLap;
		this.maPhong = maPhong;
	}

	public HopDong(Date ngayLap, String maPhong) {
		this.ngayLap = ngayLap;
		this.maPhong = maPhong;
	}

	public int getMaHopDong() {
		return maHopDong;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public String getMaPhong() {
		return maPhong;
	}
}
