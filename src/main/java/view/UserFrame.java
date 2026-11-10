
package view;

import dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.User;
import util.UserSession;

public class UserFrame extends JFrame {

    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();

    private final JComboBox<String> cmbRole =
            new JComboBox<>(new String[]{"ADMIN", "OPERATOR"});

    private final JCheckBox chkAktif = new JCheckBox("Aktif", true);

    private final JTable table = new JTable(
            ViewUtil.tableModel("ID", "Username", "Role", "Aktif")
    );

    private final UserDAO dao = new UserDAO();

    private int selected = -1;

    private final Color EMERALD = new Color(5, 150, 105);
    private final Color EMERALD_DARK = new Color(4, 120, 87);
    private final Color EMERALD_LIGHT = new Color(209, 250, 229);
    private final Color BACKGROUND = new Color(245, 250, 248);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_DARK = new Color(31, 41, 55);
    private final Color TEXT_MUTED = new Color(107, 114, 128);
    private final Color BORDER = new Color(226, 232, 240);

    public UserFrame() {
        super("Master Users - SIKURSUS Desktop");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ViewUtil.setupFrame(this, 1000, 680);

        build();
        load();
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(22, 25, 22, 25));

        JPanel header = createHeader();
        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setBackground(BACKGROUND);

        JPanel formBox = createForm();
        JPanel tableBox = createTable();

        body.add(formBox, BorderLayout.NORTH);
        body.add(tableBox, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);

        setContentPane(root);

