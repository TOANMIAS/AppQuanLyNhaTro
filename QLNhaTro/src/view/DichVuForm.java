package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.DichVuDAO;
import dao.CTKhachThueDAO;
import dao.YeuCauDichVuDAO;
import model.DichVu;
import model.YeuCauDichVu;
import util.Session;

public class DichVuForm extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private JComboBox<DichVu> cbDichVu;
	private JTextField txtSoLuong;
	private JButton btnGui;

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
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model);
		add(new JScrollPane(table), BorderLayout.CENTER);

		cbDichVu = new JComboBox<>();
		txtSoLuong = new JTextField(5);
		btnGui = new JButton("Gửi yêu cầu");

		JPanel bottom = new JPanel();
		bottom.add(new JLabel("Dịch vụ:"));
		bottom.add(cbDichVu);
		bottom.add(new JLabel("Số lượng:"));
		bottom.add(txtSoLuong);
		bottom.add(btnGui);
		add(bottom, BorderLayout.SOUTH);

		loadDichVu();
		loadData();
		bindEvent();
	}

	private void loadDichVu() {
		cbDichVu.removeAllItems();
		for (DichVu dv : DichVuDAO.getDichVuChoKhach()) {
			cbDichVu.addItem(dv);
		}
	}

	private void loadData() {
		model.setRowCount(0);

		String maPhong = CTKhachThueDAO.getMaPhongDangThue(Session.user.getMaKhach());
		if (maPhong == null)
			return;

		List<YeuCauDichVu> list = YeuCauDichVuDAO.getChoDuyet();
		// 👉 KHÁCH chỉ xem yêu cầu của mình (đơn giản)

		for (YeuCauDichVu yc : list) {
			if (!maPhong.equals(yc.getMaPhong()))
				continue;

			model.addRow(new Object[] { yc.getMaYeuCau(), yc.getTenDichVu(), yc.getSoLuong(),
					yc.getTrangThai() == 0 ? "Chờ duyệt" : yc.getTrangThai() == 1 ? "Đã duyệt" : "Từ chối" });
		}
	}

	private void bindEvent() {
		btnGui.addActionListener(e -> {
			try {
				int soLuong = Integer.parseInt(txtSoLuong.getText());
				if (soLuong <= 0)
					throw new Exception();

				String maPhong = CTKhachThueDAO.getMaPhongDangThue(Session.user.getMaKhach());
				if (maPhong == null) {
					JOptionPane.showMessageDialog(this, "Bạn chưa được gán phòng!");
					return;
				}

				DichVu dv = (DichVu) cbDichVu.getSelectedItem();

				YeuCauDichVu yc = new YeuCauDichVu();
				yc.setMaKhach(Session.user.getMaKhach());
				yc.setMaPhong(maPhong);
				yc.setMaDichVu(dv.getMaDichVu());
				yc.setTenDichVu(dv.getDichVu());
				yc.setGiaTien(dv.getGiaDichVu());
				yc.setSoLuong(soLuong);
				yc.setTrangThai(0);

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
