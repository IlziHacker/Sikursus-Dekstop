
package view;

import dao.PembayaranDAO;
import dao.PendaftaranDAO;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import model.Pembayaran;
import report.ReportService;
import util.LookupItem;
import util.Validation;

public class PembayaranFrame extends JFrame {

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

    private final JComboBox<LookupItem> pendaftaran = new JComboBox<>();
    private final JTextField tanggal = new JTextField(ViewUtil.today());
    private final JTextField nominal = new JTextField();
    private final JTextField search = new JTextField(18);
    private final JTextField awal = new JTextField(ViewUtil.today());
    private final JTextField akhir = new JTextField(ViewUtil.today());

    private final JComboBox<String> metode = new JComboBox<>(
        new String[]{"TUNAI", "TRANSFER", "QRIS"}
    );

    private final JComboBox<String> status = new JComboBox<>(
        new String[]{"MENUNGGU", "LUNAS", "BATAL"}
    );

    private final JTable table = new JTable(
        ViewUtil.tableModel(
            "ID", "Pendaftaran", "Peserta", "Kelas",
            "Tanggal", "Nominal", "Metode", "Status"
        )
    );

    private final PembayaranDAO dao = new PembayaranDAO();
    private int selected = -1;

    public PembayaranFrame() {
        super("Transaksi Pembayaran - SIKURSUS Desktop");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ViewUtil.setupFrame(this, 1150, 740);
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

        JLabel title = new JLabel("Transaksi Pembayaran");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "Kelola pembayaran, status transaksi, dan laporan pembayaran"
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

        JLabel heading = new JLabel("Form Pembayaran");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
            "Masukkan informasi transaksi pembayaran peserta."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(4));
        headingPanel.add(description);

        styleInput(tanggal);
        styleInput(nominal);
        styleCombo(pendaftaran);
        styleCombo(metode);
        styleCombo(status);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        addField(fields, "Pendaftaran", pendaftaran, 0, 0);
        addField(fields, "Tanggal Bayar (yyyy-MM-dd)", tanggal, 1, 0);
        addField(fields, "Nominal", nominal, 0, 1);
        addField(fields, "Metode", metode, 1, 1);
        addField(fields, "Status", status, 0, 2);

        JButton simpan = createButton("Simpan", EMERALD);
        JButton ubah = createButton("Ubah", BLUE);
        JButton hapus = createButton("Hapus", RED);
        JButton reset = createButton("Reset", GRAY);
        JButton semua = createButton("Cetak Semua", EMERALD_DARK);

