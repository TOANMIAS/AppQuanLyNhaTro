package view;

import javax.swing.*;
import java.awt.*;

public class XemHopDongForm extends JDialog {

	JCheckBox chkDongY;
	JButton btnXacNhan;

	public XemHopDongForm(JFrame parent, String noiDungHopDong, Runnable onAccept) {
		super(parent, "Hợp đồng thuê phòng", true);
		setSize(600, 500);
		setLocationRelativeTo(parent);

		JTextArea txt = new JTextArea(noiDungHopDong);
		txt.setEditable(false);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);

		chkDongY = new JCheckBox("Tôi đã đọc và đồng ý với các điều khoản");
		btnXacNhan = new JButton("Xác nhận đặt thuê");
		btnXacNhan.setEnabled(false);

		chkDongY.addActionListener(e -> btnXacNhan.setEnabled(chkDongY.isSelected()));

		btnXacNhan.addActionListener(e -> {
			onAccept.run(); // gọi logic đặt phòng
			dispose();
		});

		JPanel bottom = new JPanel();
		bottom.add(chkDongY);
		bottom.add(btnXacNhan);

		add(new JScrollPane(txt), BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
	}
}
