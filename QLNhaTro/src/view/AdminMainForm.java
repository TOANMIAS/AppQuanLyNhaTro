package view;

import java.sql.CallableStatement;
import java.sql.Connection;
import util.DBConnection;

import util.Session;

import javax.swing.*;
import java.awt.*;

public class AdminMainForm extends JFrame {

	private JPanel contentPanel;

	public AdminMainForm() {

		if (!Session.isAdmin()) {
			JOptionPane.showMessageDialog(this, "Không có quyền!");
			dispose();
			return;
		}

		setTitle("ADMIN - QUẢN LÝ NHÀ TRỌ");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		add(createHeader(), BorderLayout.NORTH);

		// ✅ KHỞI TẠO contentPanel Ở ĐÂY
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(new Color(245, 247, 250));
		add(contentPanel, BorderLayout.CENTER);

		add(createMenu(), BorderLayout.WEST);

		// ✅ LOAD DASHBOARD SAU KHI MỌI THỨ XONG
		showPanel(new DashboardPanel());
	}

	// ===== HEADER =====
	private JPanel createHeader() {
		JPanel header = new JPanel(new BorderLayout());
		header.setPreferredSize(new Dimension(0, 50));
		header.setBackground(new Color(30, 144, 255));

		JLabel title = new JLabel("  HỆ THỐNG QUẢN LÝ NHÀ TRỌ");
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		title.setForeground(Color.WHITE);

		JLabel user = new JLabel("Xin chào, " + Session.user.getUsername() + "  ");
		user.setForeground(Color.WHITE);

		header.add(title, BorderLayout.WEST);
		header.add(user, BorderLayout.EAST);

		return header;
	}

	// ===== MENU TRÁI =====
	private JPanel createMenu() {

		JPanel menu = new JPanel(new GridLayout(6, 1, 0, 5));
		menu.setPreferredSize(new Dimension(200, 0));
		menu.setBackground(new Color(33, 42, 57));

		menu.add(menuButton("📊 Dashboard", () -> showPanel(new DashboardPanel())));
		menu.add(menuButton("👤 Khách thuê", () -> showPanel(new KhachThueForm())));
		menu.add(menuButton("🏘 Phòng trọ", () -> showPanel(new PhongTroForm())));
		menu.add(menuButton("📋 Duyệt đặt phòng", () -> showPanel(new DuyetDatPhongForm())));
		menu.add(menuButton("🛠 Duyệt dịch vụ", () -> showPanel(new DuyetYeuCauDichVuForm())));
		menu.add(menuButton("🧾 Hóa đơn", () -> showPanel(new HoaDonForm())));
		menu.add(menuButton("🚪 Đăng xuất", this::logout));

		return menu;
	}

	private JButton menuButton(String text, Runnable action) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(45, 58, 75));
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);

		btn.addActionListener(e -> action.run());

		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btn.setBackground(new Color(70, 130, 180));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btn.setBackground(new Color(45, 58, 75));
			}
		});

		return btn;
	}

	// ===== LOAD PANEL =====
	private void showPanel(JComponent panel) {
		contentPanel.removeAll();
		contentPanel.add(panel, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private void logout() {
		Session.logout();
		dispose();
		new AuthForm().setVisible(true);
	}

	private void taoHoaDonHangThang() {
		try (Connection conn = DBConnection.getConnection();
				CallableStatement cs = conn.prepareCall("{call sp_TaoHoaDonHangThang}")) {

			cs.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
