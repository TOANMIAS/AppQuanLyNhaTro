package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DashboardDAO;
import dao.KhachThueDAO;
import dao.PhongTroDAO;
import model.KhachThue;
import model.PhongTro;
import util.Session;

public class DashboardPanel extends JPanel {

	private UserMainForm parent;

	/* ================== ADMIN ================== */
	public DashboardPanel() {
		initAdminUI();
	}

	/* ================== USER ================== */
	public DashboardPanel(UserMainForm parent) {
		this.parent = parent;
		initUserUI();
	}

	/* ========== GIAO DIỆN ADMIN ========== */
	private void initAdminUI() {
		setLayout(new BorderLayout(15, 15));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);

		JLabel title = new JLabel("DASHBOARD - QUẢN TRỊ");
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(title, BorderLayout.NORTH);

		JPanel cards = new JPanel(new GridLayout(2, 3, 15, 15));
		cards.setOpaque(false);

		cards.add(card("TỔNG PHÒNG", String.valueOf(DashboardDAO.countPhong()), new Color(52, 152, 219)));

		cards.add(card("PHÒNG ĐANG THUÊ", String.valueOf(DashboardDAO.countPhongDangThue()), new Color(46, 204, 113)));

		cards.add(card("PHÒNG TRỐNG", String.valueOf(DashboardDAO.countPhongTrong()), new Color(241, 196, 15)));

		cards.add(card("KHÁCH THUÊ", String.valueOf(DashboardDAO.countKhach()), new Color(155, 89, 182)));

		cards.add(card("HÓA ĐƠN", String.valueOf(DashboardDAO.countHoaDon()), new Color(231, 76, 60)));

		add(cards, BorderLayout.CENTER);
	}

	/* ========== GIAO DIỆN USER ========== */
	private void initUserUI() {

		setLayout(new BorderLayout(15, 15));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);

		/* ===== HEADER ===== */
		JPanel header = new JPanel(new BorderLayout());
		header.setOpaque(false);

		JLabel welcome = new JLabel("Xin chào, " + Session.user.getUsername(), JLabel.LEFT);
		welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
		header.add(welcome, BorderLayout.NORTH);

		KhachThue kt = KhachThueDAO.getById(Session.user.getMaKhach());
		if (kt != null) {
			JLabel infoUser = new JLabel("<html>" + "Tên: <b>" + kt.getTenKhach() + "</b><br>" + "CMND: " + kt.getCmnd()
					+ "<br>" + "Quê quán: " + kt.getQueQuan() + "</html>");
			infoUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			header.add(infoUser, BorderLayout.SOUTH);
		}

		add(header, BorderLayout.NORTH);

		/* ===== THÔNG TIN PHÒNG ===== */
		JPanel info = new JPanel(new GridLayout(1, 2, 15, 15));
		info.setOpaque(false);

		PhongTro phong = PhongTroDAO.getPhongDangThue(Session.user.getMaKhach());

		if (phong == null) {
			info.add(card("PHÒNG CỦA TÔI", "Chưa có", new Color(52, 152, 219)));
			info.add(card("TRẠNG THÁI", "Chưa thuê phòng", new Color(241, 196, 15)));
		} else {
			info.add(card("PHÒNG ĐANG THUÊ", phong.getMaPhong(), new Color(46, 204, 113)));
			info.add(card("GIÁ THUÊ", String.format("%,.0f VNĐ", phong.getGiaThue()), new Color(231, 76, 60)));
		}

		/* ===== ACTION ===== */
		JPanel action = new JPanel(new GridLayout(2, 2, 15, 15));
		action.setOpaque(false);

		JButton btnXemPhong = actionButton("🏘 Xem phòng");
		JButton btnDatPhong = actionButton("📝 Đặt phòng");
		JButton btnHoaDon = actionButton("🧾 Hóa đơn");
		JButton btnThanhToan = actionButton("💳 Thanh toán");

		// 🔒 Nếu đã thuê phòng thì KHÓA nút Đặt phòng
		if (phong != null) {
			btnDatPhong.setEnabled(false);
			btnDatPhong.setToolTipText("Bạn đã thuê phòng");
		}

		/* ===== SỰ KIỆN ===== */
		btnXemPhong.addActionListener(e -> parent.openInternal("Phòng trọ", new PhongTroForm()));

		btnDatPhong.addActionListener(e -> parent.openInternal("Đặt phòng", new DatPhongForm()));

		btnHoaDon.addActionListener(e -> parent.openInternal("Hóa đơn", new HoaDonForm()));

		btnThanhToan.addActionListener(e -> parent.openInternal("Thanh toán", new HoaDonForm()));

		action.add(btnXemPhong);
		action.add(btnDatPhong);
		action.add(btnHoaDon);
		action.add(btnThanhToan);

		add(info, BorderLayout.CENTER);
		add(action, BorderLayout.SOUTH);
	}

	/* ========== COMPONENT DÙNG CHUNG ========== */
	private JPanel card(String title, String value, Color color) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(color);
		p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel t = new JLabel(title);
		t.setForeground(Color.WHITE);
		t.setFont(new Font("Segoe UI", Font.BOLD, 14));

		JLabel v = new JLabel(value, JLabel.CENTER);
		v.setForeground(Color.WHITE);
		v.setFont(new Font("Segoe UI", Font.BOLD, 28));

		p.add(t, BorderLayout.NORTH);
		p.add(v, BorderLayout.CENTER);
		return p;
	}

	private JButton actionButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		return btn;
	}
}
