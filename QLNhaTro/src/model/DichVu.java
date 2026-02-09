package model;

public class DichVu {
	private String maDichVu;
	private String dichVu;
	private double giaDichVu;
	private boolean laCoDinh;

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getDichVu() {
		return dichVu;
	}

	public void setDichVu(String dichVu) {
		this.dichVu = dichVu;
	}

	public double getGiaDichVu() {
		return giaDichVu;
	}

	public void setGiaDichVu(double giaDichVu) {
		this.giaDichVu = giaDichVu;
	}

	public boolean isLaCoDinh() {
		return laCoDinh;
	}

	public void setLaCoDinh(boolean laCoDinh) {
		this.laCoDinh = laCoDinh;
	}

	@Override
	public String toString() {
		return dichVu + " (" + String.format("%,.0f", giaDichVu) + ")";
	}
}
