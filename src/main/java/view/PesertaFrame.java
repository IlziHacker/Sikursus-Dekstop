
package view;

import dao.PesertaDAO;
import model.Peserta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PesertaFrame extends JFrame {

    private final PesertaDAO dao = new PesertaDAO();
    private int selectedId = -1;

    private JTextField txtNim, txtNama, txtProdi;
    private JTextField txtNoHp, txtEmail, txtCari;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup groupGender;
    private JTable tblPeserta;
    private DefaultTableModel model;

    private final Color EMERALD = new Color(5, 150, 105);
    private final Color EMERALD_DARK = new Color(4, 120, 87);
    private final Color EMERALD_LIGHT = new Color(209, 250, 229);
    private final Color BACKGROUND = new Color(245, 250, 248);
    private final Color TEXT_DARK = new Color(31, 41, 55);
    private final Color TEXT_MUTED = new Color(107, 114, 128);
    private final Color BORDER = new Color(226, 232, 240);
    private final Color WHITE = Color.WHITE;

    public PesertaFrame() {
        setTitle("Data Peserta Kursus");
        setSize(1100, 720);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        loadTable();
    }

    private void initComponents() {
        JPanel utama = new JPanel(new BorderLayout(0, 18));
        utama.setBackground(BACKGROUND);
        utama.setBorder(new EmptyBorder(22, 25, 22, 25));

        JPanel header = createHeader();
        JPanel form = createForm();
        JPanel tabel = createTablePanel();

        utama.add(header, BorderLayout.NORTH);

        JPanel konten = new JPanel(new BorderLayout(0, 18));
        konten.setBackground(BACKGROUND);
        konten.add(form, BorderLayout.NORTH);
        konten.add(tabel, BorderLayout.CENTER);

        utama.add(konten, BorderLayout.CENTER);

        add(utama);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel teks = new JPanel();
        teks.setLayout(new BoxLayout(teks, BoxLayout.Y_AXIS));
        teks.setOpaque(false);

        JLabel judul = new JLabel("Data Peserta Kursus");
        judul.setFont(new Font("SansSerif", Font.BOLD, 23));
        judul.setForeground(WHITE);

        JLabel deskripsi = new JLabel(
                "Kelola informasi dan data peserta kursus"
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

        JLabel judul = new JLabel("Informasi Peserta");
        judul.setFont(new Font("SansSerif", Font.BOLD, 17));
        judul.setForeground(TEXT_DARK);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        txtNim = createTextField();
        txtNama = createTextField();
        txtProdi = createTextField();
        txtNoHp = createTextField();
        txtEmail = createTextField();

        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");

        styleRadioButton(rbLaki);
        styleRadioButton(rbPerempuan);

        groupGender = new ButtonGroup();
        groupGender.add(rbLaki);
        groupGender.add(rbPerempuan);

        JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        gender.setOpaque(false);
        gender.add(rbLaki);
        gender.add(rbPerempuan);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addFormRow(form, gbc, 0, "NIM", txtNim, "Nama Lengkap", txtNama);
        addFormRow(form, gbc, 1, "Jenis Kelamin", gender,
                "Program Studi", txtProdi);
        addFormRow(form, gbc, 2, "Nomor HP", txtNoHp, "Email", txtEmail);

        JButton btnSimpan = createButton("Simpan", EMERALD);
        JButton btnUbah = createButton("Ubah", new Color(37, 99, 235));
        JButton btnHapus = createButton("Hapus", new Color(220, 38, 38));
        JButton btnBersih = createButton("Bersihkan", new Color(100, 116, 139));

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        tombol.setOpaque(false);

        tombol.add(btnSimpan);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnBersih);

        JPanel bagianAtas = new JPanel(new BorderLayout());
        bagianAtas.setOpaque(false);
        bagianAtas.add(judul, BorderLayout.WEST);

        card.add(bagianAtas, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(tombol, BorderLayout.SOUTH);

        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnBersih.addActionListener(e -> clearForm());

        getRootPane().setDefaultButton(btnSimpan);

        return card;
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label1,
            JComponent field1,
            String label2,
            JComponent field2) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        panel.add(createLabel(label1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field1, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(createLabel(label2), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        panel.add(field2, gbc);
    }

    private JPanel createTablePanel() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel judul = new JLabel("Daftar Peserta");
        judul.setFont(new Font("SansSerif", Font.BOLD, 17));
        judul.setForeground(TEXT_DARK);

        txtCari = createTextField();
        txtCari.setPreferredSize(new Dimension(220, 36));

        JButton btnCari = createButton("Cari", EMERALD);
        JButton btnRefresh = createButton("Refresh",
                new Color(100, 116, 139));

        JPanel pencarian = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pencarian.setOpaque(false);

        pencarian.add(new JLabel("Cari NIM / Nama:"));
        pencarian.add(txtCari);
        pencarian.add(btnCari);
        pencarian.add(btnRefresh);

        JPanel bagianAtas = new JPanel(new BorderLayout());
        bagianAtas.setOpaque(false);
        bagianAtas.add(judul, BorderLayout.WEST);
        bagianAtas.add(pencarian, BorderLayout.EAST);

        model = new DefaultTableModel(
                new String[]{
                    "ID", "NIM", "Nama", "Jenis Kelamin",
                    "Prodi", "No HP", "Email"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPeserta = new JTable(model);
        tblPeserta.setRowHeight(34);
        tblPeserta.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tblPeserta.setForeground(TEXT_DARK);
        tblPeserta.setBackground(WHITE);
        tblPeserta.setSelectionBackground(EMERALD_LIGHT);
        tblPeserta.setSelectionForeground(EMERALD_DARK);
        tblPeserta.setGridColor(BORDER);
        tblPeserta.setShowVerticalLines(false);
        tblPeserta.setShowHorizontalLines(true);
        tblPeserta.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        tblPeserta.setAutoCreateRowSorter(true);

        tblPeserta.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );
        tblPeserta.getTableHeader().setBackground(EMERALD);
        tblPeserta.getTableHeader().setForeground(WHITE);
        tblPeserta.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );
        tblPeserta.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        tblPeserta.getColumnModel().getColumn(0)
                .setCellRenderer(center);
        tblPeserta.getColumnModel().getColumn(1)
                .setCellRenderer(center);
        tblPeserta.getColumnModel().getColumn(3)
                .setCellRenderer(center);

        tblPeserta.getColumnModel().getColumn(0).setPreferredWidth(45);
        tblPeserta.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblPeserta.getColumnModel().getColumn(2).setPreferredWidth(170);
        tblPeserta.getColumnModel().getColumn(3).setPreferredWidth(110);
        tblPeserta.getColumnModel().getColumn(4).setPreferredWidth(130);
        tblPeserta.getColumnModel().getColumn(5).setPreferredWidth(120);
        tblPeserta.getColumnModel().getColumn(6).setPreferredWidth(180);

        JScrollPane scroll = new JScrollPane(tblPeserta);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(WHITE);

        card.add(bagianAtas, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        btnCari.addActionListener(e -> cari());
        btnRefresh.addActionListener(e -> {
            txtCari.setText("");
            loadTable();
        });

        txtCari.addActionListener(e -> cari());

        tblPeserta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pilihData();
            }
        });

        return card;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(1, 1, 1, 1)
        ));
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(TEXT_MUTED);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(TEXT_DARK);
        field.setBackground(WHITE);
        field.setCaretColor(EMERALD);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(7, 10, 7, 10)
        ));
        field.setPreferredSize(new Dimension(150, 36));
        return field;
    }

    private void styleRadioButton(JRadioButton radio) {
        radio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        radio.setForeground(TEXT_DARK);
        radio.setBackground(WHITE);
        radio.setFocusPainted(false);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 17, 10, 17));
        return button;
    }

    private String getJenisKelamin() {
        if (rbLaki.isSelected()) {
            return "Laki-laki";
        }

        if (rbPerempuan.isSelected()) {
            return "Perempuan";
        }

        return "";
    }

    private Peserta getFormData() {
        return new Peserta(
                selectedId < 0 ? 0 : selectedId,
                txtNama.getText(),
                txtNoHp.getText(),
                txtNim.getText(),
                getJenisKelamin(),
                txtProdi.getText(),
                txtEmail.getText()
        );
    }

    private void simpan() {
        try {
            if (getJenisKelamin().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Pilih jenis kelamin!"
                );
                return;
            }

            if (dao.insert(getFormData())) {
                JOptionPane.showMessageDialog(
                        this, "Data berhasil disimpan!"
                );
                clearForm();
                loadTable();
            }
        } catch (Exception ex) {
            tampilError(ex);
        }
    }

    private void ubah() {
        try {
            if (selectedId < 0) {
                JOptionPane.showMessageDialog(
                        this, "Pilih data yang ingin diubah!"
                );
                return;
            }

            if (getJenisKelamin().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Pilih jenis kelamin!"
                );
                return;
            }

            if (dao.update(getFormData())) {
                JOptionPane.showMessageDialog(
                        this, "Data berhasil diubah!"
                );
                clearForm();
                loadTable();
            }
        } catch (Exception ex) {
            tampilError(ex);
        }
    }

    private void hapus() {
        try {
            if (selectedId < 0) {
                JOptionPane.showMessageDialog(
                        this, "Pilih data yang ingin dihapus!"
                );
                return;
            }

            int konfirmasi = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin menghapus peserta ini?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (konfirmasi == JOptionPane.YES_OPTION) {
                if (dao.delete(selectedId)) {
                    JOptionPane.showMessageDialog(
                            this, "Data berhasil dihapus!"
                    );
                    clearForm();
                    loadTable();
                }
            }
        } catch (Exception ex) {
            tampilError(ex);
        }
    }

    private void cari() {
        try {
            tampilData(dao.search(txtCari.getText()));
        } catch (Exception ex) {
            tampilError(ex);
        }
    }

    private void loadTable() {
        try {
            tampilData(dao.findAll());
        } catch (Exception ex) {
            tampilError(ex);
        }
    }

    private void tampilData(List<Peserta> data) {
        model.setRowCount(0);

        for (Peserta p : data) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNim(),
                p.getNama(),
                p.getJenisKelamin(),
                p.getProdi(),
                p.getNoHp(),
                p.getEmail()
            });
        }
    }

    private void pilihData() {
        int row = tblPeserta.getSelectedRow();

        if (row < 0) {
            return;
        }

        int modelRow = tblPeserta.convertRowIndexToModel(row);

        selectedId = Integer.parseInt(
                model.getValueAt(modelRow, 0).toString()
        );

        txtNim.setText(model.getValueAt(modelRow, 1).toString());
        txtNama.setText(model.getValueAt(modelRow, 2).toString());

        String gender = model.getValueAt(modelRow, 3).toString();

        rbLaki.setSelected(gender.equals("Laki-laki"));
        rbPerempuan.setSelected(gender.equals("Perempuan"));

        txtProdi.setText(model.getValueAt(modelRow, 4).toString());
        txtNoHp.setText(model.getValueAt(modelRow, 5).toString());
        txtEmail.setText(model.getValueAt(modelRow, 6).toString());
    }

    private void clearForm() {
        selectedId = -1;

        txtNim.setText("");
        txtNama.setText("");
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtCari.setText("");

        groupGender.clearSelection();
        tblPeserta.clearSelection();
    }

    private void tampilError(Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
