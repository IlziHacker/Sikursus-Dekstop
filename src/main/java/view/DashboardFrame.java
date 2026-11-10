
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import java.sql.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;
import report.ReportService;
import util.UserSession;
import util.WindowManager;

public class DashboardFrame extends JFrame {

    private static final Color EMERALD = new Color(5, 150, 105);
    private static final Color EMERALD_DARK = new Color(4, 120, 87);
    private static final Color EMERALD_LIGHT = new Color(209, 250, 229);
    private static final Color BACKGROUND = new Color(245, 250, 248);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color BORDER = new Color(226, 232, 240);

    private final JLabel lblUser = new JLabel();
    private final JLabel lblRole = new JLabel();

    private final JMenuItem miUsers = new JMenuItem("Users");
    private final JMenuItem miPeserta = new JMenuItem("Peserta");
    private final JMenuItem miInstruktur = new JMenuItem("Instruktur");
    private final JMenuItem miKursus = new JMenuItem("Kursus");
    private final JMenuItem miKelas = new JMenuItem("Kelas");

    public DashboardFrame() {
        super("Dashboard - SIKURSUS Desktop");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ViewUtil.setupFrame(this, 1100, 720);
        setMinimumSize(new Dimension(900, 600));

        buildMenu();
        buildBody();
        applySession();
    }

    private void buildBody() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        root.add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setBackground(BACKGROUND);
        body.setBorder(new EmptyBorder(22, 28, 24, 28));

        body.add(buildWelcomePanel(), BorderLayout.NORTH);
        body.add(buildContentPanel(), BorderLayout.CENTER);
        body.add(buildFooter(), BorderLayout.SOUTH);

        root.add(body, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EMERALD_DARK);
        header.setBorder(new EmptyBorder(18, 30, 18, 30));

        JPanel brand = new JPanel(new GridLayout(2, 1, 0, 3));
        brand.setOpaque(false);

        JLabel title = new JLabel("SIKURSUS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("COURSE MANAGEMENT SYSTEM");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        subtitle.setForeground(new Color(209, 250, 229));

        brand.add(title);
        brand.add(subtitle);

        JPanel account = new JPanel(new GridLayout(2, 1, 0, 4));
        account.setOpaque(false);

        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(Color.WHITE);
        lblUser.setHorizontalAlignment(SwingConstants.RIGHT);

        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblRole.setForeground(new Color(209, 250, 229));
        lblRole.setHorizontalAlignment(SwingConstants.RIGHT);

        account.add(lblUser);
        account.add(lblRole);

        header.add(brand, BorderLayout.WEST);
        header.add(account, BorderLayout.EAST);

        return header;
    }

