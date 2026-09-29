package view;
import model.Kursus;
import javax.swing.JOptionPane;

public class FormPendaftaranAwal extends javax.swing.JFrame {
    public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new FormPendaftaranAwal().setVisible(true);
        }
    });
}
    public FormPendaftaranAwal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        txtBiaya = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbKursus = new javax.swing.JComboBox<>();
        btnProses = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHasil = new javax.swing.JTextArea();
        txtNama = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiskon = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 204, 0));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        jLabel1.setText("Form Pendaftaran");
        jLabel1.setMaximumSize(new java.awt.Dimension(100, 34));
        jLabel1.setPreferredSize(new java.awt.Dimension(260, 54));

        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel2.setText("Nama");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel3.setText("Biaya");

        jLabel4.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel4.setText("Jumlah");

        txtJumlah.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        txtBiaya.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtBiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBiayaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel5.setText("Kursus");

        cmbKursus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Java", "Data Science", "UI/UX" }));
        cmbKursus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnProses.setBackground(new java.awt.Color(255, 255, 255));
        btnProses.setText("Proses");
        btnProses.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnProses.setBorderPainted(false);
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReset.setBorderPainted(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        txtHasil.setColumns(20);
        txtHasil.setRows(5);
        txtHasil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(txtHasil);

        txtNama.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel6.setText("DIskon");

        txtDiskon.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiskonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 245, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(314, 314, 314))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(cmbKursus, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(183, 183, 183))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtBiaya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtJumlah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDiskon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(btnProses, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(160, 160, 160)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(179, 179, 179))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbKursus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnProses, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed

    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtBiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBiayaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBiayaActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code he
    }//GEN-LAST:event_txtNamaActionPerformed

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
 
     String nama = txtNama.getText().trim();

    // Validasi nama
    if (nama.isEmpty() || nama.equalsIgnoreCase("Nama")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Nama peserta harus diisi!",
                "Peringatan",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtNama.requestFocus();
        return;
    }

    int biaya;
    int jumlah;
    double diskon;

    // =========================
    // VALIDASI BIAYA
    // =========================
    try {
        biaya = Integer.parseInt(txtBiaya.getText().trim());

    } catch (NumberFormatException e) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Biaya Kursus harus berupa angka!\n"
                + "Contoh: 150000",
                "Biaya Tidak Valid",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtBiaya.requestFocus();
        return;
    }

    // Biaya tidak boleh negatif
    if (biaya < 0) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Biaya Kursus tidak boleh kurang dari 0!",
                "Biaya Tidak Valid",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtBiaya.requestFocus();
        return;
    }

    // =========================
    // VALIDASI JUMLAH
    // =========================
    try {
        jumlah = Integer.parseInt(txtJumlah.getText().trim());

    } catch (NumberFormatException e) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Jumlah harus berupa angka!\n"
                + "Contoh: 2",
                "Jumlah Tidak Valid",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtJumlah.requestFocus();
        return;
    }

    // Jumlah harus lebih dari 0
    if (jumlah <= 0) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Jumlah harus lebih dari 0!",
                "Jumlah Tidak Valid",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtJumlah.requestFocus();
        return;
    }

    // =========================
    // VALIDASI DISKON
    // =========================
    try {
        diskon = Double.parseDouble(txtDiskon.getText().trim());

    } catch (NumberFormatException e) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Diskon harus berupa angka!\n"
                + "Contoh: 10",
                "Diskon Tidak Valid",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtDiskon.requestFocus();
        return;
    }

    // Diskon harus 0 sampai 100
    if (diskon < 0 || diskon > 100) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Diskon harus antara 0 sampai 100%!",
                "Diskon Tidak Valid",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        txtDiskon.requestFocus();
        return;
    }

    // =========================
    // MENGAMBIL KURSUS
    // =========================
    String kursus = cmbKursus.getSelectedItem().toString();

    // =========================
    // PERHITUNGAN
    // =========================

    // Total harga sebelum diskon
    double totalSebelumDiskon = biaya * jumlah;

    // Menghitung nilai diskon
    double nilaiDiskon = totalSebelumDiskon * diskon / 100;

    // Total setelah diskon
    double totalHarga = totalSebelumDiskon - nilaiDiskon;

    // =========================
    // MENAMPILKAN HASIL
    // =========================
    txtHasil.setText(
            "INFORMASI PENDAFTARAN\n"
            + "==============================\n"
            + "Nama Peserta : " + nama + "\n"
            + "Biaya Kursus : Rp "
            + String.format("%,.0f", (double) biaya) + "\n"
            + "Jumlah       : " + jumlah + "\n"
            + "Pilih Kursus : " + kursus + "\n"
            + "Diskon       : "
            + String.format("%.0f", diskon) + "%\n"
            + "Nilai Diskon : Rp "
            + String.format("%,.0f", nilaiDiskon) + "\n"
            + "Total Harga  : Rp "
            + String.format("%,.0f", totalHarga)
    );
    }//GEN-LAST:event_btnProsesActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
   txtNama.setText("");
        txtBiaya.setText("");
        txtJumlah.setText("");

        // Mengembalikan pilihan kursus ke pilihan pertama
        cmbKursus.setSelectedIndex(0);

        // Menghapus hasil
        txtHasil.setText("");

        // Cursor kembali ke Nama
        txtNama.requestFocus();     // TODO add your handling code here:
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiskonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbKursus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBiaya;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextArea txtHasil;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}