        JPanel actions = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 9, 0)
        );
        actions.setOpaque(false);

        actions.add(simpan);
        actions.add(ubah);
        actions.add(hapus);
        actions.add(reset);
        actions.add(semua);

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
            e -> ReportService.showPembayaranSemua(this)
        );

        getRootPane().setDefaultButton(simpan);

        return card;
    }

    private JPanel createTableCard() {
        JPanel card = createCard();

        JLabel heading = new JLabel("Data Pembayaran");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
            "Cari transaksi dan cetak laporan berdasarkan periode tanggal."
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
        styleInput(awal);
        styleInput(akhir);

        search.setPreferredSize(new Dimension(175, 36));
        awal.setPreferredSize(new Dimension(115, 36));
        akhir.setPreferredSize(new Dimension(115, 36));

        JButton cari = createButton("Cari", EMERALD);
        JButton periode = createButton("Cetak Periode", EMERALD_DARK);

        JPanel filter = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 7)
        );
        filter.setOpaque(false);

        JLabel cariLabel = createFilterLabel("Cari");
        JLabel awalLabel = createFilterLabel("Awal");
        JLabel akhirLabel = createFilterLabel("Akhir");

        filter.add(cariLabel);
        filter.add(search);
        filter.add(cari);
        filter.add(Box.createHorizontalStrut(8));
        filter.add(awalLabel);
        filter.add(awal);
        filter.add(akhirLabel);
        filter.add(akhir);
        filter.add(periode);

        JPanel top = new JPanel(new BorderLayout(0, 10));
        top.setOpaque(false);
        top.add(titlePanel, BorderLayout.NORTH);
        top.add(filter, BorderLayout.CENTER);

        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        card.add(top, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        cari.addActionListener(e -> load(search.getText()));
        search.addActionListener(e -> load(search.getText()));
        periode.addActionListener(e -> period());

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

    private JLabel createFilterLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(TEXT);
        return label;
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
        table.getColumnModel().getColumn(4).setCellRenderer(center);
        table.getColumnModel().getColumn(7).setCellRenderer(center);
    }

    private void loadLookups() {
        try {
            pendaftaran.removeAllItems();

            for (LookupItem x : new PendaftaranDAO().lookup()) {
                pendaftaran.addItem(x);
            }
        } catch (SQLException ex) {
            ViewUtil.sqlError(
                this,
                "Memuat pilihan pembayaran",
                ex
            );
        }
    }

    private Pembayaran data() {
        LookupItem p = (LookupItem) pendaftaran.getSelectedItem();

        if (p == null) {
            throw new IllegalArgumentException(
                "Pendaftaran wajib tersedia"
            );
        }

        Pembayaran x = new Pembayaran();
        x.setIdPembayaran(Math.max(selected, 0));
        x.setIdPendaftaran(p.getId());
        x.setTanggalBayar(
            Validation.date(tanggal.getText(), "Tanggal bayar")
        );
        x.setNominal(
            Validation.positiveMoney(nominal.getText(), "Nominal")
        );
        x.setMetode(metode.getSelectedItem().toString());
        x.setStatus(status.getSelectedItem().toString());

        return x;
    }

    private void save() {
        try {
            dao.insert(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Menyimpan pembayaran");
        }
    }

    private void update() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih pembayaran."
            );
            return;
        }

        try {
            dao.update(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Mengubah pembayaran");
        }
    }

    private void delete() {
        if (selected < 0) {
            return;
        }

        if (JOptionPane.showConfirmDialog(
            this,
            "Hapus pembayaran?",
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
                "Menghapus pembayaran",
                ex
            );
        }
    }

    private void load(String q) {
        try {
            List<Pembayaran> list = dao.search(q);

            DefaultTableModel m =
                (DefaultTableModel) table.getModel();

            m.setRowCount(0);

            for (Pembayaran x : list) {
                m.addRow(new Object[]{
                    x.getIdPembayaran(),
                    x.getIdPendaftaran(),
                    x.getNamaPeserta(),
                    x.getNamaKelas(),
                    x.getTanggalBayar(),
                    x.getNominal(),
                    x.getMetode(),
                    x.getStatus()
                });
            }
        } catch (SQLException ex) {
            ViewUtil.sqlError(
                this,
                "Memuat pembayaran",
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

        int pid = Integer.parseInt(
            table.getModel().getValueAt(modelRow, 1).toString()
        );

        ViewUtil.selectLookup(pendaftaran, pid);

        tanggal.setText(
            table.getModel().getValueAt(modelRow, 4).toString()
        );

        nominal.setText(
            table.getModel().getValueAt(modelRow, 5).toString()
        );

        metode.setSelectedItem(
            table.getModel().getValueAt(modelRow, 6)
        );

        status.setSelectedItem(
            table.getModel().getValueAt(modelRow, 7)
        );
    }

    private void period() {
        try {
            Date a = Validation.date(
                awal.getText(),
                "Tanggal awal"
            );

            Date b = Validation.date(
                akhir.getText(),
                "Tanggal akhir"
            );

            if (a.after(b)) {
                throw new IllegalArgumentException(
                    "Tanggal awal tidak boleh setelah tanggal akhir"
                );
            }

            ReportService.showPembayaranPeriode(this, a, b);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage()
            );
        }
    }

    private void clear() {
        selected = -1;
        tanggal.setText(ViewUtil.today());
        nominal.setText("");
        metode.setSelectedIndex(0);
        status.setSelectedIndex(0);
        table.clearSelection();
        loadLookups();
        pendaftaran.requestFocus();
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
