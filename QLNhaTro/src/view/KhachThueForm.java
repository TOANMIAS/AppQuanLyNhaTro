package view;

import dao.KhachThueDAO;
import model.KhachThue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachThueForm extends JPanel {

	JTable table;
	DefaultTableModel model;
	JTextField txtTen, txtPhai, txtCMND, txtQueQuan, txtNghe;

	KhachThueDAO dao = new KhachThueDAO();

	public KhachThueForm() {
		setLayout(new BorderLayout(10, 10));
		setBackground(Color.WHITE);

		// ===== TITLE =====
		JLabel title = new JLabel("QUẢN LÝ KHÁCH THUÊ");
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(title, BorderLayout.NORTH);

		// ===== TABLE =====
		model = new DefaultTableModel(new String[] { "Mã", "Tên", "Phái", "CMND", "Quê quán", "Nghề" }, 0);
		table = new JTable(model);
		table.setRowHeight(25);
		loadData();

		JScrollPane sp = new JScrollPane(table);

		// ===== FORM RIGHT =====
		JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
		form.setBorder(BorderFactory.createTitledBorder("Thông tin khách thuê"));

		txtTen = new JTextField();
		txtPhai = new JTextField();
		txtCMND = new JTextField();
		txtQueQuan = new JTextField();
		txtNghe = new JTextField();

		form.add(new JLabel("Tên khách"));
		form.add(txtTen);
		form.add(new JLabel("Phái"));
		form.add(txtPhai);
		form.add(new JLabel("CMND"));
		form.add(txtCMND);
		form.add(new JLabel("Quê quán"));
		form.add(txtQueQuan);
		form.add(new JLabel("Nghề nghiệp"));
		form.add(txtNghe);

		// ===== BUTTON =====
		JButton btnAdd = new JButton("Thêm");
		JButton btnUpdate = new JButton("Sửa");
		JButton btnDelete = new JButton("Xóa");
		JButton btnReload = new JButton("Làm mới");

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		btnPanel.add(btnAdd);
		btnPanel.add(btnUpdate);
		btnPanel.add(btnDelete);
		btnPanel.add(btnReload);

		JPanel right = new JPanel(new BorderLayout(10, 10));
		right.add(form, BorderLayout.CENTER);
		right.add(btnPanel, BorderLayout.SOUTH);

		add(sp, BorderLayout.CENTER);
		add(right, BorderLayout.EAST);

		// ===== EVENT =====
		table.getSelectionModel().addListSelectionListener(e -> {
			int r = table.getSelectedRow();
			if (r >= 0) {
				txtTen.setText(model.getValueAt(r, 1).toString());
				txtPhai.setText(model.getValueAt(r, 2).toString());
				txtCMND.setText(model.getValueAt(r, 3).toString());
				txtQueQuan.setText(model.getValueAt(r, 4).toString());
				txtNghe.setText(model.getValueAt(r, 5).toString());
			}
		});

		btnAdd.addActionListener(e -> {
			KhachThue k = new KhachThue();
			k.setTenKhach(txtTen.getText());
			k.setPhai(txtPhai.getText());
			k.setCmnd(txtCMND.getText());
			k.setQueQuan(txtQueQuan.getText());
			k.setNgheNghiep(txtNghe.getText());

			if (dao.insert(k)) {
				loadData();
			}
		});

		btnUpdate.addActionListener(e -> {
			int r = table.getSelectedRow();
			if (r < 0)
				return;

			KhachThue k = new KhachThue();
			k.setMaKhach((int) model.getValueAt(r, 0));
			k.setTenKhach(txtTen.getText());
			k.setPhai(txtPhai.getText());
			k.setCmnd(txtCMND.getText());
			k.setQueQuan(txtQueQuan.getText());
			k.setNgheNghiep(txtNghe.getText());

			if (dao.update(k))
				loadData();
		});

		btnDelete.addActionListener(e -> {
			int r = table.getSelectedRow();
			if (r < 0)
				return;

			int id = (int) model.getValueAt(r, 0);
			if (JOptionPane.showConfirmDialog(this, "Xóa khách này?", "Xác nhận",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				dao.delete(id);
				loadData();
			}
		});

		btnReload.addActionListener(e -> loadData());
	}

	void loadData() {
		model.setRowCount(0);
		List<KhachThue> list = dao.getAll();
		for (KhachThue k : list) {
			model.addRow(new Object[] { k.getMaKhach(), k.getTenKhach(), k.getPhai(), k.getCmnd(), k.getQueQuan(),
					k.getNgheNghiep() });
		}
	}
}