    private JPanel buildWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 8));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(22, 24, 22, 24)
        ));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        textPanel.setOpaque(false);

        JLabel title = new JLabel("Selamat Datang di SIKURSUS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(TEXT);

        JLabel description = new JLabel(
                "Kelola data kursus, peserta, instruktur, transaksi, dan laporan dalam satu aplikasi."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(MUTED);

        textPanel.add(title);
        textPanel.add(description);

        JLabel badge = new JLabel("  DESKTOP APPLICATION  ");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(EMERALD_DARK);
        badge.setOpaque(true);
        badge.setBackground(EMERALD_LIGHT);
        badge.setBorder(new EmptyBorder(9, 10, 9, 10));

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(badge, BorderLayout.EAST);

        return panel;
    }

    private JPanel buildContentPanel() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);

        JLabel sectionTitle = new JLabel("Ringkasan Sistem");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        sectionTitle.setForeground(TEXT);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(sectionTitle, BorderLayout.WEST);

        content.add(top, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 2, 16, 16));
        cards.setOpaque(false);

        cards.add(createInfoCard(
                "MASTER DATA",
                "Pengelolaan Data",
                "Kelola pengguna, peserta, instruktur, kursus, dan kelas.",
                "Gunakan menu Master di bagian atas."
        ));

        cards.add(createInfoCard(
                "TRANSAKSI",
                "Pendaftaran dan Pembayaran",
                "Kelola proses pendaftaran peserta dan pembayaran kursus.",
                "Gunakan menu Transaksi di bagian atas."
        ));

        cards.add(createInfoCard(
                "LAPORAN",
                "Informasi dan Rekapitulasi",
                "Tampilkan laporan peserta, pendaftaran, dan pembayaran.",
                "Gunakan menu Laporan di bagian atas."
        ));

        cards.add(createInfoCard(
                "DATABASE",
                "db_sikursus",
                "Penyimpanan data menggunakan MySQL melalui JDBC.",
                "Arsitektur: View - DAO - JDBC - MySQL."
        ));

        content.add(cards, BorderLayout.CENTER);

        return content;
    }

    private JPanel createInfoCard(
            String category,
            String titleText,
            String descriptionText,
            String footerText) {

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        categoryLabel.setForeground(EMERALD);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(TEXT);

        JTextArea description = new JTextArea(descriptionText);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);
        description.setBackground(CARD);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setFocusable(false);

        JLabel footer = new JLabel(footerText);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(EMERALD_DARK);

        JPanel heading = new JPanel(new GridLayout(2, 1, 0, 7));
        heading.setOpaque(false);
        heading.add(categoryLabel);
        heading.add(title);

        card.add(heading, BorderLayout.NORTH);
        card.add(description, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);

        return card;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(5, 2, 0, 2));

        JLabel left = new JLabel("SIKURSUS Desktop | Final Project PBO");
        left.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        left.setForeground(MUTED);

        JLabel right = new JLabel("Sistem Manajemen Kursus");
        right.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        right.setForeground(MUTED);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);

        return footer;
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));

        JMenu master = createMenu("Master");
        master.add(miUsers);
        master.add(miPeserta);
        master.add(miInstruktur);
        master.add(miKursus);
        master.add(miKelas);

        JMenu trx = createMenu("Transaksi");
        JMenuItem reg = new JMenuItem("Pendaftaran");
        JMenuItem pay = new JMenuItem("Pembayaran");
        trx.add(reg);
        trx.add(pay);

        JMenu rep = createMenu("Laporan");
        JMenuItem rp = new JMenuItem("Peserta - Semua");
        JMenuItem rr = new JMenuItem("Pendaftaran - Semua");
        JMenuItem rt = new JMenuItem("Pembayaran - Semua");
        JMenuItem rtp = new JMenuItem("Pembayaran - Rentang Tanggal");

        rep.add(rp);
        rep.add(rr);
        rep.add(rt);
        rep.add(rtp);

        JMenu out = createMenu("Keluar");
        JMenuItem logout = new JMenuItem("Logout");
        JMenuItem exit = new JMenuItem("Tutup Aplikasi");

        out.add(logout);
        out.addSeparator();
        out.add(exit);

        bar.add(master);
        bar.add(trx);
        bar.add(rep);
        bar.add(out);

        setJMenuBar(bar);

        styleMenuItems(
                miUsers, miPeserta, miInstruktur, miKursus, miKelas,
                reg, pay, rp, rr, rt, rtp, logout, exit
        );

        miUsers.addActionListener(e ->
                WindowManager.open(UserFrame.class, UserFrame::new));

        miPeserta.addActionListener(e ->
                WindowManager.open(PesertaFrame.class, PesertaFrame::new));

        miInstruktur.addActionListener(e ->
                WindowManager.open(InstrukturFrame.class, InstrukturFrame::new));

        miKursus.addActionListener(e ->
                WindowManager.open(KursusFrame.class, KursusFrame::new));

        miKelas.addActionListener(e ->
                WindowManager.open(KelasFrame.class, KelasFrame::new));

        reg.addActionListener(e ->
                WindowManager.open(PendaftaranFrame.class, PendaftaranFrame::new));

        pay.addActionListener(e ->
                WindowManager.open(PembayaranFrame.class, PembayaranFrame::new));

        rp.addActionListener(e -> ReportService.showPeserta(this));
        rr.addActionListener(e -> ReportService.showPendaftaranSemua(this));
        rt.addActionListener(e -> ReportService.showPembayaranSemua(this));
        rtp.addActionListener(e -> showPaymentPeriod());

        logout.addActionListener(e -> logout());
        exit.addActionListener(e -> System.exit(0));

        miPeserta.setIcon(loadIcon("/icons/peserta.png"));
        miInstruktur.setIcon(loadIcon("/icons/instruktur.png"));
        miKursus.setIcon(loadIcon("/icons/kursus.png"));
        miKelas.setIcon(loadIcon("/icons/kelas.png"));
    }

    private JMenu createMenu(String title) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menu.setForeground(EMERALD_DARK);
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);
        return menu;
    }

    private void styleMenuItems(JMenuItem... items) {
        for (JMenuItem item : items) {
            item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            item.setForeground(TEXT);
            item.setBackground(Color.WHITE);
            item.setOpaque(true);
            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    private ImageIcon loadIcon(String path) {
        URL u = getClass().getResource(path);
        return u == null ? null : new ImageIcon(u);
    }

    private void applySession() {
        User u = UserSession.getCurrentUser();

        if (u == null) {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginFrame().setVisible(true);
                }
            });
            return;
        }

        lblUser.setText("Pengguna: " + u.getUsername());
        lblRole.setText("Role: " + u.getRole());

        boolean admin = u.isAdmin();

        miUsers.setEnabled(admin);
        miInstruktur.setEnabled(admin);
        miKursus.setEnabled(admin);
        miKelas.setEnabled(admin);
        miPeserta.setEnabled(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showAppInfo();
            }
        });
    }

    private void showAppInfo() {
        User user = UserSession.getCurrentUser();

        String username = user == null ? "-" : user.getUsername();
        String role = user == null ? "-" : user.getRole();

        String message =
                "SIKURSUS DESKTOP\n"
                + "Sistem Informasi Manajemen Kursus\n\n"
                + "TENTANG APLIKASI:\n"
                + "SIKURSUS merupakan aplikasi desktop untuk\n"
                + "mengelola administrasi kegiatan kursus.\n\n"
                + "FITUR UTAMA:\n"
                + "- Manajemen Data Pengguna\n"
                + "- Manajemen Data Peserta\n"
                + "- Manajemen Data Instruktur\n"
                + "- Manajemen Data Kursus\n"
                + "- Manajemen Data Kelas\n"
                + "- Pendaftaran Peserta\n"
                + "- Pengelolaan Pembayaran\n"
                + "- Laporan dan Rekapitulasi\n\n"
                + "Database: MySQL\n"
                + "Login sebagai: " + username + "\n"
                + "Role: " + role + "\n\n"
                + "Selamat menggunakan SIKURSUS Desktop!";

        JOptionPane.showMessageDialog(
                this,
                message,
                "Tentang Aplikasi SIKURSUS",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showPaymentPeriod() {
        String a = JOptionPane.showInputDialog(
                this,
                "Tanggal awal (yyyy-MM-dd):",
                ViewUtil.today()
        );

        if (a == null) {
            return;
        }

        String b = JOptionPane.showInputDialog(
                this,
                "Tanggal akhir (yyyy-MM-dd):",
                ViewUtil.today()
        );

        if (b == null) {
            return;
        }

        try {
            Date startDate = Date.valueOf(a.trim());
            Date endDate = Date.valueOf(b.trim());

            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tanggal awal tidak boleh melebihi tanggal akhir."
                );
                return;
            }

            ReportService.showPembayaranPeriode(this, startDate, endDate);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Format tanggal harus yyyy-MM-dd."
            );
        }
    }

    private void logout() {
        WindowManager.closeAll();
        UserSession.clear();
        dispose();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
