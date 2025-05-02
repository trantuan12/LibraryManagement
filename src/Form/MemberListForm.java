/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Classes.Member;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class MemberListForm extends javax.swing.JFrame {

    Classes.Func_class func = new Classes.Func_class();
    Classes.Member member = new Classes.Member();

    String imagePath = null;
    private final Color PRIMARY_COLOR = new Color(0, 102, 102);    // Xanh lục đậm (màu header)
    private final Color SECONDARY_COLOR = new Color(153, 204, 255); // Xanh dương nhạt (cho nút "Chọn tệp tin")
    private final Color TEXT_COLOR = Color.WHITE;                  // Màu chữ cho nút chính
    private final Color HOVER_COLOR = new Color(0, 128, 128);      // Màu khi hover nút chính
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    /**
     * Creates new form AddMemberForm
     */
    // Trong constructor của AddMemberForm
    public MemberListForm() {

        this.member = new Classes.Member();
        initComponents();
        customizeSearchButton();
        setLocationRelativeTo(null);
        customizeDetailLabels();
        customizeChooseFileButton();
        populateJTableWithMembers("");
        jLabel_Name.setVisible(false);
        jLabel_Phone.setVisible(false);
        jLabel_Gender.setVisible(false);
        jLabel_Email.setVisible(false);
        customizeTableHeader();
        jTable_Member.setRowHeight(30);
        jTable_Member.setIntercellSpacing(new Dimension(0, 0));
        setTitle("Member List");

    }

    private void customizeTableHeader() {
        jTable_Member.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable_Member.getTableHeader().setBackground(PRIMARY_COLOR);
        jTable_Member.getTableHeader().setForeground(PRIMARY_COLOR);
        jTable_Member.getTableHeader().setOpaque(false);
    }

    private void customizeSearchButton() {
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch.setBackground(PRIMARY_COLOR);
        btnSearch.setForeground(TEXT_COLOR);
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSearch.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(SECONDARY_COLOR);
            }
        });
    }

    private void customizeChooseFileButton() {
        // Tạo nút với viền bo tròn nhẹ
        btnSearch = new JButton() {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Màu nền tùy thuộc vào trạng thái
                if (getModel().isPressed()) {
                    g2.setColor(new Color(220, 230, 240));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(230, 240, 250));
                } else {
                    g2.setColor(new Color(240, 245, 255));
                }

                // Vẽ hình chữ nhật bo tròn
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Vẽ viền nhẹ
                g2.setColor(new Color(200, 210, 230));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Không vẽ viền mặc định
            }
        };

    }

    private void customizeDetailLabels() {
        Font detailFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color detailColor = Color.DARK_GRAY;

        jLabel_Name.setFont(detailFont);
        jLabel_Name.setForeground(detailColor);

        jLabel_Phone.setFont(detailFont);
        jLabel_Phone.setForeground(detailColor);

        jLabel_Email.setFont(detailFont);
        jLabel_Email.setForeground(detailColor);

        jLabel_Gender.setFont(detailFont);
        jLabel_Gender.setForeground(detailColor);

        jLabel_Gender1.setFont(detailFont);
        jLabel_Gender2.setFont(detailFont);
        jLabel_Gender3.setFont(detailFont);
        jLabel_Gender4.setFont(detailFont);
    }

