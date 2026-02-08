package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.DatPhongDAO;
import dao.KhachThueDAO;
import dao.PhongTroDAO;
import model.KhachThue;
import model.PhongTro;
import util.HopDongUtil;
import util.Session;

public class DatPhongForm extends JPanel {

	JTable table;
	DefaultTableModel model;
	JButton btnDat;
	JCheckBox chkTrong;

	public DatPhongForm() {

		setLayout(new BorderLayout(15, 15));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);

		/* ===== TITLE ===== */
		JLabel title = new JLabel("ĐẶT PHÒNG");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);

		/* ===== TABLE ===== */
		model = new DefaultTableModel(new String[] { "Mã phòng", "Số người", "Giá phòng", "Trạng thái" }, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(28);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Tô màu trạng thái
		table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int col) {

				JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

				if ("Trống".equals(value)) {
					c.setForeground(new Color(0, 128, 0));
				} else {
					c.setForeground(Color.RED);
				}
				return c;
			}
		});

		add(new JScrollPane(table), BorderLayout.CENTER);

		/* ===== BOTTOM ===== */
		chkTrong = new JCheckBox("Chỉ hiển thị phòng trống");
		btnDat = new JButton("Đặt phòng");
		btnDat.setEnabled(false);

		JPanel bottom = new JPanel();
		bottom.add(chkTrong);
		bottom.add(btnDat);
		add(bottom, BorderLayout.SOUTH);

		/* ===== LOAD DATA ===== */
		loadData(false);

		/* ===== EVENT ===== */

		// Lọc phòng trống
		chkTrong.addActionListener(e -> loadData(chkTrong.isSelected()));

		// Chỉ bật nút khi chọn phòng trống
		table.getSelectionModel().addListSelectionListener(e -> {
			int r = table.getSelectedRow();
			if (r >= 0) {
				btnDat.setEnabled("Trống".equals(model.getValueAt(r, 3).toString()));
			}
		});

		// ===== SỰ KIỆN ĐẶT PHÒNG (HIỆN HỢP ĐỒNG) =====
		btnDat.addActionListener(e -> {

			int r = table.getSelectedRow();
			if (r < 0)
				return;

			String maPhong = model.getValueAt(r, 0).toString();
			int maKhach = Session.user.getMaKhach();

			// Lấy thông tin phòng + khách
			PhongTro phong = PhongTroDAO.getByMaPhong(maPhong);
			KhachThue khach = KhachThueDAO.getById(maKhach);

			// Tạo nội dung hợp đồng
			String hopDongText = HopDongUtil.taoNoiDungHopDong(phong, khach);

			JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

			// Hiện form xem hợp đồng
			new XemHopDongForm(parent, hopDongText, () -> {
				DatPhongDAO.datPhong(maPhong, maKhach, hopDongText);
				JOptionPane.showMessageDialog(this, "Đã gửi yêu cầu thuê!\nVui lòng chờ admin duyệt.");
				loadData(chkTrong.isSelected());
			}).setVisible(true);
		});
	}

	/* ===== LOAD DATA ===== */
	private void loadData(boolean chiPhongTrong) {

		model.setRowCount(0);
		List<Object[]> list = PhongTroDAO.getPhongChoUser();

		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

		for (Object[] o : list) {
			int trangThai = (int) o[3];
			if (chiPhongTrong && trangThai == 1)
				continue;

			model.addRow(new Object[] { o[0], o[1], nf.format(o[2]), trangThai == 0 ? "Trống" : "Đã thuê" });
		}
	}
}
