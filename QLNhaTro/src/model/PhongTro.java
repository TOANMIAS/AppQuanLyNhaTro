package model;

public class PhongTro {

	private String maPhong; // varchar(5)
	private int trangThai; // bit → map int
	private String thongTinPhong;
	private int soNguoi;
	private double giaThue;

	public PhongTro() {
	}

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

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public String getThongTinPhong() {
		return thongTinPhong;
	}

	public void setThongTinPhong(String thongTinPhong) {
		this.thongTinPhong = thongTinPhong;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) {
		this.soNguoi = soNguoi;
	}

	public double getGiaThue() {
		return giaThue;
	}

	public void setGiaThue(double giaThue) {
		this.giaThue = giaThue;
	}
}
