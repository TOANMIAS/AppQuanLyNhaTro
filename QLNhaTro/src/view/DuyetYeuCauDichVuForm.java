package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.CTDichVuDAO;
import dao.HoaDonDAO;
import dao.YeuCauDichVuDAO;
import model.YeuCauDichVu;
import util.Session;

public class DuyetYeuCauDichVuForm extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private JButton btnDuyet, btnTuChoi;

	public DuyetYeuCauDichVuForm() {

		if (!Session.isAdmin()) {
			JOptionPane.showMessageDialog(this, "Không có quyền Admin!");
			return;
		}

		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("DUYỆT YÊU CẦU DỊCH VỤ", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "Mã YC", "Mã phòng", "Dịch vụ", "Số lượng" }, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model);
		add(new JScrollPane(table), BorderLayout.CENTER);

		btnDuyet = new JButton("Duyệt");
		btnTuChoi = new JButton("Từ chối");

		JPanel bottom = new JPanel();
		bottom.add(btnDuyet);
		bottom.add(btnTuChoi);
		add(bottom, BorderLayout.SOUTH);

		loadData();
		bindEvent();
	}

	private void loadData() {
		model.setRowCount(0);
		List<YeuCauDichVu> list = YeuCauDichVuDAO.getChoDuyet();

		for (YeuCauDichVu yc : list) {
			model.addRow(new Object[] { yc.getMaYeuCau(), yc.getMaPhong(), yc.getTenDichVu(), yc.getSoLuong() });
		}
	}

	private void bindEvent() {
		btnDuyet.addActionListener(e -> duyet());
		btnTuChoi.addActionListener(e -> tuChoi());
	}

	private void duyet() {

		int r = table.getSelectedRow();
		if (r < 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn yêu cầu!");
			return;
		}

		int maYC = (int) model.getValueAt(r, 0);
		YeuCauDichVu yc = YeuCauDichVuDAO.getById(maYC);

		if (yc == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy yêu cầu!");
			return;
		}

		// 🔴 LẤY HÓA ĐƠN CHƯA THANH TOÁN (ĐÃ TẠO SẴN)
		int maHoaDon = HoaDonDAO.getMaHoaDonChuaThanhToan(yc.getMaPhong());
		if (maHoaDon <= 0) {
			JOptionPane.showMessageDialog(this, "Phòng chưa có hóa đơn tháng này!");
			return;
		}

		CTDichVuDAO.themDichVuVaoHoaDon(maHoaDon, yc);
		YeuCauDichVuDAO.capNhatTrangThai(maYC, 1);

		JOptionPane.showMessageDialog(this, "Đã duyệt & cộng vào hóa đơn!");
		loadData();
	}

	private void tuChoi() {
		int r = table.getSelectedRow();
		if (r < 0)
			return;

		int maYC = (int) model.getValueAt(r, 0);
		YeuCauDichVuDAO.capNhatTrangThai(maYC, 2);
		JOptionPane.showMessageDialog(this, "Đã từ chối!");
		loadData();
	}
}