// Phương thức tạo biểu tượng file đơn giản
    private ImageIcon createFileIcon() {
        // Tạo hình ảnh 16x16 pixels
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // Vẽ biểu tượng tệp tin đơn giản
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ trang giấy
        g2.setColor(Color.WHITE);
        g2.fillRect(1, 1, 10, 14);

        // Vẽ góc gấp
        g2.setColor(new Color(230, 230, 230));
        int[] xPoints = {11, 14, 11};
        int[] yPoints = {1, 4, 4};
        g2.fillPolygon(xPoints, yPoints, 3);

        // Vẽ viền
        g2.setColor(new Color(100, 150, 200));
        g2.drawRect(1, 1, 10, 14);
        g2.drawLine(11, 1, 14, 4);
        g2.drawLine(14, 4, 14, 15);
        g2.drawLine(14, 15, 1, 15);
        g2.drawLine(11, 1, 11, 4);
        g2.drawLine(11, 4, 14, 4);

        g2.dispose();

        return new ImageIcon(image);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Member = new javax.swing.JTable();
        jlabel_image = new javax.swing.JLabel();
        jLabel_Name = new javax.swing.JLabel();
        jLabel_Phone = new javax.swing.JLabel();
        jLabel_Gender = new javax.swing.JLabel();
        jLabel_Email = new javax.swing.JLabel();
        jLabel_Gender1 = new javax.swing.JLabel();
        jLabel_Gender2 = new javax.swing.JLabel();
        jLabel_Gender3 = new javax.swing.JLabel();
        jLabel_Gender4 = new javax.swing.JLabel();

        jLabel15.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*Vui lòng nhập đầy đủ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(" Member List");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel2.setText("Nhập tên thành viên");

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jTable_Member.setAutoCreateRowSorter(true);
        jTable_Member.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable_Member.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_MemberMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Member);

        jlabel_image.setBackground(new java.awt.Color(255, 255, 255));
        jlabel_image.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jlabel_image.setOpaque(true);

        jLabel_Name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Name.setText("Họ và tên");

        jLabel_Phone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Phone.setText("Số điện thoại");

        jLabel_Gender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Gender.setText("Giới tính");

        jLabel_Email.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Email.setText("Email");

        jLabel_Gender1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Gender1.setText("Giới tính:");

        jLabel_Gender2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Gender2.setText("Email:");

        jLabel_Gender3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Gender3.setText("Họ và tên:");

        jLabel_Gender4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Gender4.setText("Số điện thoại:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnSearch))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Gender3)
                            .addComponent(jLabel_Gender2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_Gender4)
                                    .addComponent(jLabel_Gender1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel_Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_Phone, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                    .addComponent(jLabel_Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel_Email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnSearch))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Gender3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Gender4)
                            .addComponent(jLabel_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Gender2)
                            .addComponent(jLabel_Email))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Gender1)
                            .addComponent(jLabel_Gender))))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String value = txtSearch.getText();
        String query = "SELECT * FROM `member` WHERE `hovaten` LIKE'%" + value + "%'";
        populateJTableWithMembers(query);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void jTable_MemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_MemberMouseClicked
        // TODO add your handling code here:

        Member SelectedMember = null;

        try {

            Integer rowIndex = jTable_Member.getSelectedRow();
            Integer id = Integer.valueOf(jTable_Member.getModel().getValueAt(rowIndex, 0).toString());
            SelectedMember = member.getMemberById(id);

            if (SelectedMember != null) {

                jLabel_Name.setText(SelectedMember.getName());
                jLabel_Phone.setText(SelectedMember.getPhone());
                jLabel_Email.setText(SelectedMember.getEmail());
                jLabel_Gender.setText(SelectedMember.getGender());
                jLabel_Name.setVisible(true);
                jLabel_Phone.setVisible(true);
                jLabel_Gender.setVisible(true);
                jLabel_Email.setVisible(true);
                jlabel_image.setVisible(true);

                byte[] image = SelectedMember.getPicture();
                func.displayImage(125, 80, image, "", jlabel_image);

            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy thành viên với ID " + id, "ID không hợp lệ", 3);
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập 1 ID hợp lệ", "ID không hợp lệ", 3);

        }

    }//GEN-LAST:event_jTable_MemberMouseClicked

    public void populateJTableWithMembers(String query) {

        // Hiển thị  thông tin thành viên
        ArrayList<Classes.Member> membersList = member.memberlistt(query);

        // Table columns
        String[] colNames = {"ID", "Họ và tên", "Số điện thoại", "Email", "Giới tính"};

        // Rows
        Object[][] rows = new Object[membersList.size()][colNames.length];

        for (int i = 0; i < membersList.size(); i++) {
            rows[i][0] = membersList.get(i).getId();
            rows[i][1] = membersList.get(i).getName();
            rows[i][2] = membersList.get(i).getPhone();
            rows[i][3] = membersList.get(i).getEmail();
            rows[i][4] = membersList.get(i).getGender();
        }
        DefaultTableModel model = new DefaultTableModel(rows, colNames);
        jTable_Member.setModel(model);
    }

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
            java.util.logging.Logger.getLogger(MemberListForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemberListForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemberListForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemberListForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MemberListForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Email;
    private javax.swing.JLabel jLabel_Gender;
    private javax.swing.JLabel jLabel_Gender1;
    private javax.swing.JLabel jLabel_Gender2;
    private javax.swing.JLabel jLabel_Gender3;
    private javax.swing.JLabel jLabel_Gender4;
    private javax.swing.JLabel jLabel_Name;
    private javax.swing.JLabel jLabel_Phone;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Member;
    private javax.swing.JLabel jlabel_image;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
