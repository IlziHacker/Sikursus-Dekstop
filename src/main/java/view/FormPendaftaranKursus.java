package view;

import dao.PesertaDAO;
import model.Peserta;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FormPendaftaranKursus extends JFrame {

    private final Color biruGelap = new Color(6, 78, 59);
    private final Color biruLangit = new Color(5, 150, 105);
    private final Color biruTerang = new Color(209, 250, 229);
    private final Color latar = new Color(236, 253, 245);
    private final Color putih = Color.WHITE;
    private final Color teks = new Color(30, 41, 59);
    private final Color teksSekunder = new Color(167, 243, 208);
    private final Color garis = new Color(203, 228, 215);
    private final Color warnaKartu = Color.WHITE;
    private final Color cyan = new Color(16, 185, 129);

    private JPanel panelUtama;
    private JPanel panelHeader;
    private JPanel panelKonten;
    private JPanel panelForm;
    private JPanel panelRingkasan;
    private JPanel panelJenis;
    private JPanel panelGender;
    private JPanel panelFasilitas;
    private JPanel panelTombol;

    private JLabel lblJudul;
    private JLabel lblSubjudul;
    private JLabel lblNim;
    private JLabel lblNama;
    private JLabel lblGender;
    private JLabel lblProdi;
    private JLabel lblNoHp;
    private JLabel lblEmail;
    private JLabel lblKodeAkses;
    private JLabel lblJenis;
    private JLabel lblKursus;
    private JLabel lblFasilitas;
    private JLabel lblRingkasan;

    private JTextField txtNim;
    private JTextField txtNama;
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private ButtonGroup groupGender;
    private JTextField txtProdi;
    private JTextField txtNoHp;
    private JTextField txtEmail;

    private JPasswordField txtKodeAkses;
    private JRadioButton rbMahasiswa;
    private JRadioButton rbUmum;
    private ButtonGroup groupJenis;
    private JComboBox<String> cmbKursus;
    private JCheckBox cbModul;
    private JCheckBox cbSertifikat;
    private JCheckBox cbRekaman;
    private JTextArea txtRingkasan;
    private JScrollPane scrollRingkasan;
    private JButton btnProses;
    private JButton btnReset;

    public FormPendaftaranKursus() {
        initComponents();

        groupJenis.add(rbMahasiswa);
        groupJenis.add(rbUmum);

        groupGender.add(rbLaki);
        groupGender.add(rbPerempuan);

        rbLaki.setSelected(true);

        txtRingkasan.setEditable(false);
        txtRingkasan.setLineWrap(true);
        txtRingkasan.setWrapStyleWord(true);

        getRootPane().setDefaultButton(btnProses);

        setMinimumSize(new Dimension(1050, 700));
        setSize(new Dimension(1280, 820));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        panelUtama = new JPanel(new BorderLayout());
        panelHeader = new JPanel(new BorderLayout());
        panelKonten = new JPanel(new GridBagLayout());
        panelForm = new JPanel(new GridBagLayout());
        panelRingkasan = new JPanel(new BorderLayout(0, 12));
        panelJenis = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 2));
        panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 2));
        panelFasilitas = new JPanel(new GridBagLayout());
        panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        lblJudul = new JLabel("Pendaftaran Kursus");
        lblSubjudul = new JLabel(
            "SIKURSUS  •  Lengkapi data peserta dan pilih kursus yang sesuai"
        );

        lblNim = new JLabel("NIM");
        lblNama = new JLabel("Nama Peserta");
        lblGender = new JLabel("Jenis Kelamin");
        lblProdi = new JLabel("Program Studi");
        lblNoHp = new JLabel("Nomor HP");
        lblEmail = new JLabel("Email");
        lblKodeAkses = new JLabel("Kode Akses");
        lblJenis = new JLabel("Jenis Peserta");
        lblKursus = new JLabel("Pilih Kursus");
        lblFasilitas = new JLabel("Fasilitas Tambahan");
        lblRingkasan = new JLabel("Ringkasan Pendaftaran");

        txtNim = new JTextField();
        txtNama = new JTextField();

        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        groupGender = new ButtonGroup();

        txtProdi = new JTextField();
        txtNoHp = new JTextField();
        txtEmail = new JTextField();

        txtKodeAkses = new JPasswordField();

        rbMahasiswa = new JRadioButton("Mahasiswa");
        rbUmum = new JRadioButton("Umum");
        groupJenis = new ButtonGroup();

        cmbKursus = new JComboBox<String>(new String[]{
            "-- Pilih Kursus --",
            "Java Desktop",
            "Data Science",
            "Web Programming",
            "UI/UX Design"
        });

        cbModul = new JCheckBox("Modul Cetak (+Rp50.000)");
        cbSertifikat = new JCheckBox("Sertifikat (+Rp75.000)");
        cbRekaman = new JCheckBox("Akses Rekaman (+Rp40.000)");

        txtRingkasan = new JTextArea();
        scrollRingkasan = new JScrollPane(txtRingkasan);

        btnProses = new JButton("Proses Pendaftaran");
        btnReset = new JButton("Reset Form");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SIKURSUS Desktop | Pendaftaran Kursus");

        panelUtama.setBackground(latar);
        panelHeader.setBackground(biruGelap);
        panelKonten.setBackground(latar);
        panelForm.setBackground(warnaKartu);
        panelRingkasan.setBackground(warnaKartu);
        panelJenis.setBackground(warnaKartu);
        panelGender.setBackground(warnaKartu);
        panelFasilitas.setBackground(warnaKartu);
        panelTombol.setBackground(warnaKartu);

        panelHeader.setBorder(new EmptyBorder(20, 30, 20, 30));

        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblJudul.setForeground(putih);

        lblSubjudul.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubjudul.setForeground(teksSekunder);

        JPanel teksHeader = new JPanel(new GridBagLayout());
        teksHeader.setOpaque(false);

        GridBagConstraints h = new GridBagConstraints();
        h.gridx = 0;
        h.gridy = 0;
        h.anchor = GridBagConstraints.WEST;
        h.insets = new Insets(0, 0, 5, 0);
        teksHeader.add(lblJudul, h);

        h.gridy = 1;
        h.insets = new Insets(0, 0, 0, 0);
        teksHeader.add(lblSubjudul, h);

        panelHeader.add(teksHeader, BorderLayout.WEST);

        JLabel logo = new JLabel("SIKURSUS", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logo.setForeground(putih);
        logo.setOpaque(true);
        logo.setBackground(new Color(4, 120, 87));
        logo.setBorder(new EmptyBorder(12, 15, 12, 15));

        panelHeader.add(logo, BorderLayout.EAST);

        panelKonten.setBorder(new EmptyBorder(18, 22, 18, 22));

        panelForm.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        panelRingkasan.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1, true),
            new EmptyBorder(18, 18, 16, 18)
        ));

        styleLabel(lblNim);
        styleLabel(lblNama);
        styleLabel(lblGender);
        styleLabel(lblProdi);
        styleLabel(lblNoHp);
        styleLabel(lblEmail);
        styleLabel(lblKodeAkses);
        styleLabel(lblJenis);
        styleLabel(lblKursus);
        styleLabel(lblFasilitas);

        lblRingkasan.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblRingkasan.setForeground(biruGelap);

        styleField(txtNim);
        styleField(txtNama);
        styleField(txtProdi);
        styleField(txtNoHp);
        styleField(txtEmail);
        styleField(txtKodeAkses);
        styleCombo(cmbKursus);

        txtKodeAkses.setToolTipText(
            "Kode akses demo praktikum: PBO2026"
        );

        styleRadio(rbLaki);
        styleRadio(rbPerempuan);
        styleRadio(rbMahasiswa);
        styleRadio(rbUmum);

        panelGender.add(rbLaki);
        panelGender.add(rbPerempuan);

        panelJenis.add(rbMahasiswa);
        panelJenis.add(rbUmum);

        styleCheckBox(cbModul);
        styleCheckBox(cbSertifikat);
        styleCheckBox(cbRekaman);

        addFacility(0, cbModul);
        addFacility(1, cbSertifikat);
        addFacility(2, cbRekaman);

        txtRingkasan.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtRingkasan.setForeground(teks);
        txtRingkasan.setBackground(new Color(240, 253, 244));
        txtRingkasan.setBorder(new EmptyBorder(14, 14, 14, 14));

        scrollRingkasan.setBorder(new LineBorder(garis, 1, true));
        scrollRingkasan.getViewport().setBackground(
            new Color(240, 253, 244)
        );

        styleButton(
            btnProses, biruLangit, putih,
            new Dimension(205, 42)
        );

        styleButton(
            btnReset, biruTerang, biruGelap,
            new Dimension(125, 42)
        );

        btnProses.addActionListener(
            evt -> btnProsesActionPerformed()
        );

        btnReset.addActionListener(
            evt -> btnResetActionPerformed()
        );

        panelTombol.add(btnProses);
        panelTombol.add(btnReset);

        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 0;
        f.weightx = 1.0;
        f.fill = GridBagConstraints.HORIZONTAL;
        f.anchor = GridBagConstraints.NORTHWEST;

        addFormRow(lblNim, txtNim, 0, f);
        addFormRow(lblNama, txtNama, 2, f);
        addFormRow(lblGender, panelGender, 4, f);
        addFormRow(lblProdi, txtProdi, 6, f);
        addFormRow(lblNoHp, txtNoHp, 8, f);
        addFormRow(lblEmail, txtEmail, 10, f);
        addFormRow(lblKodeAkses, txtKodeAkses, 12, f);
        addFormRow(lblJenis, panelJenis, 14, f);
        addFormRow(lblKursus, cmbKursus, 16, f);
        addFormRow(lblFasilitas, panelFasilitas, 18, f);

        f.gridy = 20;
        f.insets = new Insets(8, 0, 0, 0);
        panelForm.add(panelTombol, f);

        panelRingkasan.add(lblRingkasan, BorderLayout.NORTH);
        panelRingkasan.add(scrollRingkasan, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;

        c.gridx = 0;
        c.weightx = 1.05;
        c.insets = new Insets(0, 0, 0, 14);
        panelKonten.add(panelForm, c);

        c.gridx = 1;
        c.weightx = 0.95;
        c.insets = new Insets(0, 0, 0, 0);
        panelKonten.add(panelRingkasan, c);

        JLabel footer = new JLabel(
            "SIKURSUS  |  Sistem Pendaftaran Kursus Desktop"
        );

        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(6, 95, 70));
        footer.setBorder(new EmptyBorder(0, 28, 10, 0));

        panelUtama.add(panelHeader, BorderLayout.NORTH);
        panelUtama.add(panelKonten, BorderLayout.CENTER);
        panelUtama.add(footer, BorderLayout.SOUTH);

        setContentPane(panelUtama);
    }

    private void addFormRow(
        JLabel label,
        java.awt.Component component,
        int startRow,
        GridBagConstraints f
    ) {
        f.gridy = startRow;
        f.insets = new Insets(0, 0, 4, 0);
        panelForm.add(label, f);

        f.gridy = startRow + 1;
        f.insets = new Insets(0, 0, 8, 0);
        panelForm.add(component, f);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(teks);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(teks);
        field.setBackground(putih);
        field.setPreferredSize(new Dimension(300, 35));

        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(teks);
        combo.setBackground(putih);
        combo.setPreferredSize(new Dimension(300, 35));
    }

    private void styleRadio(JRadioButton radio) {
        radio.setBackground(warnaKartu);
        radio.setForeground(teks);
        radio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        radio.setFocusPainted(false);
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        checkBox.setForeground(teks);
        checkBox.setBackground(warnaKartu);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleButton(
        JButton button,
        Color background,
        Color foreground,
        Dimension size
    ) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color hover = background.equals(biruLangit)
            ? new Color(4, 120, 87)
            : new Color(167, 243, 208);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(background);
            }
        });
    }

    private void addFacility(int row, JCheckBox checkBox) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(2, 0, 2, 0);

        panelFasilitas.add(checkBox, c);
    }

    private double hitungBiayaDasar(String kursus) {
        switch (kursus) {
            case "Java Desktop":
                return 500000;
            case "Data Science":
                return 650000;
            case "Web Programming":
                return 450000;
            case "UI/UX Design":
                return 400000;
            default:
                return 0;
        }
    }

    private double hitungBiayaFasilitas() {
        double total = 0;

        if (cbModul.isSelected()) {
            total += 50000;
        }

        if (cbSertifikat.isSelected()) {
            total += 75000;
        }

        if (cbRekaman.isSelected()) {
            total += 40000;
        }

        return total;
    }

    private String buatRingkasanFasilitas() {
        StringBuilder hasil = new StringBuilder();

        if (cbModul.isSelected()) {
            hasil.append("- Modul Cetak\n");
        }

        if (cbSertifikat.isSelected()) {
            hasil.append("- Sertifikat\n");
        }

        if (cbRekaman.isSelected()) {
            hasil.append("- Akses Rekaman\n");
        }

        if (hasil.length() == 0) {
            hasil.append("- Tidak ada fasilitas tambahan\n");
        }

        return hasil.toString();
    }

    private boolean validasiInput() {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();
        String kode = new String(txtKodeAkses.getPassword());

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty()
                || (!rbLaki.isSelected() && !rbPerempuan.isSelected())) {

            JOptionPane.showMessageDialog(
                this,
                "NIM, nama, jenis kelamin, dan prodi wajib diisi.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        if (!kode.equals("PBO2026")) {
            JOptionPane.showMessageDialog(
                this,
                "Kode akses salah. Masukkan PBO2026.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );

            txtKodeAkses.requestFocusInWindow();
            return false;
        }

        if (!rbMahasiswa.isSelected() && !rbUmum.isSelected()) {
            JOptionPane.showMessageDialog(
                this,
                "Silakan pilih jenis peserta.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        if (cmbKursus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "Silakan pilih kursus terlebih dahulu.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );

            cmbKursus.requestFocusInWindow();
            return false;
        }

        return true;
    }

    private void btnProsesActionPerformed() {
        if (!validasiInput()) {
            return;
        }

        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String jenisKelamin = rbLaki.isSelected() ? "L" : "P";
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();

        String jenis = rbMahasiswa.isSelected()
            ? "Mahasiswa"
            : "Umum";

        String kursus = cmbKursus.getSelectedItem().toString();

    Peserta peserta = new Peserta(
        0,
        nama,
        noHp,
        nim,
        jenisKelamin,
        prodi,
        email
    );

        PesertaDAO dao = new PesertaDAO();

        try {
            boolean berhasil = dao.insert(peserta);

            if (!berhasil) {
                JOptionPane.showMessageDialog(
                    this,
                    "Data peserta tidak tersimpan.",
                    "Informasi",
                    JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            double biayaDasar = hitungBiayaDasar(kursus);

            double diskon = jenis.equals("Mahasiswa")
                ? biayaDasar * 0.10
                : 0;

            double biayaFasilitas = hitungBiayaFasilitas();
            double total = biayaDasar - diskon + biayaFasilitas;

            String genderLengkap = rbLaki.isSelected()
                ? "Laki-laki"
                : "Perempuan";

            String ringkasan =
                "RINGKASAN PENDAFTARAN KURSUS\n"
                + "====================================\n\n"
                + "DATA PESERTA\n"
                + "------------------------------------\n"
                + "NIM             : " + nim + "\n"
                + "Nama Peserta    : " + nama + "\n"
                + "Jenis Kelamin   : " + genderLengkap + "\n"
                + "Program Studi   : " + prodi + "\n"
                + "Nomor HP        : "
                + (noHp.isEmpty() ? "-" : noHp) + "\n"
                + "Email           : "
                + (email.isEmpty() ? "-" : email) + "\n"
                + "Jenis Peserta   : " + jenis + "\n"
                + "Kursus          : " + kursus + "\n\n"
                + "RINCIAN BIAYA\n"
                + "------------------------------------\n"
                + "Biaya Dasar     : "
                + formatRupiah(biayaDasar) + "\n"
                + "Diskon          : "
                + formatRupiah(diskon) + "\n"
                + "Fasilitas:\n"
                + buatRingkasanFasilitas()
                + "Biaya Fasilitas : "
                + formatRupiah(biayaFasilitas) + "\n"
                + "------------------------------------\n"
                + "TOTAL           : "
                + formatRupiah(total) + "\n\n"
                + "Status          : Data peserta berhasil disimpan ke database.";

            txtRingkasan.setText(ringkasan);
            txtRingkasan.setCaretPosition(0);

            JOptionPane.showMessageDialog(
                this,
                "Data peserta berhasil disimpan dan pendaftaran diproses.",
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal menyimpan data:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    private String formatRupiah(double nilai) {
        String angka = String.format(
            Locale.US,
            "%,.0f",
            nilai
        ).replace(',', '.');

        return "Rp" + angka;
    }

    private void btnResetActionPerformed() {
        int jawab = JOptionPane.showConfirmDialog(
            this,
            "Kosongkan seluruh input?",
            "Konfirmasi Reset",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (jawab == JOptionPane.YES_OPTION) {
            clearForm();

            txtRingkasan.setText(
                "Belum ada pendaftaran.\n\n"
                + "Silakan lengkapi formulir di sebelah kiri."
            );
        }
    }

    private void clearForm() {
        txtNim.setText("");
        txtNama.setText("");
        groupGender.clearSelection();
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtKodeAkses.setText("");
        groupJenis.clearSelection();
        cmbKursus.setSelectedIndex(0);
        cbModul.setSelected(false);
        cbSertifikat.setSelected(false);
        cbRekaman.setSelected(false);

        txtNim.requestFocusInWindow();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new FormPendaftaranKursus().setVisible(true);
        });
    }
}