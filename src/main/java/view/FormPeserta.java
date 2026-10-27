package view;

import model.Peserta;
import config.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormPeserta extends JFrame {

    private JTextField txtNim;
    private JTextField txtNama;
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private JTextField txtProdi;
    private JTextField txtNoHp;
    private JTextField txtEmail;
    private JButton btnSimpan;
    private ButtonGroup bgJenisKelamin;

    private final Color COLOR_BG = new Color(239, 248, 243);
    private final Color COLOR_CARD = new Color(255, 255, 255);
    private final Color COLOR_TEXT_MAIN = new Color(31, 55, 45);
    private final Color COLOR_TEXT_MUTED = new Color(105, 126, 114);
    private final Color COLOR_ACCENT = new Color(4, 120, 87);
    private final Color COLOR_INPUT_BG = new Color(247, 251, 248);

    public FormPeserta() {
        initUI();
    }

    private void initUI() {
        setTitle("Sikursus - Manajemen Data Peserta");
        setSize(680, 850);
        setMinimumSize(new Dimension(620, 780));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setBorder(new EmptyBorder(30, 48, 24, 48));

        JLabel lblAccent = new JLabel("SIKURSUS  /  DATA MAHASISWA");
        lblAccent.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAccent.setForeground(COLOR_ACCENT);
        lblAccent.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitle = new JLabel("Formulir Peserta");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setForeground(COLOR_TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel(
                "Silakan lengkapi data mahasiswa dengan benar"
        );
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(COLOR_TEXT_MUTED);
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(lblAccent);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(lblSubtitle);

        add(headerPanel, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(COLOR_CARD);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(218, 233, 223), 1
                ),
                new EmptyBorder(24, 24, 24, 24)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(7, 12, 7, 12);
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 15);

        gbc.gridy = 0;
        cardPanel.add(createLabel("NIM", labelFont), gbc);

        gbc.gridy = 1;
        txtNim = createTextField(inputFont);
        cardPanel.add(txtNim, gbc);

        gbc.gridy = 2;
        cardPanel.add(createLabel("Nama Peserta", labelFont), gbc);

        gbc.gridy = 3;
        txtNama = createTextField(inputFont);
        cardPanel.add(txtNama, gbc);

        gbc.gridy = 4;
        cardPanel.add(createLabel("Jenis Kelamin", labelFont), gbc);

        gbc.gridy = 5;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        radioPanel.setBackground(COLOR_CARD);

        rbLaki = new JRadioButton("Laki-laki");
        rbLaki.setFont(inputFont);
        rbLaki.setForeground(COLOR_TEXT_MAIN);
        rbLaki.setBackground(COLOR_CARD);
        rbLaki.setFocusPainted(false);
        rbLaki.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rbPerempuan = new JRadioButton("Perempuan");
        rbPerempuan.setFont(inputFont);
        rbPerempuan.setForeground(COLOR_TEXT_MAIN);
        rbPerempuan.setBackground(COLOR_CARD);
        rbPerempuan.setFocusPainted(false);
        rbPerempuan.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bgJenisKelamin = new ButtonGroup();
        bgJenisKelamin.add(rbLaki);
        bgJenisKelamin.add(rbPerempuan);
        rbLaki.setSelected(true);

        radioPanel.add(rbLaki);
        radioPanel.add(Box.createHorizontalStrut(35));
        radioPanel.add(rbPerempuan);

        cardPanel.add(radioPanel, gbc);

        gbc.gridy = 6;
        cardPanel.add(createLabel("Program Studi", labelFont), gbc);

        gbc.gridy = 7;
        txtProdi = createTextField(inputFont);
        cardPanel.add(txtProdi, gbc);

        gbc.gridy = 8;
        cardPanel.add(createLabel("Nomor HP", labelFont), gbc);

        gbc.gridy = 9;
        txtNoHp = createTextField(inputFont);
        cardPanel.add(txtNoHp, gbc);

        gbc.gridy = 10;
        cardPanel.add(createLabel("Email", labelFont), gbc);

        gbc.gridy = 11;
        txtEmail = createTextField(inputFont);
        cardPanel.add(txtEmail, gbc);

        gbc.gridy = 12;
        gbc.insets = new Insets(25, 12, 10, 12);

        btnSimpan = new JButton("Simpan Ke Database");
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setBackground(COLOR_ACCENT);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.setOpaque(true);
        btnSimpan.setBorderPainted(false);

        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnSimpan.setBackground(new Color(6, 95, 70));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnSimpan.setBackground(COLOR_ACCENT);
            }
        });

        btnSimpan.addActionListener(this::actionSimpan);

        cardPanel.add(btnSimpan, gbc);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setBorder(new EmptyBorder(0, 36, 30, 36));
        contentPanel.add(cardPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setFont(font);
        label.setForeground(COLOR_TEXT_MUTED);
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setForeground(COLOR_TEXT_MAIN);
        textField.setBackground(COLOR_INPUT_BG);
        textField.setCaretColor(COLOR_ACCENT);
        textField.setPreferredSize(new Dimension(100, 46));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(211, 228, 216), 1
                ),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        return textField;
    }

    private void actionSimpan(ActionEvent e) {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String jk = rbLaki.isSelected() ? "Laki-laki" : "Perempuan";
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || noHp.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field teks formulir wajib diisi!",
                    "Peringatan Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Peserta peserta = new Peserta(0, nama, noHp, nim, jk, prodi, email);

            String sql = "INSERT INTO peserta (nim, nama, jenis_kelamin, prodi, no_hp, email) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, peserta.getNim());
                ps.setString(2, peserta.getNama());
                ps.setString(3, peserta.getJenisKelamin());
                ps.setString(4, peserta.getProdi());
                ps.setString(5, peserta.getNoHp());
                ps.setString(6, peserta.getEmail());

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data Mahasiswa " + peserta.getNama() + " berhasil disimpan!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    resetForm();
                }
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Validasi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal simpan ke database:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        rbLaki.setSelected(true);
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtNim.requestFocus();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new FormPeserta().setVisible(true);
        });
    }
}
