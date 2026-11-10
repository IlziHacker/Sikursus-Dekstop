
package view;

import dao.InstrukturDAO;
import dao.KelasDAO;
import dao.KursusDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import model.Kelas;
import util.LookupItem;
import util.Validation;

public class KelasFrame extends JFrame {

    private static final Color EMERALD = new Color(5, 150, 105);
    private static final Color EMERALD_DARK = new Color(4, 120, 87);
    private static final Color EMERALD_LIGHT = new Color(209, 250, 229);
    private static final Color BG = new Color(245, 249, 247);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color RED = new Color(220, 70, 70);

    private final JComboBox<LookupItem> kursus = new JComboBox<>();
    private final JComboBox<LookupItem> instruktur = new JComboBox<>();
    private final JTextField nama = new JTextField();
    private final JTextField tanggal = new JTextField(ViewUtil.today());
    private final JTextField kapasitas = new JTextField("20");
    private final JTextField search = new JTextField(18);

    private final JTable table = new JTable(
        ViewUtil.tableModel(
            "ID", "Kelas", "Kursus", "Instruktur",
            "Tanggal Mulai", "Kapasitas"
        )
    );

    private final KelasDAO dao = new KelasDAO();
    private int selected = -1;

    public KelasFrame() {
        super("Master Kelas - SIKURSUS Desktop");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ViewUtil.setupFrame(this, 1040, 720);
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

        getRootPane().setDefaultButton(createDefaultButtonReference());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pick();
            }
        });
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD_DARK);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Master Data Kelas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "Kelola jadwal, kursus, instruktur, dan kapasitas kelas"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(209, 250, 229));

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

        JLabel heading = new JLabel("Form Kelas");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
            "Masukkan informasi kelas pada formulir berikut."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(4));
        headingPanel.add(description);

        styleInput(nama);
        styleInput(tanggal);
        styleInput(kapasitas);
        styleCombo(kursus);
        styleCombo(instruktur);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        addField(fields, "Kursus", kursus, 0, 0);
        addField(fields, "Instruktur", instruktur, 1, 0);
        addField(fields, "Nama Kelas", nama, 0, 1);
        addField(fields, "Tanggal Mulai (yyyy-MM-dd)", tanggal, 1, 1);
        addField(fields, "Kapasitas", kapasitas, 0, 2);

        JButton simpan = createButton("Simpan", EMERALD);
        JButton ubah = createButton("Ubah", new Color(59, 130, 246));
        JButton hapus = createButton("Hapus", RED);
        JButton reset = createButton("Reset", new Color(100, 116, 139));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        actions.setOpaque(false);
        actions.add(simpan);
        actions.add(ubah);
        actions.add(hapus);
        actions.add(reset);

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

        getRootPane().setDefaultButton(simpan);

        return card;
    }

    private JPanel createTableCard() {
        JPanel card = createCard();

        JLabel heading = new JLabel("Data Kelas");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
            "Daftar kelas yang telah tersimpan di dalam sistem."
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

        JButton cari = createButton("Cari", EMERALD);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(300, 38));
        searchPanel.add(search, BorderLayout.CENTER);
        searchPanel.add(cari, BorderLayout.EAST);

        JPanel top = new JPanel(new BorderLayout(10, 12));
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
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(EMERALD);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(8, 11, 8, 11)
        ));
        field.setPreferredSize(new Dimension(180, 38));
    }

    private void styleCombo(JComboBox<LookupItem> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(180, 38));
        combo.setBorder(BorderFactory.createLineBorder(BORDER));
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 17, 10, 17));
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

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(5).setCellRenderer(center);
    }

    private JButton createDefaultButtonReference() {
        return null;
    }

    private void loadLookups() {
        try {
            kursus.removeAllItems();
            for (LookupItem x : new KursusDAO().lookup()) {
                kursus.addItem(x);
            }

            instruktur.removeAllItems();
            for (LookupItem x : new InstrukturDAO().lookup()) {
                instruktur.addItem(x);
            }
        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Memuat pilihan kelas", ex);
        }
    }

    private Kelas data() {
        LookupItem k = (LookupItem) kursus.getSelectedItem();
        LookupItem i = (LookupItem) instruktur.getSelectedItem();

        if (k == null || i == null) {
            throw new IllegalArgumentException(
                "Kursus dan instruktur wajib tersedia"
            );
        }

        Kelas x = new Kelas();
        x.setIdKelas(Math.max(selected, 0));
        x.setIdKursus(k.getId());
        x.setIdInstruktur(i.getId());
        x.setNamaKelas(nama.getText());
        x.setTanggalMulai(
            Validation.date(tanggal.getText(), "Tanggal mulai")
        );
        x.setKapasitas(
            Validation.positiveInt(kapasitas.getText(), "Kapasitas")
        );

        return x;
    }

    private void save() {
        try {
            dao.insert(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Menyimpan kelas");
        }
    }

    private void update() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Pilih kelas.");
            return;
        }

        try {
            dao.update(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Mengubah kelas");
        }
    }

    private void delete() {
        if (selected < 0) {
            return;
        }

        if (JOptionPane.showConfirmDialog(
            this,
            "Hapus kelas?",
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
            ViewUtil.sqlError(this, "Menghapus kelas", ex);
        }
    }

    private void load(String q) {
        try {
            List<Kelas> list = dao.search(q);
            DefaultTableModel m =
                (DefaultTableModel) table.getModel();

            m.setRowCount(0);

            for (Kelas x : list) {
                m.addRow(new Object[]{
                    x.getIdKelas(),
                    x.getNamaKelas(),
                    x.getKodeKursus() + " - " + x.getNamaKursus(),
                    x.getNamaInstruktur(),
                    x.getTanggalMulai(),
                    x.getKapasitas()
                });
            }
        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Memuat kelas", ex);
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
            for (Kelas x : dao.findAll()) {
                if (x.getIdKelas() == selected) {
                    nama.setText(x.getNamaKelas());
                    tanggal.setText(x.getTanggalMulai().toString());
                    kapasitas.setText(
                        String.valueOf(x.getKapasitas())
                    );

                    ViewUtil.selectLookup(kursus, x.getIdKursus());
                    ViewUtil.selectLookup(
                        instruktur,
                        x.getIdInstruktur()
                    );

                    break;
                }
            }
        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Membaca kelas", ex);
        }
    }

    private void clear() {
        selected = -1;
        nama.setText("");
        tanggal.setText(ViewUtil.today());
        kapasitas.setText("20");

        if (kursus.getItemCount() > 0) {
            kursus.setSelectedIndex(0);
        }

        if (instruktur.getItemCount() > 0) {
            instruktur.setSelectedIndex(0);
        }

        table.clearSelection();
        loadLookups();
        nama.requestFocus();
    }

    private void handle(Exception ex, String action) {
        if (ex instanceof SQLException) {
            ViewUtil.sqlError(
                this,
                action,
                (SQLException) ex
            );
        } else {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
