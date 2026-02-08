package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.PhongTroDAO;
import model.PhongTro;
import util.Session;

public class PhongTroForm extends JPanel {

	JTable table;
	DefaultTableModel model;

	JTextField txtMaPhong, txtThongTin, txtGiaThue;
	JComboBox<Integer> cboSoNguoi;
	JComboBox<String> cboTrangThai;

	JButton btnThem, btnSua, btnXoa;

	public PhongTroForm() {
		setLayout(new BorderLayout(10, 10));

		JLabel title = new JLabel("DANH SÁCH PHÒNG TRỌ");
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		// ===== TABLE =====
		model = new DefaultTableModel(new String[] { "Mã phòng", "Thông tin", "Số người", "Giá thuê", "Trạng thái" },
				0);
		table = new JTable(model);
		table.setRowHeight(25);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// ===== FORM =====
		JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
		form.setBorder(BorderFactory.createTitledBorder("Thông tin phòng"));

		txtMaPhong = new JTextField();
		txtThongTin = new JTextField();
		txtGiaThue = new JTextField();

		cboSoNguoi = new JComboBox<>(new Integer[] { 1, 2, 3, 4 });
		cboTrangThai = new JComboBox<>(new String[] { "Trống", "Đã thuê" });

		txtMaPhong.setEditable(false);
		txtMaPhong.setText(PhongTroDAO.taoMaPhong());

		form.add(new JLabel("Mã phòng"));
		form.add(txtMaPhong);

		form.add(new JLabel("Thông tin"));
		form.add(txtThongTin);

		form.add(new JLabel("Số người"));
		form.add(cboSoNguoi);

		form.add(new JLabel("Giá thuê (VNĐ)"));
		form.add(txtGiaThue);

		form.add(new JLabel("Trạng thái"));
		form.add(cboTrangThai);

		btnThem = new JButton("Thêm");
		btnSua = new JButton("Sửa");
		btnXoa = new JButton("Xóa");

		JPanel btnPanel = new JPanel();
		btnPanel.add(btnThem);
		btnPanel.add(btnSua);
		btnPanel.add(btnXoa);

		JPanel right = new JPanel(new BorderLayout(10, 10));
		right.add(form, BorderLayout.CENTER);
		right.add(btnPanel, BorderLayout.SOUTH);

		add(right, BorderLayout.EAST);

		loadData();
		bindEvent();
		bindTable();
		phanQuyen();
	}

	/* ================== EVENT BUTTON ================== */
	private void bindEvent() {

		// ===== THÊM =====
		btnThem.addActionListener(e -> {
			try {
				PhongTro p = new PhongTro(txtMaPhong.getText().trim(), cboTrangThai.getSelectedIndex(),
						txtThongTin.getText().trim(), (int) cboSoNguoi.getSelectedItem(),
						Double.parseDouble(txtGiaThue.getText()));

				PhongTroDAO.insert(p);
				JOptionPane.showMessageDialog(this, "Thêm phòng thành công!");
				loadData();
				clearForm();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Giá thuê không hợp lệ!");
			}
		});

		// ===== SỬA =====
		btnSua.addActionListener(e -> {
			int r = table.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Chọn phòng cần sửa!");
				return;
			}

			try {
				PhongTro p = new PhongTro(txtMaPhong.getText().trim(), cboTrangThai.getSelectedIndex(),
						txtThongTin.getText().trim(), (int) cboSoNguoi.getSelectedItem(),
						Double.parseDouble(txtGiaThue.getText()));

				PhongTroDAO.update(p);
				JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
				loadData();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Giá thuê không hợp lệ!");
			}
		});

		// ===== XÓA =====
		btnXoa.addActionListener(e -> {
			int r = table.getSelectedRow();
			if (r < 0) {
				JOptionPane.showMessageDialog(this, "Chọn phòng cần xóa!");
				return;
			}

			String maPhong = model.getValueAt(r, 0).toString();
			if (JOptionPane.showConfirmDialog(this, "Xóa phòng " + maPhong + "?", "Xác nhận",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;

			PhongTroDAO.delete(maPhong);
			JOptionPane.showMessageDialog(this, "Đã xóa phòng!");
			loadData();
			clearForm();
		});
	}

	/* ================== EVENT TABLE ================== */
	private void bindTable() {
		table.getSelectionModel().addListSelectionListener(e -> {
			int r = table.getSelectedRow();
			if (r >= 0) {
				txtMaPhong.setText(model.getValueAt(r, 0).toString());
				txtThongTin.setText(model.getValueAt(r, 1).toString());
				cboSoNguoi.setSelectedItem(model.getValueAt(r, 2));
				txtGiaThue.setText(model.getValueAt(r, 3).toString().replaceAll("[^0-9]", ""));
				cboTrangThai.setSelectedItem(model.getValueAt(r, 4));
			}
		});
	}

	private void clearForm() {
		txtMaPhong.setText(PhongTroDAO.taoMaPhong());
		txtThongTin.setText("");
		txtGiaThue.setText("");
		cboSoNguoi.setSelectedIndex(0);
		cboTrangThai.setSelectedIndex(0);
	}

	private void loadData() {
		model.setRowCount(0);
		List<PhongTro> list = PhongTroDAO.getAll();

		for (PhongTro p : list) {
			model.addRow(new Object[] { p.getMaPhong(), p.getThongTinPhong(), p.getSoNguoi(),
					String.format("%,.0f VNĐ", p.getGiaThue()), p.getTrangThai() == 1 ? "Đã thuê" : "Trống" });
		}
	}

	private void phanQuyen() {
		if (!Session.isAdmin()) {
			txtThongTin.setEnabled(false);
			txtGiaThue.setEnabled(false);
			cboSoNguoi.setEnabled(false);
			cboTrangThai.setEnabled(false);

			btnThem.setVisible(false);
			btnSua.setVisible(false);
			btnXoa.setVisible(false);
		}
	}
}
