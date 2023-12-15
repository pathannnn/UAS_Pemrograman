/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Kasir;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Form_Member extends javax.swing.JFrame {

    /**
     * Creates new form Form_Member
     */
    public Form_Member() {
        initComponents();
        setTitle("FORM DATA MEMBER");
        this.setLocation(200, 100);
        Koneksi_db.openConnection();
        RefreshMember();
        BtnEdit.setEnabled(false);
        setLocationRelativeTo(null);
    }

    private void SimpanMember() {
    String sql = "INSERT INTO data_member VALUES (?, ?, ?, ?, ?)";
    String jkel = "";

    try {
         PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);

        st.setString(1, TxtIDMember.getText());
        st.setString(2, TxtNamaMember.getText());
        if (RbLk.isSelected()) {
            jkel = "Laki-Laki";
        } else if (RbPr.isSelected()) {
            jkel = "Perempuan";
        }
        st.setString(3, jkel);
        st.setString(4, TxtTelpMember.getText());
        st.setString(5, TxtAlamatMember.getText());
        
         int rowsAffected = st.executeUpdate();
         
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        System.out.println(e);
    }
}
     private void EditMember(){
        String sql=
                "update data_member set Kode =?, Nama=?, Jenis_Kelamin=?, Telepon=?, Alamat=? where Kode='"+ TxtIDMember.getText()+"'";
        try{
            PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
            String jkel="";
            
            st.setString(1, TxtIDMember.getText());
            st.setString(2, TxtNamaMember.getText());
            if (RbLk.isSelected() == true) jkel="laki-Laki";
                    else
                if(RbPr.isSelected()== true) jkel="Perempuan";
            st.setString(3, jkel);
            st.setString(4, TxtTelpMember.getText());
            st.setString(5, TxtAlamatMember.getText());
            
             int rowsAffected = st.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void HapusMember(String Kode) {
    String sql = "DELETE FROM data_member WHERE Kode=?";
    try {
        PreparedStatement st = (PreparedStatement) Koneksi_db.conn.prepareStatement(sql);
        st.setString(1, Kode);
        st.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        System.out.println(e);
    }
}
    private void TampilMember(String Kode) {
    try {
        String sql = "SELECT * FROM data_member WHERE Kode=?";
        PreparedStatement st = Koneksi_db.conn.prepareStatement(sql);
        st.setString(1, Kode);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            TxtIDMember.setText(rs.getString("Kode"));
            TxtNamaMember.setText(rs.getString("Nama"));
            if ("Laki-Laki".equals(rs.getString("Jenis_Kelamin"))) {
                RbLk.setSelected(true);
            } else {
                RbPr.setSelected(true);
            }
            TxtTelpMember.setText(rs.getString("Telepon"));
            TxtAlamatMember.setText(rs.getString("Alamat"));
            JOptionPane.showMessageDialog(null, "Data Ditemukan");
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}
    public void RefreshMember() {
    Statement st;
    java.sql.ResultSet rs;
    try {
        st = (Statement) Koneksi_db.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM `data_member` ORDER BY Kode ASC";
        st.execute(sql);
        rs = st.getResultSet();

        String[] header = {"Kode", "Nama", "Jenis_Kelamin", "Telepon", "Alamat"};
        int baris = 0;
        rs.beforeFirst();
        while (rs.next()) {
            baris = rs.getRow();
        }

        Object[][] dtbl = new Object[baris][5];
        rs.beforeFirst();
        int curbaris = 0;
        while (rs.next()) {
            dtbl[curbaris][0] = rs.getString("Kode");
            dtbl[curbaris][1] = rs.getString("Nama");
            dtbl[curbaris][2] = rs.getString("Jenis_Kelamin");
            dtbl[curbaris][3] = rs.getString("Telepon");
            dtbl[curbaris][4] = rs.getString("Alamat");
            curbaris++;
        }

        TblMember.setModel(new DefaultTableModel(dtbl, header));
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
    private void clearData() {
    TxtIDMember.setText("");
    TxtNamaMember.setText("");
    BgJk.clearSelection();
    TxtTelpMember.setText("");
    TxtAlamatMember.setText("");
    TxtIDMember.requestFocus();
    BtnEdit.setEnabled(false);
    TxtIDMember.setEnabled(true);
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

        BgJk = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TxtAlamatMember = new javax.swing.JTextField();
        TxtIDMember = new javax.swing.JTextField();
        TxtNamaMember = new javax.swing.JTextField();
        TxtTelpMember = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblMember = new javax.swing.JTable();
        BtnKeluar = new javax.swing.JButton();
        BtnTambah = new javax.swing.JButton();
        BtnEdit = new javax.swing.JButton();
        BtnCari = new javax.swing.JButton();
        BtnHapus = new javax.swing.JButton();
        RbPr = new javax.swing.JRadioButton();
        RbLk = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(242, 169, 31));

        jLabel1.setBackground(new java.awt.Color(246, 205, 82));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setText("DATA MEMBER TOKO PERDANA");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Alamat           ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText(" Nama              ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Jenis Kelamin ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Telp       ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("ID             ");

        TxtAlamatMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtAlamatMemberActionPerformed(evt);
            }
        });

        TxtIDMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIDMemberActionPerformed(evt);
            }
        });

        TxtNamaMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNamaMemberActionPerformed(evt);
            }
        });

        TxtTelpMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTelpMemberActionPerformed(evt);
            }
        });

        TblMember.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TblMember);

        BtnKeluar.setText("Keluar");
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });

        BtnTambah.setText("Tambah");
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });

        BtnEdit.setText("Edit");
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });

        BtnCari.setText("Cari");
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });

        BtnHapus.setText("Hapus");
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });

        BgJk.add(RbPr);
        RbPr.setText("Perempuan");
        RbPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbPrActionPerformed(evt);
            }
        });

        BgJk.add(RbLk);
        RbLk.setText("Laki-Laki");
        RbLk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbLkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(TxtIDMember, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(RbLk))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(RbPr))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtNamaMember, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtTelpMember, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtAlamatMember, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 33, Short.MAX_VALUE)
                                .addComponent(BtnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnCari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnKeluar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(TxtIDMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(TxtNamaMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RbLk)
                            .addComponent(jLabel5)
                            .addComponent(RbPr))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(TxtTelpMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtAlamatMember, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnHapus)
                            .addComponent(BtnKeluar)
                            .addComponent(BtnCari)
                            .addComponent(BtnEdit)
                            .addComponent(BtnTambah))))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtAlamatMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtAlamatMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtAlamatMemberActionPerformed

    private void TxtIDMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIDMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIDMemberActionPerformed

    private void TxtNamaMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNamaMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNamaMemberActionPerformed

    private void TxtTelpMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTelpMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelpMemberActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        // TODO add your handling code here:
        Keluar();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        // TODO add your handling code here:
        SimpanMember();
        this.requestFocus();
        RefreshMember();
        clearData();
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        // TODO add your handling code here:
        EditMember();
        RefreshMember();
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        // TODO add your handling code here:
        String strInput = JOptionPane.showInputDialog("Silahkan Masukkan ID :");
        TxtIDMember.setEnabled(false);
        TampilMember(strInput);
        BtnEdit.setEnabled(true);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        // TODO add your handling code here:
          String strInput = JOptionPane.showInputDialog("Silahkan Masukan ID :");
        HapusMember(strInput);
        RefreshMember();
        clearData();
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void RbPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbPrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RbPrActionPerformed

    private void RbLkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbLkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RbLkActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Member().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BgJk;
    private javax.swing.JButton BtnCari;
    private javax.swing.JButton BtnEdit;
    private javax.swing.JButton BtnHapus;
    private javax.swing.JButton BtnKeluar;
    private javax.swing.JButton BtnTambah;
    private javax.swing.JRadioButton RbLk;
    private javax.swing.JRadioButton RbPr;
    private javax.swing.JTable TblMember;
    private javax.swing.JTextField TxtAlamatMember;
    private javax.swing.JTextField TxtIDMember;
    private javax.swing.JTextField TxtNamaMember;
    private javax.swing.JTextField TxtTelpMember;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