        getRootPane().setDefaultButton(createDefaultButton);
    }

    private final JButton createDefaultButton =
            new JButton();

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel teks = new JPanel();
        teks.setLayout(new BoxLayout(teks, BoxLayout.Y_AXIS));
        teks.setOpaque(false);

        JLabel judul = new JLabel("Manajemen User & Role");
        judul.setFont(new Font("SansSerif", Font.BOLD, 23));
        judul.setForeground(WHITE);

        JLabel deskripsi = new JLabel(
                "Kelola akun pengguna, hak akses, dan status akun sistem"
        );
        deskripsi.setFont(new Font("SansSerif", Font.PLAIN, 13));
        deskripsi.setForeground(new Color(220, 252, 231));

        teks.add(judul);
        teks.add(Box.createVerticalStrut(5));
        teks.add(deskripsi);

        header.add(teks, BorderLayout.WEST);

        return header;
    }

    private JPanel createForm() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 16));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel judul = createTitle("Form User");

        styleInput(txtUsername);
        styleInput(txtPassword);
        styleCombo(cmbRole);
        styleCheckBox(chkAktif);

        JPanel form = new JPanel(new java.awt.GridBagLayout());
        form.setOpaque(false);

        java.awt.GridBagConstraints gbc =
                new java.awt.GridBagConstraints();

        gbc.insets = new java.awt.Insets(8, 8, 8, 8);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addFormRow(form, gbc, 0, "Username", txtUsername);
        addFormRow(form, gbc, 1, "Password / Password Baru", txtPassword);
        addFormRow(form, gbc, 2, "Role", cmbRole);
        addFormRow(form, gbc, 3, "Status Akun", chkAktif);

        JButton tambah = createButton("Tambah", EMERALD);
        JButton update = createButton(
                "Ubah Role/Status", new Color(37, 99, 235)
        );
        JButton resetPass = createButton(
                "Reset Password", new Color(8, 145, 178)
        );
        JButton hapus = createButton(
                "Hapus", new Color(220, 38, 38)
        );
        JButton clear = createButton(
                "Reset Form", new Color(100, 116, 139)
        );

        JPanel tombol = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 9, 0)
        );
        tombol.setOpaque(false);

        tombol.add(tambah);
        tombol.add(update);
        tombol.add(resetPass);
        tombol.add(hapus);
        tombol.add(clear);

        card.add(judul, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(tombol, BorderLayout.SOUTH);

        tambah.addActionListener(e -> create());
        update.addActionListener(e -> update());
        resetPass.addActionListener(e -> resetPassword());
        hapus.addActionListener(e -> delete());
        clear.addActionListener(e -> clear());

        getRootPane().setDefaultButton(tambah);

        return card;
    }

    private void addFormRow(
            JPanel panel,
            java.awt.GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component) {

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0;

        panel.add(createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(component, gbc);
    }

    private JPanel createTable() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel judul = createTitle("Daftar User");

        table.setRowHeight(36);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(TEXT_DARK);
        table.setBackground(WHITE);
        table.setSelectionBackground(EMERALD_LIGHT);
        table.setSelectionForeground(EMERALD_DARK);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        table.setAutoCreateRowSorter(true);

        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );
        table.getTableHeader().setBackground(EMERALD);
        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0)
                .setCellRenderer(center);

        table.getColumnModel().getColumn(2)
                .setCellRenderer(center);

        table.getColumnModel().getColumn(3)
                .setCellRenderer(center);

        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(WHITE);

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pick();
            }
        });

        return card;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createLineBorder(BORDER));
        return panel;
    }

    private JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 17));
        label.setForeground(TEXT_DARK);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(TEXT_MUTED);
        return label;
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(TEXT_DARK);
        field.setBackground(WHITE);
        field.setCaretColor(EMERALD);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(7, 10, 7, 10)
        ));
        field.setPreferredSize(new Dimension(200, 36));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setBackground(WHITE);
        combo.setForeground(TEXT_DARK);
        combo.setPreferredSize(new Dimension(200, 36));
    }

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkBox.setForeground(TEXT_DARK);
        checkBox.setBackground(WHITE);
        checkBox.setFocusPainted(false);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));

        return button;
    }

    private void load() {
        try {
            DefaultTableModel m =
                    (DefaultTableModel) table.getModel();

            m.setRowCount(0);

            for (User u : dao.findAll()) {
                m.addRow(new Object[]{
                    u.getIdUser(),
                    u.getUsername(),
                    u.getRole(),
                    u.isAktif()
                });
            }

        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Memuat users", ex);
        }
    }

    private void pick() {
        int r = table.getSelectedRow();

        if (r < 0) {
            return;
        }

        int modelRow = table.convertRowIndexToModel(r);

        selected = (Integer) table.getModel().getValueAt(modelRow, 0);

        txtUsername.setText(
                table.getModel().getValueAt(modelRow, 1).toString()
        );

        txtUsername.setEditable(false);

        cmbRole.setSelectedItem(
                table.getModel().getValueAt(modelRow, 2)
        );

        chkAktif.setSelected(
                (Boolean) table.getModel().getValueAt(modelRow, 3)
        );
    }

    private void create() {
        char[] p = txtPassword.getPassword();

        try {
            if (txtUsername.getText().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Username wajib diisi"
                );
            }

            dao.create(
                    txtUsername.getText().trim(),
                    p,
                    cmbRole.getSelectedItem().toString(),
                    chkAktif.isSelected()
            );

            JOptionPane.showMessageDialog(
                    this, "User berhasil ditambahkan."
            );

            load();
            clear();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());

        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Menambah user", ex);

        } finally {
            Arrays.fill(p, '\0');
        }
    }

    private void update() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Pilih user.");
            return;
        }

        try {
            dao.updateRoleStatus(
                    selected,
                    cmbRole.getSelectedItem().toString(),
                    chkAktif.isSelected()
            );

            JOptionPane.showMessageDialog(
                    this, "Role dan status user berhasil diperbarui."
            );

            load();

        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Mengubah user", ex);
        }
    }

    private void resetPassword() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Pilih user.");
            return;
        }

        char[] p = txtPassword.getPassword();

        try {
            dao.resetPassword(selected, p);

            JOptionPane.showMessageDialog(
                    this, "Password berhasil direset."
            );

            txtPassword.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Kesalahan",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {
            Arrays.fill(p, '\0');
        }
    }

    private void delete() {
        if (selected < 0) {
            return;
        }

        if (UserSession.getCurrentUser() != null
                && selected == UserSession.getCurrentUser().getIdUser()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Akun yang sedang dipakai tidak boleh dihapus."
            );

            return;
        }

        if (JOptionPane.showConfirmDialog(
                this,
                "Hapus user terpilih?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        ) != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.delete(selected);

            JOptionPane.showMessageDialog(
                    this, "User berhasil dihapus."
            );

            load();
            clear();

        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Menghapus user", ex);
        }
    }

    private void clear() {
        selected = -1;

        txtUsername.setText("");
        txtUsername.setEditable(true);

        txtPassword.setText("");

        cmbRole.setSelectedIndex(1);
        chkAktif.setSelected(true);

        table.clearSelection();

        txtUsername.requestFocus();
    }
}
