package view;

import javax.swing.*;
import java.awt.CardLayout;

public class AuthForm extends JFrame {

	CardLayout card = new CardLayout();
	JPanel mainPanel = new JPanel(card);

	public AuthForm() {
		setTitle("Đăng nhập hệ thống");
		setSize(420, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		mainPanel.add(new LoginPanel(this), "LOGIN");
		mainPanel.add(new RegisterPanel(this), "REGISTER");

		add(mainPanel);
		card.show(mainPanel, "LOGIN");
	}

	public void showLogin() {
		card.show(mainPanel, "LOGIN");
	}

	public void showRegister() {
		card.show(mainPanel, "REGISTER");
	}

	// ===== MAIN METHOD (BẮT BUỘC) =====
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new AuthForm().setVisible(true);
		});
	}
}
