package model;

public class CTDichVu {
	private int maHoaDon;
	private String maDichVu; // ✅ STRING
	private int donViSuDung;

	public CTDichVu(int maHoaDon, String maDichVu, int donViSuDung) {
		this.maHoaDon = maHoaDon;
		this.maDichVu = maDichVu;
		this.donViSuDung = donViSuDung;
	}

	public int getMaHoaDon() {
		return maHoaDon;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public int getDonViSuDung() {
		return donViSuDung;
	}
}
