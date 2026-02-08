package model;

public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private double giaDichVu;

	// ✅ constructor mặc định
	public DichVu() {
	}

	// ✅ constructor đầy đủ (FIX LỖI)
	public DichVu(String maDichVu, String tenDichVu, double giaDichVu) {
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.giaDichVu = giaDichVu;
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

	public double getGiaDichVu() {
		return giaDichVu;
	}

	public void setGiaDichVu(double giaDichVu) {
		this.giaDichVu = giaDichVu;
	}

	@Override
	public String toString() {
		return tenDichVu + " (" + giaDichVu + "đ)";
	}
}
