package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.*;
import model.*;
import util.Session;

public class DichVuForm extends JPanel {

	JTable table;
	DefaultTableModel model;
	JComboBox<DichVu> cbDichVu;
	JTextField txtSoLuong;
	JButton btnGui;

	public DichVuForm() {

		if (!Session.isLogin()) {
			JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập!");
			return;
		}

		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("YÊU CẦU DỊCH VỤ", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "Mã YC", "Dịch vụ", "Số lượng", "Trạng thái" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(25);
		add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel form = new JPanel(new FlowLayout());

		cbDichVu = new JComboBox<>();
		txtSoLuong = new JTextField(5);
		btnGui = new JButton("Gửi yêu cầu");

		form.add(new JLabel("Dịch vụ:"));
		form.add(cbDichVu);
		form.add(new JLabel("Số lượng:"));
		form.add(txtSoLuong);
		form.add(btnGui);

		add(form, BorderLayout.SOUTH);

		loadDichVu();
		loadData();
		bindEvent();
	}

	private void loadDichVu() {
		cbDichVu.removeAllItems();
		for (DichVu dv : DichVuDAO.getAllChoUser()) {
			cbDichVu.addItem(dv);
		}
	}

	private void loadData() {
		model.setRowCount(0);

		PhongTro phong = PhongTroDAO.getPhongDangThue(Session.user.getMaKhach());
		if (phong == null)
			return;

		List<YeuCauDichVu> list = YeuCauDichVuDAO.getByPhong(phong.getMaPhong());

		for (YeuCauDichVu yc : list) {
			model.addRow(new Object[] { yc.getMaYC(), DichVuDAO.getTenByMa(yc.getMaDichVu()), yc.getSoLuong(),
					yc.getTrangThai() == 0 ? "Chờ duyệt" : yc.getTrangThai() == 1 ? "Đã duyệt" : "Từ chối" });
		}
	}

	private void bindEvent() {
		btnGui.addActionListener(e -> {
			try {
				int soLuong = Integer.parseInt(txtSoLuong.getText());
				if (soLuong <= 0)
					throw new Exception();

				PhongTro phong = PhongTroDAO.getPhongDangThue(Session.user.getMaKhach());
				if (phong == null) {
					JOptionPane.showMessageDialog(this, "Bạn chưa thuê phòng!");
					return;
				}

				DichVu dv = (DichVu) cbDichVu.getSelectedItem();

				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaPhong(phong.getMaPhong());
				yc.setMaDichVu(dv.getMaDichVu());
				yc.setSoLuong(soLuong);

				YeuCauDichVuDAO.guiYeuCau(yc);

				JOptionPane.showMessageDialog(this, "Đã gửi yêu cầu!");
				txtSoLuong.setText("");
				loadData();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
			}
		});
	}
}
