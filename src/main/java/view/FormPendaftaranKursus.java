
package view;

import dao.PesertaDAO;
import model.Peserta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.util.List;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FormPendaftaranKursus extends JFrame {

    // WARNA TEMA EMERALD
    private final Color hijauGelap = new Color(6, 78, 59);
    private final Color hijau = new Color(5, 150, 105);
    private final Color hijauMuda = new Color(209, 250, 229);
    private final Color latar = new Color(236, 253, 245);
    private final Color putih = Color.WHITE;
    private final Color teks = new Color(31, 41, 55);
    private final Color garis = new Color(203, 228, 215);

    // PANEL
    private JPanel panelUtama;
    private JPanel panelHeader;
    private JPanel panelKonten;
    private JPanel panelForm;
    private JPanel panelTabel;
    private JPanel panelGender;
    private JPanel panelJenis;
    private JPanel panelFasilitas;
    private JPanel panelTombol;
    private JPanel panelPencarian;

    // LABEL
    private JLabel lblJudul;
    private JLabel lblSubjudul;

    // INPUT
    private JTextField txtNim;
    private JTextField txtNama;
    private JTextField txtProdi;
    private JTextField txtNoHp;
    private JTextField txtEmail;
    private JTextField txtCari;
    private JPasswordField txtKodeAkses;

    // RADIO BUTTON
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private JRadioButton rbMahasiswa;
    private JRadioButton rbUmum;

    private ButtonGroup groupGender;
    private ButtonGroup groupJenis;

    // KURSUS
    private JComboBox<String> cmbKursus;

    // FASILITAS
    private JCheckBox cbModul;
    private JCheckBox cbSertifikat;
    private JCheckBox cbRekaman;

    // BUTTON
    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnReset;
    private JButton btnCetak;
    private JButton btnCari;
    private JButton btnRefresh;

    // TABEL
    private JTable tblPeserta;
    private JScrollPane scrollTabel;
    private DefaultTableModel modelTabel;

    // DATABASE
    private final PesertaDAO dao = new PesertaDAO();
    private int selectedId = -1;

    public FormPendaftaranKursus() {
        initComponents();

        groupGender.add(rbLaki);
        groupGender.add(rbPerempuan);

        groupJenis.add(rbMahasiswa);
        groupJenis.add(rbUmum);

        configureTable();
        loadTable();
        clearForm();

        setMinimumSize(new Dimension(1100, 700));
        setSize(1450, 850);
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setTitle("SIKURSUS Desktop | Pendaftaran Kursus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelUtama = new JPanel(new BorderLayout());
        panelHeader = new JPanel(new BorderLayout());
        panelKonten = new JPanel(new GridBagLayout());

        panelForm = new JPanel(new GridBagLayout());
        panelTabel = new JPanel(new BorderLayout(0, 12));

        panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 2));
        panelJenis = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 2));
        panelFasilitas = new JPanel(new GridBagLayout());

        panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 5));
        panelPencarian = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));

        panelUtama.setBackground(latar);
        panelHeader.setBackground(hijauGelap);
        panelKonten.setBackground(latar);

        panelForm.setBackground(putih);
        panelTabel.setBackground(putih);
        panelGender.setBackground(putih);
        panelJenis.setBackground(putih);
        panelFasilitas.setBackground(putih);
        panelTombol.setBackground(putih);
        panelPencarian.setBackground(putih);

        panelHeader.setBorder(new EmptyBorder(20, 25, 20, 25));
        panelKonten.setBorder(new EmptyBorder(15, 18, 15, 18));

        panelForm.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1, true),
            new EmptyBorder(18, 18, 18, 18)
        ));

        panelTabel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        // HEADER APLIKASI
        lblJudul = new JLabel("Pendaftaran Kursus");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 27));
        lblJudul.setForeground(Color.WHITE);

        lblSubjudul = new JLabel(
            "SIKURSUS  |  Lengkapi data peserta dan pilih kursus yang sesuai"
        );
        lblSubjudul.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubjudul.setForeground(hijauMuda);

        JPanel headerText = new JPanel(new GridBagLayout());
        headerText.setOpaque(false);

        GridBagConstraints h = new GridBagConstraints();
        h.gridx = 0;
        h.gridy = 0;
        h.anchor = GridBagConstraints.WEST;
        h.insets = new Insets(0, 0, 5, 0);
        headerText.add(lblJudul, h);

        h.gridy = 1;
        h.insets = new Insets(0, 0, 0, 0);
        headerText.add(lblSubjudul, h);

        panelHeader.add(headerText, BorderLayout.WEST);

        JLabel logo = new JLabel("SIKURSUS", SwingConstants.CENTER);
        logo.setOpaque(true);
        logo.setBackground(hijau);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logo.setBorder(new EmptyBorder(12, 16, 12, 16));

        panelHeader.add(logo, BorderLayout.EAST);

        // LABEL FORM
        JLabel lblNim = buatLabel("NIM");
        JLabel lblNama = buatLabel("Nama Peserta");
        JLabel lblGender = buatLabel("Jenis Kelamin");
        JLabel lblProdi = buatLabel("Program Studi");
        JLabel lblNoHp = buatLabel("Nomor HP");
        JLabel lblEmail = buatLabel("Email");
        JLabel lblKode = buatLabel("Kode Akses");
        JLabel lblJenis = buatLabel("Jenis Peserta");
        JLabel lblKursus = buatLabel("Pilih Kursus");
        JLabel lblFasilitas = buatLabel("Fasilitas Tambahan");

        // INPUT
        txtNim = buatField();
        txtNama = buatField();
        txtProdi = buatField();
        txtNoHp = buatField();
        txtEmail = buatField();

        txtKodeAkses = new JPasswordField();
        txtKodeAkses.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtKodeAkses.setForeground(teks);
        txtKodeAkses.setBackground(putih);
        txtKodeAkses.setPreferredSize(new Dimension(300, 35));
        txtKodeAkses.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1),
            new EmptyBorder(5, 9, 5, 9)
        ));
        txtKodeAkses.setToolTipText("Kode akses: PBO2026");

        txtCari = new JTextField(18);
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCari.setForeground(teks);
        txtCari.setBackground(putih);
        txtCari.setPreferredSize(new Dimension(200, 35));
        txtCari.setBorder(new LineBorder(garis, 1));

        // RADIO BUTTON
        rbLaki = buatRadio("Laki-laki");
        rbPerempuan = buatRadio("Perempuan");
        rbMahasiswa = buatRadio("Mahasiswa");
        rbUmum = buatRadio("Umum");

        groupGender = new ButtonGroup();
        groupJenis = new ButtonGroup();

        panelGender.add(rbLaki);
        panelGender.add(rbPerempuan);

        panelJenis.add(rbMahasiswa);
        panelJenis.add(rbUmum);

        // KURSUS
        cmbKursus = new JComboBox<>(new String[]{
            "-- Pilih Kursus --",
            "Java Desktop",
            "Data Science",
            "Web Programming",
            "UI/UX Design"
        });

        cmbKursus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbKursus.setForeground(teks);
        cmbKursus.setBackground(putih);
        cmbKursus.setPreferredSize(new Dimension(300, 35));

        // FASILITAS
        cbModul = buatCheckBox("Modul Cetak (+Rp50.000)");
        cbSertifikat = buatCheckBox("Sertifikat (+Rp75.000)");
        cbRekaman = buatCheckBox("Akses Rekaman (+Rp40.000)");

        tambahFasilitas(0, cbModul);
        tambahFasilitas(1, cbSertifikat);
        tambahFasilitas(2, cbRekaman);

        // TOMBOL
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnCetak = new JButton("Cetak");
        btnCari = new JButton("Cari");
        btnRefresh = new JButton("Refresh");

        styleButton(btnSimpan, new Color(5, 150, 105), Color.WHITE);
        styleButton(btnUbah, new Color(217, 119, 6), Color.WHITE);
        styleButton(btnHapus, new Color(220, 38, 38), Color.WHITE);
        styleButton(btnReset, new Color(226, 232, 240), teks);
        styleButton(btnCetak, new Color(37, 99, 235), Color.WHITE);
        styleButton(btnCari, hijauGelap, Color.WHITE);
        styleButton(btnRefresh, new Color(5, 150, 105), Color.WHITE);

        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);
        panelTombol.add(btnCetak);

        // LAYOUT FORM
        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 0;
        f.weightx = 1;
        f.fill = GridBagConstraints.HORIZONTAL;
        f.anchor = GridBagConstraints.NORTHWEST;

        tambahBarisForm(lblNim, txtNim, 0, f);
        tambahBarisForm(lblNama, txtNama, 2, f);
        tambahBarisForm(lblGender, panelGender, 4, f);
        tambahBarisForm(lblProdi, txtProdi, 6, f);
        tambahBarisForm(lblNoHp, txtNoHp, 8, f);
        tambahBarisForm(lblEmail, txtEmail, 10, f);
        tambahBarisForm(lblKode, txtKodeAkses, 12, f);
        tambahBarisForm(lblJenis, panelJenis, 14, f);
        tambahBarisForm(lblKursus, cmbKursus, 16, f);
        tambahBarisForm(lblFasilitas, panelFasilitas, 18, f);

        f.gridy = 20;
        f.insets = new Insets(12, 0, 0, 0);
        panelForm.add(panelTombol, f);

        // TABEL PESERTA
        modelTabel = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "ID", "NIM", "Nama", "Jenis Kelamin",
                "Program Studi", "Nomor HP", "Email"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPeserta = new JTable(modelTabel);
        scrollTabel = new JScrollPane(tblPeserta);

        JLabel lblTabel = new JLabel("Data Peserta Kursus");
        lblTabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblTabel.setForeground(hijauGelap);

        JLabel lblCari = buatLabel("Cari Nama / NIM");

        panelPencarian.add(lblCari);
        panelPencarian.add(txtCari);
        panelPencarian.add(btnCari);
        panelPencarian.add(btnRefresh);

        JPanel bagianAtasTabel = new JPanel(new BorderLayout(0, 10));
        bagianAtasTabel.setBackground(putih);
        bagianAtasTabel.add(lblTabel, BorderLayout.NORTH);
        bagianAtasTabel.add(panelPencarian, BorderLayout.CENTER);

        panelTabel.add(bagianAtasTabel, BorderLayout.NORTH);
        panelTabel.add(scrollTabel, BorderLayout.CENTER);

        // LAYOUT UTAMA
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;

        c.gridx = 0;
        c.weightx = 0.48;
        c.insets = new Insets(0, 0, 0, 12);
        panelKonten.add(panelForm, c);

        c.gridx = 1;
        c.weightx = 0.52;
        c.insets = new Insets(0, 0, 0, 0);
        panelKonten.add(panelTabel, c);

        // FOOTER
        JLabel footer = new JLabel(
            "SIKURSUS  |  Sistem Pendaftaran Kursus Desktop"
        );
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(hijauGelap);
        footer.setBorder(new EmptyBorder(0, 20, 10, 0));

        // EVENT BUTTON
        btnSimpan.addActionListener(e -> btnSimpanActionPerformed());
        btnUbah.addActionListener(e -> btnUbahActionPerformed());
        btnHapus.addActionListener(e -> btnHapusActionPerformed());
        btnReset.addActionListener(e -> btnResetActionPerformed());
        btnCetak.addActionListener(e -> btnCetakActionPerformed());
        btnCari.addActionListener(e -> btnCariActionPerformed());
        btnRefresh.addActionListener(e -> refreshData());

        txtCari.addActionListener(e -> btnCariActionPerformed());

        // EVENT PILIH BARIS TABEL
        tblPeserta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pilihDataTabel();
            }
        });

        getRootPane().setDefaultButton(btnSimpan);

        panelUtama.add(panelHeader, BorderLayout.NORTH);
        panelUtama.add(panelKonten, BorderLayout.CENTER);
        panelUtama.add(footer, BorderLayout.SOUTH);

        setContentPane(panelUtama);
    }

    // HELPER LABEL
    private JLabel buatLabel(String tulisan) {
        JLabel label = new JLabel(tulisan);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(teks);
        return label;
    }

    // HELPER INPUT
    private JTextField buatField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(teks);
        field.setBackground(putih);
        field.setPreferredSize(new Dimension(300, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(garis, 1),
            new EmptyBorder(5, 9, 5, 9)
        ));
        return field;
    }

    // HELPER RADIO
    private JRadioButton buatRadio(String tulisan) {
        JRadioButton radio = new JRadioButton(tulisan);
        radio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        radio.setForeground(teks);
        radio.setBackground(putih);
        radio.setFocusPainted(false);
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return radio;
    }

    // HELPER CHECKBOX
    private JCheckBox buatCheckBox(String tulisan) {
        JCheckBox check = new JCheckBox(tulisan);
        check.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        check.setForeground(teks);
        check.setBackground(putih);
        check.setFocusPainted(false);
        check.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return check;
    }

    // STYLE BUTTON
    private void styleButton(
        JButton button,
        Color background,
        Color foreground
    ) {
        button.setUI(new BasicButtonUI());
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(background.darker(), 1, true));
        button.setPreferredSize(new Dimension(88, 36));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // LAYOUT BARIS FORM
    private void tambahBarisForm(
        JLabel label,
        Component komponen,
        int baris,
        GridBagConstraints f
    ) {
        f.gridy = baris;
        f.insets = new Insets(0, 0, 4, 0);
        panelForm.add(label, f);

        f.gridy = baris + 1;
        f.insets = new Insets(0, 0, 9, 0);
        panelForm.add(komponen, f);
    }

    // LAYOUT FASILITAS
    private void tambahFasilitas(int baris, JCheckBox check) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = baris;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(2, 0, 2, 0);
        panelFasilitas.add(check, c);
    }

    // HITUNG BIAYA KURSUS
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

    // HITUNG BIAYA FASILITAS
    private double hitungBiayaFasilitas() {
        double total = 0;

        if (cbModul.isSelected()) total += 50000;
        if (cbSertifikat.isSelected()) total += 75000;
        if (cbRekaman.isSelected()) total += 40000;

        return total;
    }

    // FORMAT RUPIAH
    private String formatRupiah(double nilai) {
        return "Rp" + String.format(
            Locale.US,
            "%,.0f",
            nilai
        ).replace(',', '.');
    }

    // VALIDASI INPUT PENDAFTARAN
    private boolean validasiInput() {
        if (txtNim.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtProdi.getText().trim().isEmpty()
                || (!rbLaki.isSelected() && !rbPerempuan.isSelected())) {

            JOptionPane.showMessageDialog(
                this,
                "NIM, nama, jenis kelamin, dan prodi wajib diisi."
            );
            return false;
        }

        String kode = new String(txtKodeAkses.getPassword());

        if (!kode.equals("PBO2026")) {
            JOptionPane.showMessageDialog(
                this,
                "Kode akses salah. Masukkan PBO2026."
            );
            txtKodeAkses.requestFocus();
            return false;
        }

        if (!rbMahasiswa.isSelected() && !rbUmum.isSelected()) {
            JOptionPane.showMessageDialog(this, "Pilih jenis peserta.");
            return false;
        }

        if (cmbKursus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih kursus terlebih dahulu."
            );
            return false;
        }

        return true;
    }

    // VALIDASI UBAH DATA
    private boolean validasiDataPeserta() {
        if (txtNim.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtProdi.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "NIM, nama, dan prodi wajib diisi."
            );
            return false;
        }

        if (!rbLaki.isSelected() && !rbPerempuan.isSelected()) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih jenis kelamin."
            );
            return false;
        }

        return true;
    }

    // JENIS KELAMIN
    private String getJenisKelamin() {
        if (rbLaki.isSelected()) return "L";
        if (rbPerempuan.isSelected()) return "P";
        return null;
    }

    // AMBIL DATA FORM
    private Peserta getFormData() {
        return new Peserta(
            selectedId,
            txtNama.getText().trim(),
            txtNoHp.getText().trim(),
            txtNim.getText().trim(),
            getJenisKelamin(),
            txtProdi.getText().trim(),
            txtEmail.getText().trim()
        );
    }

    // SIMPAN DATA
    private void btnSimpanActionPerformed() {
        if (!validasiInput()) return;

        String jenis = rbMahasiswa.isSelected()
            ? "Mahasiswa" : "Umum";

        String kursus = cmbKursus.getSelectedItem().toString();

        Peserta peserta = new Peserta(
            0,
            txtNama.getText().trim(),
            txtNoHp.getText().trim(),
            txtNim.getText().trim(),
            getJenisKelamin(),
            txtProdi.getText().trim(),
            txtEmail.getText().trim()
        );

        try {
            if (dao.insert(peserta)) {

                double dasar = hitungBiayaDasar(kursus);
                double diskon = jenis.equals("Mahasiswa")
                    ? dasar * 0.10 : 0;

                double fasilitas = hitungBiayaFasilitas();
                double total = dasar - diskon + fasilitas;

                String rincian =
                    "Data peserta berhasil disimpan.\n\n"
                    + "Nama: " + peserta.getNama() + "\n"
                    + "NIM: " + peserta.getNim() + "\n"
                    + "Jenis peserta: " + jenis + "\n"
                    + "Kursus: " + kursus + "\n"
                    + "Biaya dasar: " + formatRupiah(dasar) + "\n"
                    + "Diskon: " + formatRupiah(diskon) + "\n"
                    + "Biaya fasilitas: " + formatRupiah(fasilitas) + "\n"
                    + "Total: " + formatRupiah(total);

                JOptionPane.showMessageDialog(
                    this,
                    rincian,
                    "Pendaftaran Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
                );

                refreshData();

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Data gagal disimpan."
                );
            }

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

    // KONFIGURASI TABEL
    private void configureTable() {
        tblPeserta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPeserta.setRowHeight(32);
        tblPeserta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblPeserta.setForeground(teks);
        tblPeserta.setBackground(putih);
        tblPeserta.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
        tblPeserta.setFillsViewportHeight(true);
        tblPeserta.setGridColor(garis);
        tblPeserta.setShowGrid(true);
        tblPeserta.setIntercellSpacing(new Dimension(1, 1));

        // WARNA HEADER TABEL
        tblPeserta.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 12)
        );
        tblPeserta.getTableHeader().setBackground(hijauGelap);
        tblPeserta.getTableHeader().setForeground(Color.WHITE);
        tblPeserta.getTableHeader().setOpaque(true);
        tblPeserta.getTableHeader().setReorderingAllowed(false);
        tblPeserta.getTableHeader().setPreferredSize(
            new Dimension(0, 38)
        );

        DefaultTableCellRenderer headerRenderer =
            new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean selected,
                    boolean focused,
                    int row,
                    int column
                ) {
                    JLabel label = (JLabel) super
                        .getTableCellRendererComponent(
                            table, value, selected,
                            focused, row, column
                        );

                    label.setOpaque(true);
                    label.setBackground(hijauGelap);
                    label.setForeground(Color.WHITE);
                    label.setFont(
                        new Font("Segoe UI", Font.BOLD, 12)
                    );
                    label.setHorizontalAlignment(
                        SwingConstants.CENTER
                    );
                    label.setBorder(
                        BorderFactory.createMatteBorder(
                            0, 0, 1, 1, new Color(5, 110, 80)
                        )
                    );

                    return label;
                }
            };

        for (int i = 0; i < tblPeserta.getColumnModel()
                .getColumnCount(); i++) {
            tblPeserta.getColumnModel()
                .getColumn(i)
                .setHeaderRenderer(headerRenderer);
        }

        // WARNA SAAT BARIS DIPILIH
        tblPeserta.setSelectionBackground(
            new Color(167, 243, 208)
        );
        tblPeserta.setSelectionForeground(teks);

        int[] lebar = {
            50, 120, 160, 125, 150, 120, 190
        };

        for (int i = 0; i < lebar.length; i++) {
            tblPeserta.getColumnModel()
                .getColumn(i)
                .setPreferredWidth(lebar[i]);
        }

        scrollTabel.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scrollTabel.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollTabel.getViewport().setBackground(putih);
        scrollTabel.setBorder(new LineBorder(garis, 1));
    }

    // TAMPILKAN DATA KE TABEL
    private void fillTable(List<Peserta> data) {
        modelTabel.setRowCount(0);

        for (Peserta p : data) {
            String gender = p.getJenisKelamin();

            if ("L".equalsIgnoreCase(gender)) {
                gender = "Laki-laki";
            } else if ("P".equalsIgnoreCase(gender)) {
                gender = "Perempuan";
            }

            modelTabel.addRow(new Object[]{
                p.getId(),
                p.getNim(),
                p.getNama(),
                gender,
                p.getProdi(),
                p.getNoHp(),
                p.getEmail()
            });
        }
    }

    // LOAD DATA DARI DATABASE
    private void loadTable() {
        try {
            fillTable(dao.findAll());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memuat data peserta:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    // REFRESH DATA
    private void refreshData() {
        txtCari.setText("");
        loadTable();
        clearForm();
    }

    // PILIH DATA DARI TABEL
    private void pilihDataTabel() {
        int row = tblPeserta.getSelectedRow();

        if (row < 0) return;

        int modelRow = tblPeserta.convertRowIndexToModel(row);

        selectedId = Integer.parseInt(
            modelTabel.getValueAt(modelRow, 0).toString()
        );

        txtNim.setText(
            String.valueOf(modelTabel.getValueAt(modelRow, 1))
        );

        txtNama.setText(
            String.valueOf(modelTabel.getValueAt(modelRow, 2))
        );

        String gender = String.valueOf(
            modelTabel.getValueAt(modelRow, 3)
        );

        rbLaki.setSelected(
            "L".equalsIgnoreCase(gender)
            || "Laki-laki".equalsIgnoreCase(gender)
        );

        rbPerempuan.setSelected(
            "P".equalsIgnoreCase(gender)
            || "Perempuan".equalsIgnoreCase(gender)
        );

        txtProdi.setText(
            String.valueOf(modelTabel.getValueAt(modelRow, 4))
        );

        Object hp = modelTabel.getValueAt(modelRow, 5);
        Object email = modelTabel.getValueAt(modelRow, 6);

        txtNoHp.setText(hp == null ? "" : hp.toString());
        txtEmail.setText(email == null ? "" : email.toString());

        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
    }

    // UBAH DATA
    private void btnUbahActionPerformed() {
        if (selectedId <= 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih peserta dari tabel terlebih dahulu."
            );
            return;
        }

        if (!validasiDataPeserta()) return;

        try {
            if (dao.update(getFormData())) {

                JOptionPane.showMessageDialog(
                    this,
                    "Data peserta berhasil diubah."
                );

                refreshData();

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Data tidak ditemukan atau tidak berhasil diubah."
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal mengubah data:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    // HAPUS DATA

private void btnHapusActionPerformed() {

    if (selectedId <= 0) {
        JOptionPane.showMessageDialog(
            this,
            "Pilih peserta dari tabel terlebih dahulu."
        );
        return;
    }

    int jawab = JOptionPane.showConfirmDialog(
        this,
        "Yakin ingin menghapus peserta "
        + txtNama.getText() + " dari daftar aktif?",
        "Konfirmasi Hapus",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (jawab != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        boolean berhasil = dao.delete(selectedId);

        if (berhasil) {

            JOptionPane.showMessageDialog(
                this,
                "Peserta berhasil dihapus dari daftar aktif."
            );

            refreshData();

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Data tidak ditemukan atau sudah dinonaktifkan."
            );
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Gagal menghapus data:\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();
    }
}


    // CARI DATA
    private void btnCariActionPerformed() {
        String keyword = txtCari.getText().trim();

        try {
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                fillTable(dao.search(keyword));
            }

            clearForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Pencarian gagal:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    // RESET FORM
    private void btnResetActionPerformed() {
        int jawab = JOptionPane.showConfirmDialog(
            this,
            "Kosongkan seluruh input formulir?",
            "Konfirmasi Reset",
            JOptionPane.YES_NO_OPTION
        );

        if (jawab == JOptionPane.YES_OPTION) {
            clearForm();
            txtCari.setText("");
            loadTable();
        }
    }

    // KOSONGKAN FORM
    private void clearForm() {
        selectedId = -1;

        txtNim.setText("");
        txtNama.setText("");
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtKodeAkses.setText("");

        groupGender.clearSelection();
        groupJenis.clearSelection();

        cmbKursus.setSelectedIndex(0);

        cbModul.setSelected(false);
        cbSertifikat.setSelected(false);
        cbRekaman.setSelected(false);

        tblPeserta.clearSelection();

        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    // CETAK DATA
    private void btnCetakActionPerformed() {
        if (tblPeserta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "Tidak ada data untuk dicetak."
            );
            return;
        }

        try {
            tblPeserta.print(
                JTable.PrintMode.FIT_WIDTH,
                null,
                null,
                true,
                null,
                true,
                null
            );

        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal mencetak:\n" + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    // MAIN
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
