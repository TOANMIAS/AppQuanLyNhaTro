package view;

import javax.swing.*;
import java.awt.*;
import dao.DichVuDAO;
import dao.YeuCauDichVuDAO;
import dao.CTKhachThueDAO;
import model.DichVu;
import model.YeuCauDichVu;
import util.Session;

public class CTDichVuForm extends JPanel {

	private JComboBox<DichVu> cboDichVu;
	private JTextField txtSoLuong;
	private JButton btnGui;

	public CTDichVuForm() {

		if (!Session.isLogin()) {
			JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập!");
			return;
		}

		setLayout(new GridLayout(3, 2, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Yêu cầu dịch vụ"));

		add(new JLabel("Dịch vụ:"));
		cboDichVu = new JComboBox<>();
		loadDichVu();
		add(cboDichVu);

		add(new JLabel("Số lượng:"));
		txtSoLuong = new JTextField();
		add(txtSoLuong);

		btnGui = new JButton("Gửi yêu cầu");
		add(new JLabel());
		add(btnGui);

		btnGui.addActionListener(e -> guiYeuCau());
	}

	private void loadDichVu() {
		cboDichVu.removeAllItems();
		for (DichVu dv : DichVuDAO.getDichVuChoKhach()) {
			cboDichVu.addItem(dv);
		}
	}

	private void guiYeuCau() {
		try {
			DichVu dv = (DichVu) cboDichVu.getSelectedItem();
			int soLuong = Integer.parseInt(txtSoLuong.getText());

			// 🔴 SỬA ĐÚNG: LẤY MÃ PHÒNG TỪ CT_KHACH_THUE
			String maPhong = CTKhachThueDAO.getMaPhongDangThue(Session.user.getMaKhach());
			if (maPhong == null) {
				JOptionPane.showMessageDialog(this, "Bạn chưa được gán phòng!");
				return;
			}

			YeuCauDichVu yc = new YeuCauDichVu();
			yc.setMaKhach(Session.user.getMaKhach());
			yc.setMaPhong(maPhong);
			yc.setMaDichVu(dv.getMaDichVu());
			yc.setTenDichVu(dv.getDichVu());
			yc.setGiaTien(dv.getGiaDichVu());
			yc.setSoLuong(soLuong);
			yc.setTrangThai(0); // chờ duyệt

			YeuCauDichVuDAO.guiYeuCau(yc);
			JOptionPane.showMessageDialog(this, "Đã gửi yêu cầu dịch vụ!");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
		}
	}
}
