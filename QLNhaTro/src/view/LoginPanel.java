package view;

import dao.UserDAO;
import model.User;
import util.Session;

import javax.swing.*;

public class LoginPanel extends JPanel {

	public LoginPanel(AuthForm parent) {
		setLayout(null);

		JLabel lbUser = new JLabel("Username:");
		lbUser.setBounds(50, 50, 100, 25);
		add(lbUser);

		JTextField txtUser = new JTextField();
		txtUser.setBounds(150, 50, 180, 25);
		add(txtUser);

		JLabel lbPass = new JLabel("Password:");
		lbPass.setBounds(50, 90, 100, 25);
		add(lbPass);

		JPasswordField txtPass = new JPasswordField();
		txtPass.setBounds(150, 90, 180, 25);
		add(txtPass);

		JButton btnLogin = new JButton("Đăng nhập");
		btnLogin.setBounds(150, 130, 120, 30);
		add(btnLogin);

		JButton btnReg = new JButton("Đăng ký");
		btnReg.setBounds(150, 170, 120, 30);
		add(btnReg);

		btnLogin.addActionListener(e -> {
			User u = new UserDAO().login(txtUser.getText(), new String(txtPass.getPassword()));

			if (u != null) {
				Session.user = u;
				SwingUtilities.getWindowAncestor(this).dispose();

				if (u.isAdmin()) {
					new AdminMainForm().setVisible(true);
				} else {
					new UserMainForm().setVisible(true);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
			}
		});

		btnReg.addActionListener(e -> parent.showRegister());
	}
}
