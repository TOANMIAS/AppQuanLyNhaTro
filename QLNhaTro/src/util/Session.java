package util;

import model.User;

public class Session {
	public static User user = null;

	public static boolean isAdmin() {
		return user != null && user.isAdmin();
	}

	public static boolean isLogin() {
		return user != null;
	}

	public static void logout() {
		user = null;
	}
}
