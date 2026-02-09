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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.CTKhachThueDAO;
import dao.HoaDonDAO;
import model.HoaDon;
import util.Session;

public class HoaDonForm extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private JButton btnThanhToan, btnReload;

	public HoaDonForm() {

		if (!Session.isLogin()) {
			JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập!");
			return;
		}

		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("HÓA ĐƠN CỦA TÔI", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "Mã HĐ", "Phòng", "Ngày lập", "Tổng tiền", "Trạng thái" }, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(26);
		add(new JScrollPane(table), BorderLayout.CENTER);

		btnThanhToan = new JButton("Thanh toán");
		btnReload = new JButton("Tải lại");

		JPanel bottom = new JPanel();
		bottom.add(btnThanhToan);
		bottom.add(btnReload);
		add(bottom, BorderLayout.SOUTH);

		loadData();
		bindEvent();
		
	}
	

	/* ================= LOAD DATA ================= */
	private void loadData() {
		model.setRowCount(0);

		// 🔴 LẤY PHÒNG KHÁCH ĐANG THUÊ
		String maPhong = CTKhachThueDAO.getMaPhongDangThue(Session.user.getMaKhach());
		if (maPhong == null)
			return;

		List<HoaDon> list = HoaDonDAO.getHoaDonByPhong(maPhong);

		for (HoaDon hd : list) {
			model.addRow(new Object[] { hd.getMaHoaDon(), hd.getMaPhong(), hd.getNgayLap(),
					String.format("%,.0f", hd.getTongTien()),
					hd.isTrangThaiThanhToan() ? "Đã thanh toán" : "Chưa thanh toán" });
		}
	}

	/* ================= EVENT ================= */
	private void bindEvent() {

		btnReload.addActionListener(e -> loadData());

		btnThanhToan.addActionListener(e -> {
			int r = table.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn!");
				return;
			}

			String trangThai = model.getValueAt(r, 4).toString();
			if (trangThai.equals("Đã thanh toán")) {
				JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán!");
				return;
			}

			int maHoaDon = (int) model.getValueAt(r, 0);

			if (JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán hóa đơn " + maHoaDon + "?", "Xác nhận",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				HoaDonDAO.thanhToan(maHoaDon);
				JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
				loadData();
			}
			
		});
	}
}
