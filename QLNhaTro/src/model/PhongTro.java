package model;

public class PhongTro {
	private String maPhong;
	private int trangThai;
	private String thongTinPhong;
	private int soNguoi;
	private double giaThue;

	public PhongTro(String maPhong, int trangThai, String thongTinPhong, int soNguoi, double giaThue) {
		this.maPhong = maPhong;
		this.trangThai = trangThai;
		this.thongTinPhong = thongTinPhong;
		this.soNguoi = soNguoi;
		this.giaThue = giaThue;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public String getThongTinPhong() {
		return thongTinPhong;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public double getGiaThue() {
		return giaThue;
	}

	public void setGiaThue(double giaThue) {
		this.giaThue = giaThue;
	}
}
