package view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import util.Session;

public class UserMainForm extends JFrame {

	private JDesktopPane desktop;

	public UserMainForm() {

		// ===== KIỂM TRA LOGIN =====
		if (!Session.isLogin()) {
			JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập!");
			dispose();
			return;
		}

		setTitle("KHÁCH THUÊ - QUẢN LÝ NHÀ TRỌ");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		desktop = new JDesktopPane();
		add(desktop, BorderLayout.CENTER);

		// ===== MENU BAR =====
		JMenuBar bar = new JMenuBar();

		JMenu mUser = new JMenu("Chức năng");
		JMenuItem miDashboard = new JMenuItem("Trang chủ");
		JMenuItem miDienNuoc = new JMenuItem("Nhập điện nước");
		JMenuItem miDatPhong = new JMenuItem("Đặt phòng");
		JMenuItem miHoaDon = new JMenuItem("Hóa đơn & Thanh toán");
		JMenuItem miDoiMK = new JMenuItem("Đổi mật khẩu");
		JMenuItem miDichVu = new JMenuItem("Dịch vụ");

		mUser.add(miDashboard);
		mUser.add(miDatPhong);
		mUser.add(miDienNuoc);
		mUser.add(miHoaDon);
		mUser.add(miDichVu);
		mUser.addSeparator();
		mUser.add(miDoiMK);

		JMenu mHT = new JMenu("Hệ thống");
		JMenuItem miLogout = new JMenuItem("Đăng xuất");

		mHT.add(miLogout);

		bar.add(mUser);
		bar.add(mHT);
		setJMenuBar(bar);
// ===== SỰ KIỆN MENU =====
		miDashboard.addActionListener(e -> openInternal("Trang chủ", new DashboardPanel(this)));
		miDienNuoc.addActionListener(e -> openInternal("Nhập điện nước", new DienNuocForm()));

		miDatPhong.addActionListener(e -> openInternal("Đặt phòng", new DatPhongForm()));

		miHoaDon.addActionListener(e -> openInternal("Hóa đơn & Thanh toán", new HoaDonForm()));

		miDichVu.addActionListener(e -> openInternal("Dịch vụ", new DichVuForm()));

		miDoiMK.addActionListener(e -> openInternal("Đổi mật khẩu", new DoiMatKhauPanel()));

		miLogout.addActionListener(e -> {
			Session.logout();
			dispose();
			new AuthForm().setVisible(true);
		});

		// ===== MỞ DASHBOARD MẶC ĐỊNH =====
		openInternal("Trang chủ", new DashboardPanel(this));
	}

	// ===== HÀM MỞ FORM CON (PUBLIC) =====
	public void openInternal(String title, JComponent panel) {

		// đóng form cũ
		for (JInternalFrame f : desktop.getAllFrames()) {
			f.dispose();
		}

		JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
		frame.setSize(750, 500);
		frame.setLocation(20, 20);
		frame.add(panel);
		frame.setVisible(true);

		desktop.add(frame);
		frame.toFront();
	}
}
