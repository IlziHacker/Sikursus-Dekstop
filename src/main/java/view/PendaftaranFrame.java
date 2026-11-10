
package view;

import dao.KelasDAO;
import dao.PendaftaranDAO;
import dao.PesertaDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import model.Pendaftaran;
import report.ReportService;
import util.LookupItem;
import util.Validation;

public class PendaftaranFrame extends JFrame {

    private static final Color EMERALD = new Color(5, 150, 105);
    private static final Color EMERALD_DARK = new Color(4, 120, 87);
    private static final Color EMERALD_LIGHT = new Color(209, 250, 229);
    private static final Color BG = new Color(245, 249, 247);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color RED = new Color(220, 70, 70);
    private static final Color BLUE = new Color(59, 130, 246);
    private static final Color GRAY = new Color(100, 116, 139);

    private final JComboBox<LookupItem> peserta = new JComboBox<>();
    private final JComboBox<LookupItem> kelas = new JComboBox<>();
    private final JTextField tanggal = new JTextField(ViewUtil.today());
    private final JTextField search = new JTextField(18);

    private final JComboBox<String> status = new JComboBox<>(
        new String[]{"DAFTAR", "AKTIF", "SELESAI", "BATAL"}
    );

    private final JTable table = new JTable(
        ViewUtil.tableModel(
            "ID", "NIM", "Peserta", "Kelas",
            "Kursus", "Tanggal", "Status"
        )
    );

    private final PendaftaranDAO dao = new PendaftaranDAO();
    private int selected = -1;

    public PendaftaranFrame() {
        super("Transaksi Pendaftaran - SIKURSUS Desktop");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ViewUtil.setupFrame(this, 1080, 700);
        build();
        loadLookups();
        load("");
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel header = createHeader();

        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);

        JPanel formCard = createFormCard();
        JPanel tableCard = createTableCard();

