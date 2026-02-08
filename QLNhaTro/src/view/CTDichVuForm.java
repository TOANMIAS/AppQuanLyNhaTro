package view;

import javax.swing.*;
import java.awt.*;
import dao.*;
import model.*;

public class CTDichVuForm extends JPanel {

	private JTextField txtMaHoaDon, txtDonViSuDung;
	private JComboBox<DichVu> cboDichVu;

	public CTDichVuForm() {
		setLayout(new GridLayout(4, 2, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Chi tiết dịch vụ"));

		add(new JLabel("Mã hóa đơn:"));
		txtMaHoaDon = new JTextField();
		add(txtMaHoaDon);

		add(new JLabel("Dịch vụ:"));
		cboDichVu = new JComboBox<>();
		loadDichVu();
		add(cboDichVu);

		add(new JLabel("Đơn vị sử dụng:"));
		txtDonViSuDung = new JTextField();
		add(txtDonViSuDung);

		JButton btnLuu = new JButton("Thêm dịch vụ");
		add(new JLabel());
		add(btnLuu);

		btnLuu.addActionListener(e -> luu());
	}

	private void loadDichVu() {
		DichVuDAO dao = new DichVuDAO();
		for (DichVu dv : dao.getAllChoUser()) {
			cboDichVu.addItem(dv);
		}
	}

	private void luu() {
		try {
			DichVu dv = (DichVu) cboDichVu.getSelectedItem();
			CTDichVu ct = new CTDichVu(Integer.parseInt(txtMaHoaDon.getText()), dv.getMaDichVu(), // STRING – ĐÚNG
					Integer.parseInt(txtDonViSuDung.getText()));

			new CTDichVuDAO().themDichVu(ct);
			JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
			e.printStackTrace();
		}
	}
}
