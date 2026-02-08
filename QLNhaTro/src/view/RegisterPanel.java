package view;

import dao.UserDAO;

import javax.swing.*;

public class RegisterPanel extends JPanel {

	public RegisterPanel(AuthForm parent) {
		setLayout(null);

		JTextField txtTen = new JTextField();
		JTextField txtCMND = new JTextField();
		JTextField txtUser = new JTextField();
		JPasswordField txtPass = new JPasswordField();

		addLabel("Tên khách:", 40);
		add(txtTen);
		txtTen.setBounds(150, 40, 180, 25);
		addLabel("CMND:", 80);
		add(txtCMND);
		txtCMND.setBounds(150, 80, 180, 25);
		addLabel("Username:", 120);
		add(txtUser);
		txtUser.setBounds(150, 120, 180, 25);
		addLabel("Password:", 160);
		add(txtPass);
		txtPass.setBounds(150, 160, 180, 25);

		JButton btnReg = new JButton("Đăng ký");
		btnReg.setBounds(150, 200, 100, 30);
		add(btnReg);

		JButton btnBack = new JButton("← Đăng nhập");
		btnBack.setBounds(150, 235, 120, 25);
		add(btnBack);

		btnReg.addActionListener(e -> {
			UserDAO dao = new UserDAO();
			if (dao.register(txtTen.getText(), txtCMND.getText(), txtUser.getText(),
					new String(txtPass.getPassword()))) {
				JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
				parent.showLogin();
			} else {
				JOptionPane.showMessageDialog(this, "Đăng ký thất bại!");
			}
		});

		btnBack.addActionListener(e -> parent.showLogin());
	}

	private void addLabel(String text, int y) {
		JLabel lb = new JLabel(text);
		lb.setBounds(50, y, 100, 25);
		add(lb);
	}
}