        body.add(formCard, BorderLayout.NORTH);
        body.add(tableCard, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD_DARK);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Transaksi Pendaftaran");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "Kelola pendaftaran peserta ke dalam kelas kursus"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(EMERALD_LIGHT);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        JLabel badge = new JLabel("SIKURSUS");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 14));
        badge.setForeground(Color.WHITE);
        badge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(110, 231, 183), 1
            ),
            new EmptyBorder(9, 15, 9, 15)
        ));

        header.add(titlePanel, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        return header;
    }

    private JPanel createFormCard() {
        JPanel card = createCard();

        JLabel heading = new JLabel("Form Pendaftaran");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
            "Masukkan informasi peserta dan kelas yang akan diikuti."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(4));
        headingPanel.add(description);

        styleCombo(peserta);
        styleCombo(kelas);
        styleCombo(status);
        styleInput(tanggal);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        addField(fields, "Peserta", peserta, 0, 0);
        addField(fields, "Kelas", kelas, 1, 0);
        addField(fields, "Tanggal Daftar (yyyy-MM-dd)", tanggal, 0, 1);
        addField(fields, "Status", status, 1, 1);

        JButton simpan = createButton("Simpan", EMERALD);
        JButton ubah = createButton("Ubah", BLUE);
        JButton hapus = createButton("Hapus", RED);
        JButton reset = createButton("Reset", GRAY);
        JButton semua = createButton("Cetak Semua", EMERALD_DARK);
        JButton terpilih = createButton("Cetak Terpilih", EMERALD);

        JPanel actions = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        actions.setOpaque(false);

        actions.add(simpan);
        actions.add(ubah);
        actions.add(hapus);
        actions.add(reset);
        actions.add(semua);
        actions.add(terpilih);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(10, 0, 0, 0));
        bottom.add(actions, BorderLayout.EAST);

        card.add(headingPanel, BorderLayout.NORTH);
        card.add(fields, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        simpan.addActionListener(e -> save());
        ubah.addActionListener(e -> update());
        hapus.addActionListener(e -> delete());
        reset.addActionListener(e -> clear());

        semua.addActionListener(
            e -> ReportService.showPendaftaranSemua(this)
        );

        terpilih.addActionListener(e -> {
            if (selected < 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Pilih baris pendaftaran."
                );
            } else {
                ReportService.showPendaftaranById(this, selected);
            }
        });

        getRootPane().setDefaultButton(simpan);

        return card;
    }

    private JPanel createTableCard() {
        JPanel card = createCard();

        JLabel heading = new JLabel("Data Pendaftaran");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
            "Daftar seluruh peserta yang telah terdaftar pada kelas kursus."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(heading);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(description);

        styleInput(search);
        search.setPreferredSize(new Dimension(220, 38));

        JButton cari = createButton("Cari", EMERALD);

        JPanel searchPanel = new JPanel(
            new BorderLayout(8, 0)
        );
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(310, 38));
        searchPanel.add(search, BorderLayout.CENTER);
        searchPanel.add(cari, BorderLayout.EAST);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setOpaque(false);
        top.add(titlePanel, BorderLayout.WEST);
        top.add(searchPanel, BorderLayout.EAST);

        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        card.add(top, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        cari.addActionListener(e -> load(search.getText()));
        search.addActionListener(e -> load(search.getText()));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pick();
            }
        });

        return card;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(17, 19, 17, 19)
        ));
        return panel;
    }

    private void addField(
        JPanel parent,
        String labelText,
        JComponent component,
        int column,
        int row
    ) {
        JPanel field = new JPanel(new BorderLayout(0, 7));
        field.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT);

        field.add(label, BorderLayout.NORTH);
        field.add(component, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(8, 7, 8, 7);

        parent.add(field, gbc);
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(EMERALD);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(180, 38));
    }

    private void styleCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(180, 38));
        combo.setBorder(BorderFactory.createLineBorder(BORDER));
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        return button;
    }

    private void styleTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setRowHeight(34);
        table.setGridColor(BORDER);
        table.setSelectionBackground(EMERALD_LIGHT);
        table.setSelectionForeground(EMERALD_DARK);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        table.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 12)
        );
        table.getTableHeader().setBackground(EMERALD_DARK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer center =
            new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(1).setCellRenderer(center);
        table.getColumnModel().getColumn(5).setCellRenderer(center);
        table.getColumnModel().getColumn(6).setCellRenderer(center);
    }

    private void loadLookups() {
        try {
            peserta.removeAllItems();

            for (LookupItem x : new PesertaDAO().lookup()) {
                peserta.addItem(x);
            }

            kelas.removeAllItems();

            for (LookupItem x : new KelasDAO().lookup()) {
                kelas.addItem(x);
            }

        } catch (SQLException ex) {
            ViewUtil.sqlError(
                this,
                "Memuat pilihan pendaftaran",
                ex
            );
        }
    }

    private Pendaftaran data() {
        LookupItem p = (LookupItem) peserta.getSelectedItem();
        LookupItem k = (LookupItem) kelas.getSelectedItem();

        if (p == null || k == null) {
            throw new IllegalArgumentException(
                "Peserta dan kelas wajib tersedia"
            );
        }

        Pendaftaran x = new Pendaftaran();
        x.setIdPendaftaran(Math.max(selected, 0));
        x.setIdPeserta(p.getId());
        x.setIdKelas(k.getId());
        x.setTanggalDaftar(
            Validation.date(tanggal.getText(), "Tanggal daftar")
        );
        x.setStatus(status.getSelectedItem().toString());

        return x;
    }

    private void save() {
        try {
            dao.insert(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Menyimpan pendaftaran");
        }
    }

    private void update() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih pendaftaran."
            );
            return;
        }

        try {
            dao.update(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Mengubah pendaftaran");
        }
    }

    private void delete() {
        if (selected < 0) {
            return;
        }

        if (JOptionPane.showConfirmDialog(
            this,
            "Hapus pendaftaran? Pembayaran terkait harus dihapus lebih dahulu.",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        ) != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.delete(selected);
            load("");
            clear();
        } catch (SQLException ex) {
            ViewUtil.sqlError(
                this,
                "Menghapus pendaftaran",
                ex
            );
        }
    }

    private void load(String q) {
        try {
            List<Pendaftaran> list = dao.search(q);

            DefaultTableModel m =
                (DefaultTableModel) table.getModel();

            m.setRowCount(0);

            for (Pendaftaran x : list) {
                m.addRow(new Object[]{
                    x.getIdPendaftaran(),
                    x.getNim(),
                    x.getNamaPeserta(),
                    x.getNamaKelas(),
                    x.getNamaKursus(),
                    x.getTanggalDaftar(),
                    x.getStatus()
                });
            }

        } catch (SQLException ex) {
            ViewUtil.sqlError(
                this,
                "Memuat pendaftaran",
                ex
            );
        }
    }

    private void pick() {
        int r = table.getSelectedRow();

        if (r < 0) {
            return;
        }

        int modelRow = table.convertRowIndexToModel(r);

        selected = Integer.parseInt(
            table.getModel().getValueAt(modelRow, 0).toString()
        );

        try {
            for (Pendaftaran x : dao.findAll()) {
                if (x.getIdPendaftaran() == selected) {
                    ViewUtil.selectLookup(
                        peserta,
                        x.getIdPeserta()
                    );

                    ViewUtil.selectLookup(
                        kelas,
                        x.getIdKelas()
                    );

                    tanggal.setText(
                        x.getTanggalDaftar().toString()
                    );

                    status.setSelectedItem(x.getStatus());
                    break;
                }
            }

        } catch (SQLException ex) {
            ViewUtil.sqlError(
                this,
                "Membaca pendaftaran",
                ex
            );
        }
    }

    private void clear() {
        selected = -1;
        tanggal.setText(ViewUtil.today());
        status.setSelectedIndex(0);
        table.clearSelection();
        loadLookups();
        peserta.requestFocus();
    }

    private void handle(Exception ex, String action) {
        if (ex instanceof SQLException) {
            ViewUtil.sqlError(
                this,
                action,
                (SQLException) ex
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage()
            );
        }
    }
}
