package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.HoaDonDAO;
import model.HoaDon;
import util.Session;

public class HoaDonForm extends JPanel {

	JTable table;
	DefaultTableModel model;
	JButton btnThanhToan;

	HoaDonDAO dao = new HoaDonDAO();

	public HoaDonForm() {
		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("HÓA ĐƠN CỦA TÔI");
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "Mã HĐ", "Ngày lập", "Phòng", "Tổng tiền", "Trạng thái" }, 0);
		table = new JTable(model);
		table.setRowHeight(25);
		add(new JScrollPane(table), BorderLayout.CENTER);

		btnThanhToan = new JButton("Thanh toán");
		JPanel bottom = new JPanel();
		bottom.add(btnThanhToan);
		add(bottom, BorderLayout.SOUTH);

		loadData();

		btnThanhToan.addActionListener(e -> thanhToan());
	}

	private void loadData() {
		model.setRowCount(0);

		List<HoaDon> list = dao.getHoaDonByMaKhach(Session.user.getMaKhach());
		for (HoaDon h : list) {
			model.addRow(new Object[] { h.getMaHoaDon(), h.getNgayLap(), h.getMaPhong(), h.getTongTien(),
					h.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán" });
		}
	}

	private void thanhToan() {
		int r = table.getSelectedRow();
		if (r < 0)
			return;

		if (model.getValueAt(r, 4).toString().equals("Đã thanh toán")) {
			JOptionPane.showMessageDialog(this, "Hóa đơn đã được thanh toán!");
			return;
		}

		int maHD = (int) model.getValueAt(r, 0);

		if (JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán hóa đơn?", "Thanh toán",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			dao.thanhToanHoaDon(maHD);
			loadData();
		}
	}
}
