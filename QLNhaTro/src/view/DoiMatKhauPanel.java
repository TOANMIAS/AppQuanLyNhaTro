package view;

import dao.UserDAO;
import util.Session;

import javax.swing.*;
import java.awt.*;

public class DoiMatKhauPanel extends JPanel {

	private JPasswordField txtOld, txtNew, txtConfirm;
	private UserDAO dao = new UserDAO();

	public DoiMatKhauPanel() {
		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("ĐỔI MẬT KHẨU");
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);

		JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
		form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

		txtOld = new JPasswordField();
		txtNew = new JPasswordField();
		txtConfirm = new JPasswordField();

		form.add(new JLabel("Mật khẩu hiện tại"));
		form.add(txtOld);
		form.add(new JLabel("Mật khẩu mới"));
		form.add(txtNew);
		form.add(new JLabel("Xác nhận mật khẩu"));
		form.add(txtConfirm);

		JButton btnSave = new JButton("Cập nhật mật khẩu");

		JPanel bottom = new JPanel();
		bottom.add(btnSave);

		add(form, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		btnSave.addActionListener(e -> doiMatKhau());
	}

	private void doiMatKhau() {
		String oldPwd = new String(txtOld.getPassword());
		String newPwd = new String(txtNew.getPassword());
		String confirm = new String(txtConfirm.getPassword());

		if (oldPwd.isEmpty() || newPwd.isEmpty() || confirm.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để trống!");
			return;
		}

		if (!newPwd.equals(confirm)) {
			JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
			return;
		}

		boolean ok = dao.doiMatKhau(Session.user.getUserID(), oldPwd, newPwd);

		if (ok) {
			JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
			txtOld.setText("");
			txtNew.setText("");
			txtConfirm.setText("");
		} else {
			JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng!");
		}
	}
}
