package view;

import dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.*;
import model.User;
import util.UserSession;

public class LoginFrame extends JFrame {
    private final JTextField txtUsername = new JTextField(22);
    private final JPasswordField pwdPassword = new JPasswordField(22);
    private final UserDAO dao = new UserDAO();

    public LoginFrame() {
        super("Login - SIKURSUS Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        ViewUtil.setupFrame(this, 620, 470);
        build();
    }

    private void build() {
        JPanel root = ViewUtil.root("Login SIKURSUS Desktop");
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(ViewUtil.BG);

        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(ViewUtil.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewUtil.BORDER),
                BorderFactory.createEmptyBorder(22, 28, 22, 28)));

        JPanel form = ViewUtil.formPanel();
        form.setBackground(ViewUtil.CARD);
        ViewUtil.addFormRow(form, 0, "Username", txtUsername);
        ViewUtil.addFormRow(form, 1, "Password", pwdPassword);
        card.add(form, BorderLayout.CENTER);

        JButton login = ViewUtil.primaryButton("Login");
        JButton keluar = ViewUtil.dangerButton("Keluar");
        JPanel south = new JPanel(new BorderLayout(0, 8));
        south.setBackground(ViewUtil.CARD);
        south.add(ViewUtil.actionPanel(login, keluar), BorderLayout.NORTH);
        JLabel demo = ViewUtil.note("Demo: admin_demo / Demo#2026   |   operator_demo / Demo#2026");
        demo.setHorizontalAlignment(SwingConstants.CENTER);
        south.add(demo, BorderLayout.SOUTH);
        card.add(south, BorderLayout.SOUTH);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(18, 18, 18, 18);
        outer.add(card, c);

        root.add(outer, BorderLayout.CENTER);
        setContentPane(root);
        getRootPane().setDefaultButton(login);
        login.addActionListener(e -> login());
        keluar.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String username = txtUsername.getText().trim();
        char[] password = pwdPassword.getPassword();
        try {
            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.");
                return;
            }
            User user = dao.authenticate(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Login gagal. Periksa username/password atau status akun.", "Login", JOptionPane.WARNING_MESSAGE);
                pwdPassword.setText("");
                return;
            }
            UserSession.setCurrentUser(user);
            dispose();
            SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));
        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Login", ex);
        } finally { Arrays.fill(password, '\0'); }
    }
}