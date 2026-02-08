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
import dao.DatPhongDAO;
import dao.HoaDonDAO;
import dao.HopDongDAO;
import dao.PhongTroDAO;
import model.DatPhong;
import util.Session;

public class DuyetDatPhongForm extends JPanel {

	JTable table;
	DefaultTableModel model;
	JButton btnDuyet, btnTuChoi;

	public DuyetDatPhongForm() {

		if (!Session.isAdmin()) {
			JOptionPane.showMessageDialog(this, "Không có quyền!");
			return;
		}

		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("DUYỆT ĐẶT PHÒNG", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "Mã đặt", "Mã phòng", "Mã khách", "Thời gian" }, 0);
		table = new JTable(model);
		table.setRowHeight(25);

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
		List<DatPhong> list = DatPhongDAO.getAllChoDuyet();

		for (DatPhong d : list) {
			model.addRow(new Object[] { d.getMaDatPhong(), d.getMaPhong(), d.getMaKhach(), d.getThoiGian() });
		}
	}

	private void bindEvent() {

		// ===== DUYỆT =====
		btnDuyet.addActionListener(e -> {

			int r = table.getSelectedRow();
			if (r < 0)
				return;

			int maDat = (int) model.getValueAt(r, 0);
			String maPhong = model.getValueAt(r, 1).toString();
			int maKhach = (int) model.getValueAt(r, 2);

			// 1️⃣ kiểm tra đồng ý hợp đồng
			if (!DatPhongDAO.daDongYDieuKhoan(maDat)) {
				JOptionPane.showMessageDialog(this, "Khách chưa đồng ý điều khoản hợp đồng!");
				return;
			}

			// 2️⃣ đổi trạng thái phòng
			PhongTroDAO.capNhatTrangThai(maPhong, 1);

			// 3️⃣ gán khách ↔ phòng
			CTKhachThueDAO.themKhachVaoPhong(maKhach, maPhong);

			// 4️⃣ tạo hợp đồng
			String noiDung = DatPhongDAO.getNoiDungHopDong(maDat);
			HopDongDAO.taoHopDong(maPhong, maDat, noiDung);

			// 5️⃣ tạo hóa đơn
			// sau khi gán khách vào phòng

			// 6️⃣ cập nhật trạng thái (KHÔNG DELETE)
			DatPhongDAO.capNhatTrangThai(maDat, 1);

			JOptionPane.showMessageDialog(this, "Đã duyệt!\nHợp đồng và hóa đơn đã được tạo.");

			loadData();
		});

		// ===== TỪ CHỐI =====
		btnTuChoi.addActionListener(e -> {

			int r = table.getSelectedRow();
			if (r < 0)
				return;

			int maDat = (int) model.getValueAt(r, 0);

			if (JOptionPane.showConfirmDialog(this, "Từ chối yêu cầu đặt phòng?", "Xác nhận",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				DatPhongDAO.capNhatTrangThai(maDat, 2);
				loadData();
			}
		});
	}
}
