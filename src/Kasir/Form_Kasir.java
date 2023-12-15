/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Kasir;

import com.sun.jdi.connect.spi.Connection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Form_Kasir extends javax.swing.JFrame {

    /**
     * Creates new form Form_Transaksi
     */
    public Form_Kasir() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("FORM KASIR");
        this.setLocation(200, 100);
        Koneksi_db.openConnection();
        refreshData();
    }
    
    
   private void SimpanKasir() {
    String sql = "INSERT INTO data_kasir VALUES (?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);

        st.setString(1, TxtNoKasir.getText());
        st.setString(2, TxtID.getText());
        st.setString(3, TxtItemKasir.getText());
        st.setString(4, "Rp" + TxtHargaKasir.getText());
        st.setString(5, TxtQTYKasir.getText());
        st.setString(6, TxtTotalSatuan.getText());
        st.execute();
        
        tampilkanTotalHarga();
        tampilkanTotalQty();
    } catch (SQLException | NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        System.out.println(e);
    }
}



      private void tampilData(String No){
         try {
        String sql = "SELECT * FROM data_kasir WHERE No='"+ No + "'";
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            TxtNoKasir.setText(rs.getString("No"));
            TxtID.setText(rs.getString("Kode_Barang"));      
            TxtItemKasir.setText(rs.getString("Nama_Barang"));
            TxtHargaKasir.setText("Rp" + rs.getString("Harga"));
            TxtQTYKasir.setText(rs.getString("QTY"));
            TxtTotalSatuan.setText(rs.getString("Total_Harga"));
          
            JOptionPane.showMessageDialog(null, "Data Ditemukan");
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
    }
      
private void hitungTotalSatuan() {
    try {
        int harga = Integer.parseInt(TxtHargaKasir.getText().replaceAll("\\s", "").replaceAll(",", ""));
        int qty = Integer.parseInt(TxtQTYKasir.getText());
        int totalSatuan = harga * qty;

        // Display the value in TxtTotalSatuan
        TxtTotalSatuan.setText(String.valueOf(totalSatuan));
    } catch (NumberFormatException ex) {
        // Handle if parsing fails (e.g., input is not valid)
        TxtTotalSatuan.setText("0");
    }
}

    
 private void refreshData(){
    Statement st;
    java.sql.ResultSet rs;
    try {
        st = (Statement) Koneksi_db.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM `data_kasir` ORDER BY No ASC";
        st.execute(sql);
        rs = st.getResultSet();

        String[] header = {"No", "Kode_Barang", "Nama_Barang", "Harga", "QTY", "Total_Harga"};
        int baris = 0;
        rs.beforeFirst();
        while (rs.next()) {
            baris = rs.getRow();
        }

        Object[][] dtbl = new Object[baris][6];
        rs.beforeFirst();
        int curbaris = 0;
        while (rs.next()) {
            dtbl[curbaris][0] = rs.getString("No");
            dtbl[curbaris][1] = rs.getString("Kode_Barang");
            dtbl[curbaris][2] = rs.getString("Nama_Barang");
            dtbl[curbaris][3] = rs.getString("Harga");
            dtbl[curbaris][4] = rs.getString("QTY");
            dtbl[curbaris][5] = rs.getInt("Total_Harga");
            curbaris++;
        }

            TblList.setModel(new DefaultTableModel(dtbl, header));
                tampilkanTotalHarga(); 
        
    } catch (java.sql.SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
 
private double totalHarga = 0; 

public void hitungTotalHarga() {
    try {
        String sql = "SELECT Total_Harga FROM data_kasir";
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        totalHarga = 0; // reset total harga sebelum dihitung ulang

        while (rs.next()) {
            totalHarga += rs.getDouble("Total_Harga");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void tampilkanTotalHarga() {
    hitungTotalHarga(); // hitung total harga
    TxtJumlahHarga.setText(String.format("%,.0f", totalHarga)); // tampilkan total harga di TxtJumlahHarga
}



public int hitungTotalQty() {
    int totalQty = 0;
    try {
        String sql = "SELECT SUM(QTY) AS TotalQty FROM data_kasir";
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            totalQty = rs.getInt("TotalQty");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return totalQty;
}

public void tampilkanTotalQty() {
    int totalQty = hitungTotalQty();
    TxtDibeli.setText(String.valueOf(totalQty));
}

 public void Diskon() {
    tampilkanTotalHarga(); 
    if (CbMember.isSelected()) {
        double diskon = totalHarga * 0.1;
        totalHarga -= diskon;
        TxtDiskon.setText(String.format("%,.0f", diskon));
    } else {
        totalHarga *= 1;
    }

    TxtJumlahHarga.setText(String.format("%,.0f", totalHarga)); 
    TxtJumlahGede.setText(String.format("%,.0f", totalHarga)); 
}

 public void hitungKembalian() {
    try {
        double bayar = Double.parseDouble(TxtBayar.getText().replaceAll("[^\\d.]", ""));
        double diskon = Double.parseDouble(TxtDiskon.getText().replaceAll("[^\\d.]", ""));

        double kembalian = bayar - diskon;
        TxtKembali.setText(String.format("%,.0f", kembalian));
    } catch (NumberFormatException ex) {
        // Handle if parsing fails or other errors
        TxtKembali.setText("0");
    }
}

 private void hapusSemuaData() {
    try {
        String sql = "DELETE FROM data_kasir";
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
        
        // Eksekusi pernyataan DELETE
        int rowsDeleted = st.executeUpdate();

        // Periksa apakah ada data yang dihapus
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Semua Belanjaan Sudah Di Bayar");
        } else {
            JOptionPane.showMessageDialog(null, "Belum Membayar Belanjaan");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

    private void clearData(){
    TxtNoKasir.setText("");
    TxtID.setText("");
    TxtItemKasir.setText("");
    TxtHargaKasir.setText("");
    TxtTotalSatuan.setText("");
    TxtQTYKasir.setText("");
    TxtNoKasir.requestFocus();
    TxtNoKasir.setEnabled(true);
}
    void Keluar() {
    int jawab = JOptionPane.showConfirmDialog(null, "Kamu yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (jawab == 0) this.dispose();
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TxtNoKasir = new javax.swing.JTextField();
        TxtID = new javax.swing.JTextField();
        TxtItemKasir = new javax.swing.JTextField();
        TxtHargaKasir = new javax.swing.JTextField();
        TxtQTYKasir = new javax.swing.JTextField();
        TxtTotalSatuan = new javax.swing.JTextField();
        BtnTambah = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TanggalKasir = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblList = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CbMember = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        TxtKembali = new javax.swing.JTextField();
        TxtJumlahHarga = new javax.swing.JTextField();
        TxtDiskon = new javax.swing.JTextField();
        TxtBayar = new javax.swing.JTextField();
        TxtDibeli = new javax.swing.JLabel();
        BtnKeluar = new javax.swing.JButton();
        BtnRefresh = new javax.swing.JButton();
        TxtJumlahGede = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        BtnHitung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("No");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Kode Barang");

        TxtTotalSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTotalSatuanActionPerformed(evt);
            }
        });

        BtnTambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnTambah.setText("Tambah");
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Rp");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Rp");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(TxtNoKasir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtID, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtItemKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtHargaKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtQTYKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtTotalSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(242, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnTambah)
                        .addGap(266, 266, 266))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtNoKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TxtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtItemKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtHargaKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtQTYKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTotalSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 64, 1136, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1048, 376, 82, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Rp");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 95, -1));
        getContentPane().add(TanggalKasir, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 224, -1));

        TblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TblList);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 170, 890, 152));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Jumlah Harga");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, 76, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Kembalian");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 430, 90, -1));

        CbMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CbMember.setText("Member 10%");
        getContentPane().add(CbMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Bayar");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, 90, -1));
        getContentPane().add(TxtKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 430, 230, -1));

        TxtJumlahHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtJumlahHargaActionPerformed(evt);
            }
        });
        getContentPane().add(TxtJumlahHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, 230, -1));
        getContentPane().add(TxtDiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, 230, -1));
        getContentPane().add(TxtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 400, 230, -1));

        TxtDibeli.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        TxtDibeli.setForeground(new java.awt.Color(255, 0, 0));
        TxtDibeli.setText("0");
        TxtDibeli.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TxtDibeliAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        getContentPane().add(TxtDibeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 70, -1));

        BtnKeluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnKeluar.setText("Keluar");
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 490, -1, -1));

        BtnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnRefresh.setText("Refresh");
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 460, -1, -1));

        TxtJumlahGede.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        getContentPane().add(TxtJumlahGede, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 180, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Barang Yang Di Beli :");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        BtnHitung.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnHitung.setText("Hitung");
        BtnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHitungActionPerformed(evt);
            }
        });
        getContentPane().add(BtnHitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 460, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        // TODO add your handling code here:
        SimpanKasir();
        this.requestFocus();
        refreshData();
        clearData();  
        tampilkanTotalHarga();
        tampilkanTotalQty();
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        // TODO add your handling code here:
        Keluar();
        new Form_Home().show();
        this.dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        // TODO add your handling code here:
        hapusSemuaData();
        refreshData();
    }//GEN-LAST:event_BtnRefreshActionPerformed

    private void TxtTotalSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTotalSatuanActionPerformed
        // TODO add your handling code here:
        hitungTotalSatuan();
    }//GEN-LAST:event_TxtTotalSatuanActionPerformed

    private void TxtDibeliAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TxtDibeliAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDibeliAncestorAdded

    private void TxtJumlahHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtJumlahHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtJumlahHargaActionPerformed

    private void BtnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHitungActionPerformed
        // TODO add your handling code here:
        Diskon();
        hitungKembalian();
    }//GEN-LAST:event_BtnHitungActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnHitung;
    private javax.swing.JButton BtnKeluar;
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JButton BtnTambah;
    private javax.swing.JCheckBox CbMember;
    private com.toedter.calendar.JDateChooser TanggalKasir;
    private javax.swing.JTable TblList;
    private javax.swing.JTextField TxtBayar;
    private javax.swing.JLabel TxtDibeli;
    private javax.swing.JTextField TxtDiskon;
    private javax.swing.JTextField TxtHargaKasir;
    private javax.swing.JTextField TxtID;
    private javax.swing.JTextField TxtItemKasir;
    private javax.swing.JLabel TxtJumlahGede;
    private javax.swing.JTextField TxtJumlahHarga;
    private javax.swing.JTextField TxtKembali;
    private javax.swing.JTextField TxtNoKasir;
    private javax.swing.JTextField TxtQTYKasir;
    private javax.swing.JTextField TxtTotalSatuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
