package view;

import javax.swing.*;
import java.awt.*;
import dao.DienNuocDAO;
import dao.CTKhachThueDAO;
import model.DienNuoc;
import util.Session;

public class DienNuocForm extends JPanel {

	private JTextField txtThang, txtNam, txtSoDien, txtSoNuoc;
	private JButton btnLuu;

	public DienNuocForm() {

		if (!Session.isLogin()) {
			JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập!");
			return;
		}

		setLayout(new GridLayout(5, 2, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Nhập điện nước tháng"));

		add(new JLabel("Tháng:"));
		txtThang = new JTextField();
		add(txtThang);

		add(new JLabel("Năm:"));
		txtNam = new JTextField();
		add(txtNam);

		add(new JLabel("Số điện tiêu thụ:"));
		txtSoDien = new JTextField();
		add(txtSoDien);

		add(new JLabel("Số nước tiêu thụ:"));
		txtSoNuoc = new JTextField();
		add(txtSoNuoc);

		btnLuu = new JButton("Lưu");
		add(new JLabel());
		add(btnLuu);

		btnLuu.addActionListener(e -> luu());
	}

	private void luu() {
		try {
			// 🔴 SỬA ĐÚNG: LẤY MÃ PHÒNG TỪ CT_KHACH_THUE
			String maPhong = CTKhachThueDAO.getMaPhongDangThue(Session.user.getMaKhach());
			if (maPhong == null) {
				JOptionPane.showMessageDialog(this, "Bạn chưa được gán phòng!");
				return;
			}

			DienNuoc dn = new DienNuoc();
			dn.setMaPhong(maPhong);
			dn.setThang(Integer.parseInt(txtThang.getText()));
			dn.setNam(Integer.parseInt(txtNam.getText()));
			dn.setSoDien(Integer.parseInt(txtSoDien.getText()));
			dn.setSoNuoc(Integer.parseInt(txtSoNuoc.getText()));

			DienNuocDAO.luuDienNuoc(dn);
			JOptionPane.showMessageDialog(this, "Đã lưu điện nước!");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
		}
	}
}
