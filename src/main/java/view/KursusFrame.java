
package view;

import dao.KursusDAO;
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
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Kursus;

public class KursusFrame extends JFrame {

    private final JTextField kode = new JTextField(20);
    private final JTextField nama = new JTextField(20);
    private final JTextField biaya = new JTextField(20);
    private final JTextField search = new JTextField(18);

    private final JComboBox<String> level = new JComboBox<>(
            new String[]{"BASIC", "INTERMEDIATE", "ADVANCED"}
    );

    private final JTable table = new JTable(
            ViewUtil.tableModel(
                    "ID", "Kode", "Nama Kursus", "Level", "Biaya"
            )
    );

    private final KursusDAO dao = new KursusDAO();

    private int selected = -1;

    private final Color EMERALD = new Color(5, 150, 105);
    private final Color EMERALD_DARK = new Color(4, 120, 87);
    private final Color EMERALD_LIGHT = new Color(209, 250, 229);
    private final Color BACKGROUND = new Color(245, 250, 248);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_DARK = new Color(31, 41, 55);
    private final Color TEXT_MUTED = new Color(107, 114, 128);
    private final Color BORDER = new Color(226, 232, 240);

    public KursusFrame() {
        super("Master Kursus - SIKURSUS Desktop");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1050, 700);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);

        build();
        load("");
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
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel teks = new JPanel();
        teks.setLayout(new BoxLayout(teks, BoxLayout.Y_AXIS));
        teks.setOpaque(false);

        JLabel judul = new JLabel("Master Data Kursus");
        judul.setFont(new Font("SansSerif", Font.BOLD, 23));
        judul.setForeground(WHITE);

        JLabel deskripsi = new JLabel(
                "Kelola informasi, tingkatan, dan biaya program kursus"
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
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel judul = createTitle("Informasi Kursus");

        styleInput(kode);
        styleInput(nama);
        styleInput(biaya);
        styleCombo(level);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addFormRow(form, gbc, 0, "Kode Kursus", kode);
        addFormRow(form, gbc, 1, "Nama Kursus", nama);
        addFormRow(form, gbc, 2, "Level Kursus", level);
        addFormRow(form, gbc, 3, "Biaya Kursus (Rp)", biaya);

        JButton simpan = createButton("Simpan", EMERALD);
        JButton ubah = createButton(
                "Ubah", new Color(37, 99, 235)
        );
        JButton hapus = createButton(
                "Hapus", new Color(220, 38, 38)
        );
        JButton reset = createButton(
                "Reset", new Color(100, 116, 139)
        );

        JPanel tombol = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 9, 0)
        );
        tombol.setOpaque(false);

        tombol.add(simpan);
        tombol.add(ubah);
        tombol.add(hapus);
        tombol.add(reset);

        card.add(judul, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(tombol, BorderLayout.SOUTH);

        simpan.addActionListener(e -> save());
        ubah.addActionListener(e -> update());
        hapus.addActionListener(e -> delete());
        reset.addActionListener(e -> clear());

        getRootPane().setDefaultButton(simpan);

        return card;
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
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

        JLabel judul = createTitle("Daftar Data Kursus");

        search.setPreferredSize(new Dimension(220, 36));
        styleInput(search);

        JButton cari = createButton("Cari", EMERALD);
        JButton refresh = createButton(
                "Refresh", new Color(100, 116, 139)
        );

        JPanel pencarian = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        pencarian.setOpaque(false);

        JLabel labelCari = createLabel("Pencarian Kursus:");

        pencarian.add(labelCari);
        pencarian.add(search);
        pencarian.add(cari);
        pencarian.add(refresh);

        JPanel bagianAtas = new JPanel(new BorderLayout());
        bagianAtas.setOpaque(false);
        bagianAtas.add(judul, BorderLayout.WEST);
        bagianAtas.add(pencarian, BorderLayout.EAST);

        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(WHITE);

        card.add(bagianAtas, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        cari.addActionListener(e -> load(search.getText().trim()));

        search.addActionListener(e -> load(search.getText().trim()));

        refresh.addActionListener(e -> {
            search.setText("");
            load("");
        });

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

        if (field != search) {
            field.setPreferredSize(new Dimension(200, 36));
        }
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setForeground(TEXT_DARK);
        combo.setBackground(WHITE);
        combo.setPreferredSize(new Dimension(200, 36));
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

    private void styleTable() {
        table.setRowHeight(34);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(TEXT_DARK);
        table.setBackground(WHITE);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.setSelectionBackground(EMERALD_LIGHT);
        table.setSelectionForeground(EMERALD_DARK);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setFillsViewportHeight(true);
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

        table.getColumnModel().getColumn(1)
                .setCellRenderer(center);

        table.getColumnModel().getColumn(3)
                .setCellRenderer(center);

        table.getColumnModel().getColumn(0).setPreferredWidth(55);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(230);
        table.getColumnModel().getColumn(3).setPreferredWidth(140);
        table.getColumnModel().getColumn(4).setPreferredWidth(140);
    }

    private Kursus data() {
        String kodeKursus = kode.getText().trim();
        String namaKursus = nama.getText().trim();
        String biayaText = biaya.getText().trim();

        if (kodeKursus.isEmpty()) {
            throw new IllegalArgumentException(
                    "Kode kursus tidak boleh kosong."
            );
        }

        if (namaKursus.isEmpty()) {
            throw new IllegalArgumentException(
                    "Nama kursus tidak boleh kosong."
            );
        }

        if (biayaText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Biaya kursus tidak boleh kosong."
            );
        }

        double nominal;

        try {
            nominal = Double.parseDouble(biayaText);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "Biaya harus berupa angka."
            );
        }

        if (nominal < 0) {
            throw new IllegalArgumentException(
                    "Biaya tidak boleh negatif."
            );
        }

        return new Kursus(
                selected < 0 ? 0 : selected,
                kodeKursus,
                namaKursus,
                level.getSelectedItem().toString(),
                nominal
        );
    }

    private void save() {
        try {
            Kursus kursus = data();

            dao.insert(kursus);

            JOptionPane.showMessageDialog(
                    this,
                    "Data kursus berhasil disimpan.",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );

            load("");
            clear();

        } catch (Exception ex) {
            handle(ex, "Menyimpan kursus");
        }
    }

    private void update() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Pilih data kursus yang ingin diubah."
            );
            return;
        }

        try {
            Kursus kursus = data();

            dao.update(kursus);

            JOptionPane.showMessageDialog(
                    this,
                    "Data kursus berhasil diperbarui.",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );

            load("");
            clear();

        } catch (Exception ex) {
            handle(ex, "Mengubah kursus");
        }
    }

    private void delete() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Pilih data kursus yang ingin dihapus."
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah kamu yakin ingin menghapus kursus ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.delete(selected);

            JOptionPane.showMessageDialog(
                    this,
                    "Data kursus berhasil dihapus.",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );

            load("");
            clear();

        } catch (SQLException ex) {
            ViewUtil.sqlError(
                    this,
                    "Menghapus kursus",
                    ex
            );
        }
    }

    private void load(String q) {
        try {
            List<Kursus> list = dao.search(q);

            DefaultTableModel model =
                    (DefaultTableModel) table.getModel();

            model.setRowCount(0);

            for (Kursus kursus : list) {
                model.addRow(new Object[]{
                    kursus.getIdKursus(),
                    kursus.getKode(),
                    kursus.getNama(),
                    kursus.getLevel(),
                    String.format("%.0f", kursus.getBiaya())
                });
            }

        } catch (SQLException ex) {
            ViewUtil.sqlError(
                    this,
                    "Memuat data kursus",
                    ex
            );
        }
    }

    private void pick() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);

        selected = Integer.parseInt(
                table.getModel().getValueAt(modelRow, 0).toString()
        );

        kode.setText(
                table.getModel().getValueAt(modelRow, 1).toString()
        );

        nama.setText(
                table.getModel().getValueAt(modelRow, 2).toString()
        );

        level.setSelectedItem(
                table.getModel().getValueAt(modelRow, 3).toString()
        );

        biaya.setText(
                table.getModel().getValueAt(modelRow, 4).toString()
        );
    }

    private void clear() {
        selected = -1;

        kode.setText("");
        nama.setText("");
        biaya.setText("");

        level.setSelectedIndex(0);

        table.clearSelection();

        kode.requestFocus();
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
                    ex.getMessage(),
                    "Validasi Data",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KursusFrame().setVisible(true);
            }
        });
    }
}
