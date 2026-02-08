package model;

public class User {
	private int userID;
	private String username;
	private String pwd;
	private boolean isAdmin;
	private boolean tinhTrang;
	private int maKhach;

	public User() {
	}

	public User(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public int getMaKhach() {
		return maKhach;
	}

	public void setMaKhach(int maKhach) {
		this.maKhach = maKhach;
	}
}
