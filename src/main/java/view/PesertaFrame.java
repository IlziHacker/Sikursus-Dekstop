
package view;

import dao.PesertaDAO;
import model.Peserta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public PesertaFrame() {
        setTitle("Data Peserta Kursus");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        loadTable();
    }

    private void initComponents() {
        JPanel utama = new JPanel(new BorderLayout(10, 10));
        utama.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Form Peserta"));

        txtNim = new JTextField();
        txtNama = new JTextField();
        txtProdi = new JTextField();
        txtNoHp = new JTextField();
        txtEmail = new JTextField();

        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        groupGender = new ButtonGroup();
        groupGender.add(rbLaki);
        groupGender.add(rbPerempuan);

        JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gender.add(rbLaki);
        gender.add(rbPerempuan);

        form.add(new JLabel("NIM"));
        form.add(txtNim);
        form.add(new JLabel("Nama"));
        form.add(txtNama);
        form.add(new JLabel("Jenis Kelamin"));
        form.add(gender);
        form.add(new JLabel("Program Studi"));
        form.add(txtProdi);
        form.add(new JLabel("Nomor HP"));
        form.add(txtNoHp);
        form.add(new JLabel("Email"));
        form.add(txtEmail);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersih = new JButton("Bersihkan");

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tombol.add(btnSimpan);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnBersih);

        JPanel atas = new JPanel(new BorderLayout(5, 10));
        atas.add(form, BorderLayout.CENTER);
        atas.add(tombol, BorderLayout.SOUTH);

        txtCari = new JTextField(20);
        JButton btnCari = new JButton("Cari");
        JButton btnRefresh = new JButton("Refresh");

        JPanel pencarian = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pencarian.add(new JLabel("Cari NIM/Nama:"));
        pencarian.add(txtCari);
        pencarian.add(btnCari);
        pencarian.add(btnRefresh);

        model = new DefaultTableModel(
            new String[]{"ID", "NIM", "Nama", "Jenis Kelamin",
                         "Prodi", "No HP", "Email"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPeserta = new JTable(model);
        tblPeserta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tblPeserta);

        JPanel bawah = new JPanel(new BorderLayout(5, 10));
        bawah.add(pencarian, BorderLayout.NORTH);
        bawah.add(scroll, BorderLayout.CENTER);

        utama.add(atas, BorderLayout.NORTH);
        utama.add(bawah, BorderLayout.CENTER);

        add(utama);

        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnBersih.addActionListener(e -> clearForm());
        btnCari.addActionListener(e -> cari());
        btnRefresh.addActionListener(e -> loadTable());

        tblPeserta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pilihData();
            }
        });
    }

    private String getJenisKelamin() {
        if (rbLaki.isSelected()) return "Laki-laki";
        if (rbPerempuan.isSelected()) return "Perempuan";
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
                JOptionPane.showMessageDialog(this, "Pilih jenis kelamin!");
                return;
            }

            if (dao.insert(getFormData())) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
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
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!");
                return;
            }

            if (getJenisKelamin().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih jenis kelamin!");
                return;
            }

            if (dao.update(getFormData())) {
                JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
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
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int konfirmasi = JOptionPane.showConfirmDialog(
                this, "Yakin ingin menghapus peserta ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION
            );

            if (konfirmasi == JOptionPane.YES_OPTION) {
                if (dao.delete(selectedId)) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
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
                p.getId(), p.getNim(), p.getNama(),
                p.getJenisKelamin(), p.getProdi(),
                p.getNoHp(), p.getEmail()
            });
        }
    }

    private void pilihData() {
        int row = tblPeserta.getSelectedRow();
        if (row < 0) return;

        selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());

        txtNim.setText(model.getValueAt(row, 1).toString());
        txtNama.setText(model.getValueAt(row, 2).toString());

        String gender = model.getValueAt(row, 3).toString();
        rbLaki.setSelected(gender.equals("Laki-laki"));
        rbPerempuan.setSelected(gender.equals("Perempuan"));

        txtProdi.setText(model.getValueAt(row, 4).toString());
        txtNoHp.setText(model.getValueAt(row, 5).toString());
        txtEmail.setText(model.getValueAt(row, 6).toString());
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
            this, ex.getMessage(), "Kesalahan",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
