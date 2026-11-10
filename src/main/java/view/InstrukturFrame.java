
package view;

import dao.InstrukturDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.Instruktur;

public class InstrukturFrame extends JFrame {

    private static final Color EMERALD = new Color(5, 150, 105);
    private static final Color EMERALD_DARK = new Color(4, 120, 87);
    private static final Color BG = new Color(243, 247, 245);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color RED = new Color(220, 53, 69);

    private final JTextField nama = new JTextField();
    private final JTextField keahlian = new JTextField();
    private final JTextField hp = new JTextField();
    private final JTextField email = new JTextField();
    private final JTextField search = new JTextField(20);

    private final JTable table = new JTable(
        ViewUtil.tableModel("ID", "Nama", "Keahlian", "No. HP", "Email")
    );

    private final InstrukturDAO dao = new InstrukturDAO();
    private int selected = -1;

    public InstrukturFrame() {
        super("Master Instruktur - SIKURSUS Desktop");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        build();
        load("");
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(0, 20));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(22, 25, 22, 25));

        root.add(createHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BG);

        content.add(createFormCard(), BorderLayout.NORTH);
        content.add(createTableCard(), BorderLayout.CENTER);

        root.add(content, BorderLayout.CENTER);

        setContentPane(root);

        getRootPane().setDefaultButton(createSaveButtonReference());
    }

    private JButton createSaveButtonReference() {
        return saveButton;
    }

    private final JButton saveButton = createButton("Simpan", EMERALD, WHITE);
    private final JButton updateButton = createButton("Ubah", new Color(59, 130, 246), WHITE);
    private final JButton deleteButton = createButton("Hapus", RED, WHITE);
    private final JButton resetButton = createButton("Reset", new Color(107, 114, 128), WHITE);
    private final JButton searchButton = createButton("Cari", EMERALD, WHITE);

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD);
        header.setBorder(new EmptyBorder(22, 25, 22, 25));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Master Data Instruktur");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(WHITE);

        JLabel subtitle = new JLabel("Kelola informasi dan data instruktur kursus");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(220, 252, 231));

        titlePanel.add(title);
        titlePanel.add(subtitle);

        JLabel badge = new JLabel("SIKURSUS");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 15));
        badge.setForeground(WHITE);
        badge.setBorder(new CompoundBorder(
            new LineBorder(new Color(167, 243, 208), 1, true),
            new EmptyBorder(9, 16, 9, 16)
        ));

        header.add(titlePanel, BorderLayout.CENTER);
        header.add(badge, BorderLayout.EAST);

        return header;
    }

    private JPanel createFormCard() {
        JPanel card = createCard("Form Instruktur");

        JPanel fields = new JPanel(new GridLayout(2, 2, 18, 13));
        fields.setBackground(WHITE);
        fields.setBorder(new EmptyBorder(5, 0, 15, 0));

        fields.add(createField("Nama Instruktur", nama));
        fields.add(createField("Keahlian", keahlian));
        fields.add(createField("Nomor HP", hp));
        fields.add(createField("Email", email));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        actions.setBackground(WHITE);

        actions.add(saveButton);
        actions.add(updateButton);
        actions.add(deleteButton);
        actions.add(resetButton);

        card.add(fields, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> save());
        updateButton.addActionListener(e -> update());
        deleteButton.addActionListener(e -> delete());
        resetButton.addActionListener(e -> clear());

        return card;
    }

    private JPanel createTableCard() {
        JPanel card = createCard("Data Instruktur");

        JPanel searchPanel = new JPanel(new BorderLayout(12, 0));
        searchPanel.setBackground(WHITE);
        searchPanel.setBorder(new EmptyBorder(0, 0, 12, 0));

        JLabel searchLabel = new JLabel("Cari instruktur");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchLabel.setForeground(TEXT);

        JPanel searchInput = new JPanel(new BorderLayout(8, 0));
        searchInput.setBackground(WHITE);

        styleField(search);

        searchInput.add(search, BorderLayout.CENTER);
        searchInput.add(searchButton, BorderLayout.EAST);

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchInput, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(BORDER, 1));
        scroll.getViewport().setBackground(WHITE);

        styleTable();

        card.add(searchPanel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        searchButton.addActionListener(e -> load(search.getText()));
        search.addActionListener(e -> load(search.getText()));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pick();
            }
        });

        return card;
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(18, 20, 18, 20)
        ));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        heading.setForeground(EMERALD_DARK);

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setBackground(WHITE);
        headingPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headingPanel.add(heading, BorderLayout.WEST);

        card.add(headingPanel, BorderLayout.NORTH);

        return card;
    }

    private JPanel createField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 7));
        panel.setBackground(WHITE);

        JLabel title = new JLabel(label);
        title.setFont(new Font("Segoe UI", Font.BOLD, 12));
        title.setForeground(TEXT);

        styleField(field);

        panel.add(title, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setCaretColor(EMERALD);
        field.setPreferredSize(new Dimension(150, 37));
        field.setBorder(new CompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(7, 10, 7, 10)
        ));
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(9, 17, 9, 17));
        button.setPreferredSize(new Dimension(95, 36));

        return button;
    }

    private void styleTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(209, 250, 229));
        table.setSelectionForeground(new Color(6, 78, 59));
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(WHITE);
        header.setBackground(EMERALD_DARK);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);
    }

    private Instruktur data() {
        Instruktur x = new Instruktur();
        x.setId(Math.max(selected, 0));
        x.setNama(nama.getText());
        x.setKeahlian(keahlian.getText());
        x.setNoHp(hp.getText());
        x.setEmail(email.getText());
        return x;
    }

    private void save() {
        try {
            dao.insert(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Menyimpan instruktur");
        }
    }

    private void update() {
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Pilih instruktur.");
            return;
        }

        try {
            dao.update(data());
            load("");
            clear();
        } catch (Exception ex) {
            handle(ex, "Mengubah instruktur");
        }
    }

    private void delete() {
        if (selected < 0) {
            return;
        }

        if (JOptionPane.showConfirmDialog(
                this,
                "Hapus instruktur?",
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
            ViewUtil.sqlError(this, "Menghapus instruktur", ex);
        }
    }

    private void load(String q) {
        try {
            List<Instruktur> list = dao.search(q);

            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);

            for (Instruktur x : list) {
                m.addRow(new Object[]{
                    x.getId(),
                    x.getNama(),
                    x.getKeahlian(),
                    x.getNoHp(),
                    x.getEmail()
                });
            }
        } catch (SQLException ex) {
            ViewUtil.sqlError(this, "Memuat instruktur", ex);
        }
    }

    private void pick() {
        int r = table.getSelectedRow();

        if (r < 0) {
            return;
        }

        selected = Integer.parseInt(table.getValueAt(r, 0).toString());
        nama.setText(table.getValueAt(r, 1).toString());
        keahlian.setText(table.getValueAt(r, 2).toString());
        hp.setText(String.valueOf(table.getValueAt(r, 3)));
        email.setText(String.valueOf(table.getValueAt(r, 4)));
    }

    private void clear() {
        selected = -1;

        nama.setText("");
        keahlian.setText("");
        hp.setText("");
        email.setText("");

        table.clearSelection();
        nama.requestFocus();
    }

    private void handle(Exception ex, String action) {
        if (ex instanceof SQLException) {
            ViewUtil.sqlError(this, action, (SQLException) ex);
        } else {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
