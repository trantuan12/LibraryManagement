/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Classes.Member;
import java.awt.Color;
import java.sql.SQLException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Acer
 */
public class EditMemberForm extends javax.swing.JFrame {

    Classes.Member member = new Classes.Member();
    Classes.Func_class func = new Classes.Func_class();
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
    public EditMemberForm() {
        this.member = new Classes.Member();
        initComponents();
        setSize(350, 675);
        setLocationRelativeTo(null);

        customizeAddButton();
        customizeChooseFileButton();
        EmptyName.setVisible(false);
        EmptyEmail.setVisible(false);
        EmptyPhone.setVisible(false);
        jlabel_image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

    }


    private void customizeAddButton() {

        // Thiết lập màu nền và chữ
        btnEdit.setBackground(PRIMARY_COLOR);
        btnEdit.setForeground(Color.WHITE);

        // Loại bỏ viền và focus
        btnEdit.setBorderPainted(false);
        btnEdit.setFocusPainted(false);

        // Thiết lập font chữ
        btnEdit.setFont(BUTTON_FONT);

        // Thiết lập kích thước
        btnEdit.setPreferredSize(new Dimension(120, 35));

        // Thêm hiệu ứng hover (tùy chọn)
        btnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEdit.setBackground(PRIMARY_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEdit.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void customizeChooseFileButton() {
        // Tạo nút với viền bo tròn nhẹ
        btnChooseFile = new JButton() {

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

        // Thiết lập các thuộc tính
        btnChooseFile.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnChooseFile.setForeground(new Color(60, 60, 60));
        btnChooseFile.setContentAreaFilled(false);
        btnChooseFile.setFocusPainted(false);
        btnChooseFile.setPreferredSize(new Dimension(120, 35));

        // Thêm biểu tượng file
        btnChooseFile.setIcon(createFileIcon());
        btnChooseFile.setIconTextGap(8);

        // Thêm hiệu ứng con trỏ chuột
        btnChooseFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        jLabel3 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Gender = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnChooseFile = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        EmptyName = new javax.swing.JLabel();
        EmptyPhone = new javax.swing.JLabel();
        EmptyEmail = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtName1 = new javax.swing.JTextField();
        jButton_Search = new javax.swing.JButton();
        jlabel_image = new javax.swing.JLabel();

        jLabel15.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*Vui lòng nhập đầy đủ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Edit Member");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Họ và tên thành viên");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Số điện thoại");

        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Email");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Giới tính");

        Gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        Gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenderActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Ảnh đại diện");

        btnChooseFile.setText("Chọn tệp tin");
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        btnEdit.setText("Chỉnh sửa thành viên");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        EmptyName.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        EmptyName.setForeground(new java.awt.Color(255, 0, 0));
        EmptyName.setText("*Vui lòng nhập đầy đủ");

        EmptyPhone.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        EmptyPhone.setForeground(new java.awt.Color(255, 0, 0));
        EmptyPhone.setText("*Vui lòng nhập đầy đủ");

        EmptyEmail.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        EmptyEmail.setForeground(new java.awt.Color(255, 0, 0));
        EmptyEmail.setText("*Vui lòng nhập đầy đủ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("ID thành viên");

        jButton_Search.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_Search.setText("Search");
        jButton_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchActionPerformed(evt);
            }
        });

        jlabel_image.setBackground(new java.awt.Color(255, 255, 255));
        jlabel_image.setForeground(new java.awt.Color(51, 255, 255));
        jlabel_image.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(EmptyEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Gender, 0, 231, Short.MAX_VALUE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(EmptyPhone, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(EmptyName, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtName1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnChooseFile)
                    .addGap(48, 48, 48))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(53, 53, 53))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(jButton_Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Search))
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EmptyName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EmptyPhone)
                .addGap(4, 4, 4)
                .addComponent(jLabel7)
                .addGap(3, 3, 3)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EmptyEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseFile))
                .addGap(46, 46, 46)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
      
    // Lấy thông tin từ các trường nhập liệu
    String name = txtName1.getText().trim();
    String phone = txtPhone.getText().trim();
    String email = txtEmail.getText().trim();
    String gender = Gender.getSelectedItem().toString();
    byte[] img = null;

    // Kiểm tra các trường bắt buộc
    if (name.isEmpty()) {
        EmptyName.setVisible(true);
        return;
    }
    if (phone.isEmpty()) {
        EmptyPhone.setVisible(true);
        return;
    }
    if (email.isEmpty()) {
        EmptyEmail.setVisible(true);
        return;
    }

    try {
        // Lấy ID thành viên
        Integer id = Integer.valueOf(jTextField_ID.getText());

        // Nếu người dùng chọn ảnh mới
        if (imagePath != null) {
            Path path = Paths.get(imagePath);
            img = Files.readAllBytes(path); // Đọc dữ liệu ảnh từ file
        } else {
            // Nếu không chọn ảnh mới, giữ nguyên ảnh cũ từ cơ sở dữ liệu
            Member selectedMember = member.getMemberById(id);
            if (selectedMember != null) {
                img = selectedMember.getPicture(); // Giữ nguyên ảnh cũ
            }
        }

        // Cập nhật thông tin thành viên trong cơ sở dữ liệu
        member.editmember(id, name, phone, email, gender, img);

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi đọc tệp ảnh.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "ID không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_btnEditActionPerformed

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        // TODO add your handling code here:
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Chọn tệp ảnh của bạn");
        filechooser.setCurrentDirectory(new File("C:\\Program Files"));
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Image", ".png", ".jpg", "jpeg");
        filechooser.addChoosableFileFilter(extensionFilter);

        int fileState = filechooser.showSaveDialog(null);
        if (fileState == JFileChooser.APPROVE_OPTION) {
            String path = filechooser.getSelectedFile().getAbsolutePath();
     
            imagePath = path;
            func.displayImage(125, 80, null, path, jlabel_image);
        }
    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void GenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenderActionPerformed

    private void jButton_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchActionPerformed
        // TODO add your handling code here:\

        //tìm kiếm thành viên theo id
        Member SelectedMember = null;
        try {
            Integer id = Integer.valueOf(jTextField_ID.getText());
            SelectedMember = member.getMemberById(id);

            if (SelectedMember != null) {

                jTextField_ID.setText(String.valueOf(SelectedMember.getId()));
                txtName1.setText(SelectedMember.getName());
                txtPhone.setText(SelectedMember.getPhone());
                txtEmail.setText(SelectedMember.getEmail());
                Gender.setSelectedItem(SelectedMember.getGender());

                byte[] image = SelectedMember.getPicture();
                func.displayImage(125, 80, image, "", jlabel_image);

            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy thành viên với ID " + id, "ID không hợp lệ", 3);
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập 1 ID hợp lệ", "ID không hợp lệ", 3);

        }

    }//GEN-LAST:event_jButton_SearchActionPerformed

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
            java.util.logging.Logger.getLogger(EditMemberForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditMemberForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditMemberForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditMemberForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditMemberForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EmptyEmail;
    private javax.swing.JLabel EmptyName;
    private javax.swing.JLabel EmptyPhone;
    private javax.swing.JComboBox<String> Gender;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton jButton_Search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JLabel jlabel_image;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName1;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
