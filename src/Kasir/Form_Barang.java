package Kasir;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author DELL
 */
public class Form_Barang extends javax.swing.JFrame {

    /**
     * Creates new form Form_Barang
     */
    public Form_Barang() {
        initComponents();
        setTitle("FORM DATA BARANG");
        this.setLocation(200, 100);
        Koneksi_db.openConnection();
        RefreshBarang();
        BtnEdit.setEnabled(false);
        setLocationRelativeTo(null);
    }
     private void SimpanBarang(){
         String sql = "INSERT INTO data_barang VALUES (?, ?, ?, ?, ?)";
    try {
         PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);

        st.setString(1, TxtIDBarang.getText());
        st.setString(2, TxtItem.getText());
        st.setString(3, TxtHarga.getText());
        st.setString(4, TxtStok.getText());
        String Tanggal = new SimpleDateFormat("yyyy-MM-dd").format(TanggalBarang.getDate());
        st.setString(5,Tanggal);
        st.execute();
        
        int rowsAffected = st.executeUpdate();

        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        System.out.println(e);
    }
    }
     
     private void EditBarang(){
      String sql=
                "update data_barang set ID=?, Item=?, Harga=?, Stok=?, Tanggal=? where ID='"
              +TxtIDBarang.getText()+"'";
        try{
            PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
            String jkel="";
            
            st.setString(1, TxtIDBarang.getText());
            st.setString(2, TxtItem.getText());
            st.setString(3, TxtHarga.getText());
            st.setString(4, TxtStok.getText());
            String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(TanggalBarang.getDate());
            st.setString(5,tanggal);
            st.execute();
            
            int rowsAffected = st.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
    }
     
     private void HapusBarang(String ID){
          String sql = "DELETE FROM data_barang WHERE ID='" +ID+"'";
    try {
        PreparedStatement st = (PreparedStatement) Koneksi_db.conn.prepareStatement(sql);
        st.execute();
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        System.out.println(e);
    }
    }
     
      private void TampilanBarang(String ID){
         try {
        String sql = "SELECT * FROM data_barang WHERE ID='"+ID + "'";
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            TxtIDBarang.setText(rs.getString("ID"));
            TxtItem.setText(rs.getString("Item"));      
            TxtHarga.setText(rs.getString("Harga"));
            TxtStok.setText(rs.getString("Stok"));
            TanggalBarang.setDate(rs.getDate("Tanggal"));
            JOptionPane.showMessageDialog(null, "Data Ditemukan");
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
    }
      
      private void RefreshBarang(){
    Statement st;
    java.sql.ResultSet rs;
    try {
        st = (Statement) Koneksi_db.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM `data_barang` ORDER BY ID ASC";
        st.execute(sql);
        rs = st.getResultSet();

        String[] header = {"ID", "Item", "Harga", "Stok", "Tanggal"};
        int baris = 0;
        rs.beforeFirst();
        while (rs.next()) {
            baris = rs.getRow();
        }

        Object[][] dtbl = new Object[baris][5];
        rs.beforeFirst();
        int curbaris = 0;
        while (rs.next()) {
            dtbl[curbaris][0] = rs.getString("ID");
            dtbl[curbaris][1] = rs.getString("Item");
            dtbl[curbaris][2] = rs.getString("Harga");
            dtbl[curbaris][3] = rs.getString("Stok");
            dtbl[curbaris][4] = rs.getString("Tanggal");
            curbaris++;
        }

            TblBarang.setModel(new DefaultTableModel(dtbl, header));
        
    } catch (java.sql.SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
      private void clearData(){
    TxtIDBarang.setText("");
    TxtItem.setText("");
    TxtHarga.setText("");
    TxtStok.setText("");
    TxtIDBarang.requestFocus();
    BtnEdit.setEnabled(false);
    TxtIDBarang.setEnabled(true);
}
    
    
void Keluar() {
    int jawab = JOptionPane.showConfirmDialog(null, "Kamu yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    
    if (jawab == JOptionPane.YES_OPTION) {
        new Form_Home().show();
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(null, "Pilihan dibatalkan");
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TxtIDBarang = new javax.swing.JTextField();
        TxtItem = new javax.swing.JTextField();
        TxtHarga = new javax.swing.JTextField();
        TxtStok = new javax.swing.JTextField();
        BtnTambah = new javax.swing.JButton();
        BtnEdit = new javax.swing.JButton();
        BtnCari = new javax.swing.JButton();
        BtnHapus = new javax.swing.JButton();
        BtnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblBarang = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        TanggalBarang = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(246, 205, 82));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setText("DATA BARANG TOKO PERDANA");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("ID          :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Item     :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Harga   :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Stok     :");

        BtnTambah.setFont(new java.awt.Font("Segoe UI Semibold", 2, 12)); // NOI18N
        BtnTambah.setText("Tambah");
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });

        BtnEdit.setFont(new java.awt.Font("Segoe UI Semibold", 2, 12)); // NOI18N
        BtnEdit.setText("Edit");
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });

        BtnCari.setFont(new java.awt.Font("Segoe UI Semibold", 2, 12)); // NOI18N
        BtnCari.setText("Cari");
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });

        BtnHapus.setFont(new java.awt.Font("Segoe UI Semibold", 2, 12)); // NOI18N
        BtnHapus.setText("Hapus");
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });

        BtnKeluar.setFont(new java.awt.Font("Segoe UI Semibold", 2, 12)); // NOI18N
        BtnKeluar.setText("Keluar");
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });

        TblBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TblBarang);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tanggal :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(TxtIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(TxtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(TxtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(TxtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(BtnTambah)
                        .addGap(12, 12, 12)
                        .addComponent(BtnEdit)
                        .addGap(12, 12, 12)
                        .addComponent(BtnCari)
                        .addGap(12, 12, 12)
                        .addComponent(BtnHapus)
                        .addGap(12, 12, 12)
                        .addComponent(BtnKeluar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(TanggalBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(TxtIDBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(TxtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(TxtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(TxtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnTambah)
                            .addComponent(BtnEdit)
                            .addComponent(BtnCari)
                            .addComponent(BtnHapus)
                            .addComponent(BtnKeluar))))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(TanggalBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        // TODO add your handling code here:
        SimpanBarang();
        this.requestFocus();
        RefreshBarang();
        clearData();
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        // TODO add your handling code here:
        EditBarang();
        RefreshBarang();
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        // TODO add your handling code here:
        String strInput = JOptionPane.showInputDialog("Silahkan Masukkan ID :");
        TxtIDBarang.setEnabled(false);
        TampilanBarang(strInput);
        BtnEdit.setEnabled(true);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        // TODO add your handling code here:
        String strInput = JOptionPane.showInputDialog("Silahkan Masukan ID :");
        HapusBarang(strInput);
        RefreshBarang();
        clearData();
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        // TODO add your handling code here:
        Keluar();
    }//GEN-LAST:event_BtnKeluarActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCari;
    private javax.swing.JButton BtnEdit;
    private javax.swing.JButton BtnHapus;
    private javax.swing.JButton BtnKeluar;
    private javax.swing.JButton BtnTambah;
    private com.toedter.calendar.JDateChooser TanggalBarang;
    private javax.swing.JTable TblBarang;
    private javax.swing.JTextField TxtHarga;
    private javax.swing.JTextField TxtIDBarang;
    private javax.swing.JTextField TxtItem;
    private javax.swing.JTextField TxtStok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
