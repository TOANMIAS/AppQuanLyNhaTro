package view;

import dao.CTDichVuDAO;
import dao.DichVuDAO;
import dao.HoaDonDAO;
import dao.YeuCauDichVuDAO;
import model.YeuCauDichVu;
import util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DuyetYeuCauDichVuForm extends JPanel {

	JTable table;
	DefaultTableModel model;
	JButton btnDuyet, btnTuChoi;

	public DuyetYeuCauDichVuForm() {

		// ===== CHECK QUYỀN =====
		if (!Session.isAdmin()) {
			JOptionPane.showMessageDialog(this, "Không có quyền!");
			return;
		}

		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("DUYỆT YÊU CẦU DỊCH VỤ", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		// ===== TABLE =====
		model = new DefaultTableModel(new String[] { "Mã YC", "Phòng", "Dịch vụ", "Số lượng" }, 0);
		table = new JTable(model);
		table.setRowHeight(25);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// ===== BUTTON =====
		btnDuyet = new JButton("Duyệt");
		btnTuChoi = new JButton("Từ chối");

		JPanel bottom = new JPanel();
		bottom.add(btnDuyet);
		bottom.add(btnTuChoi);
		add(bottom, BorderLayout.SOUTH);

		loadData();
		bindEvent();
	}

	// ===== LOAD DATA =====
	private void loadData() {
		model.setRowCount(0);

		List<YeuCauDichVu> list = YeuCauDichVuDAO.getChoDuyet();

		for (YeuCauDichVu yc : list) {
			model.addRow(new Object[] { yc.getMaYC(), yc.getMaPhong(), DichVuDAO.getTenByMa(yc.getMaDichVu()), // ✅ HIỂN
																												// THỊ
																												// TÊN
					yc.getSoLuong() });
		}
	}

	// ===== EVENT =====
	private void bindEvent() {

		// ===== DUYỆT =====
		btnDuyet.addActionListener(e -> {

			int r = table.getSelectedRow();
			if (r < 0)
				return;

			int maYC = (int) model.getValueAt(r, 0);

			// 1️⃣ Lấy thông tin yêu cầu
			YeuCauDichVu yc = YeuCauDichVuDAO.getById(maYC);
			String maPhong = yc.getMaPhong();

			// 2️⃣ Lấy tháng/năm hiện tại
			int thang = java.time.LocalDate.now().getMonthValue();
			int nam = java.time.LocalDate.now().getYear();

			// 3️⃣ TÍNH HÓA ĐƠN THÁNG (tự tạo nếu chưa có)
			HoaDonDAO.tinhHoaDonThang(maPhong, thang, nam);

			// 4️⃣ GẮN DỊCH VỤ VÀO HÓA ĐƠN
			CTDichVuDAO.themDichVuTuYeuCau(maYC);

			// 5️⃣ CẬP NHẬT TRẠNG THÁI
			CTDichVuDAO.capNhatTrangThai(maYC, 1);

			JOptionPane.showMessageDialog(this, "Đã duyệt & cộng vào hóa đơn!");
			loadData();
		});

		// ===== TỪ CHỐI =====
		btnTuChoi.addActionListener(e -> {

			int r = table.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn yêu cầu!");
				return;
			}

			int maYC = (int) model.getValueAt(r, 0);

			if (JOptionPane.showConfirmDialog(this, "Xác nhận TỪ CHỐI yêu cầu này?", "Xác nhận",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;

			CTDichVuDAO.capNhatTrangThai(maYC, 2);
			JOptionPane.showMessageDialog(this, "Đã từ chối yêu cầu!");
			loadData();
		});
	}
}
