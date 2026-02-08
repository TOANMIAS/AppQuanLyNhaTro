package view;

import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DienNuocDAO;
import dao.PhongTroDAO;
import model.DienNuoc;
import model.PhongTro;
import util.Session;

public class DienNuocForm extends JPanel {

	JTextField txtDien, txtNuoc;
	JButton btnGui;

	public DienNuocForm() {

		if (!Session.isLogin()) {
			JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập!");
			return;
		}

		setLayout(new GridLayout(3, 2, 10, 10));

		add(new JLabel("Số điện tiêu thụ:"));
		txtDien = new JTextField();
		add(txtDien);

		add(new JLabel("Số nước tiêu thụ:"));
		txtNuoc = new JTextField();
		add(txtNuoc);

		btnGui = new JButton("Gửi chỉ số");
		add(new JLabel());
		add(btnGui);

		btnGui.addActionListener(e -> gui());
	}

	private void gui() {

		try {
			int dien = Integer.parseInt(txtDien.getText());
			int nuoc = Integer.parseInt(txtNuoc.getText());

			PhongTro phong = PhongTroDAO.getPhongDangThue(Session.user.getMaKhach());
			if (phong == null) {
				JOptionPane.showMessageDialog(this, "Bạn chưa được gán phòng!");
				return;
			}

			DienNuoc dn = new DienNuoc();
			dn.setMaPhong(phong.getMaPhong());
			dn.setThang(LocalDate.now().getMonthValue());
			dn.setNam(LocalDate.now().getYear());
			dn.setSoDien(dien);
			dn.setSoNuoc(nuoc);

			DienNuocDAO.nhapChiSo(dn);

			JOptionPane.showMessageDialog(this, "Đã ghi nhận chỉ số!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
		}
	}
}
