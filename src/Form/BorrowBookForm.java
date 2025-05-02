/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Classes.Member;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 *
 * @author Acer
 */
public class BorrowBookForm extends javax.swing.JFrame {

    Classes.Func_class func = new Classes.Func_class();
    Classes.Book book = new Classes.Book();
    Classes.Issue_Book issue = new Classes.Issue_Book();

    String imagePath = null;
    private final Color PRIMARY_COLOR = new Color(0, 102, 102);    // Xanh lục đậm (màu header)
    private final Color SECONDARY_COLOR = new Color(153, 204, 255); // Xanh dương nhạt (cho nút "Chọn tệp tin")
    private final Color TEXT_COLOR = Color.WHITE;                  // Màu chữ cho nút chính
    private final Color HOVER_COLOR = new Color(0, 128, 128);      // Màu khi hover nút chính
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    private String currentISBN;
    private int currentMemberID;

    /**
     * Creates new form AddMemberForm
     */
    // Trong constructor của AddMemberForm
    public BorrowBookForm() {

        this.book = new Classes.Book();
        initComponents();
        customizeSearchButton();
        customizeSearchButton2();
        setLocationRelativeTo(null);
        customizeDetailLabels();
        customizeChooseFileButton();
       
    }

    public boolean verif() {
        return true;
    }

    private void clearBookInfo() {
        jTextField_Name.setText("Tên tác phẩm");
        jTextField_Author.setText("Tác giả");
        jTextField_Publisher2.setText("Nhà xuất bản");
        jLabel_Genre.setText("Thể loại");
        lblBookCover.setIcon(null);
        currentISBN = null;
    }

    /**
     * Xóa thông tin thành viên
     */
    private void clearMemberInfo() {
        lblMemberName.setText("Tên thành viên");
        lblMemberPhone.setText("Số điện thoại");
        lblMemberEmail.setText("Email");
        currentMemberID = 0;
    }

    private void clearAll() {
        clearBookInfo();
        clearMemberInfo();
        txtSearch.setText("");
        txtMemberID.setText("");
        txtNote.setText("");

        // Đặt lại ngày mượn là ngày hiện tại
        dateChooserBorrow.setDate(new Date());

        // Đặt lại ngày hẹn trả là 14 ngày sau
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        dateChooserDue.setDate(calendar.getTime());
    }

    private void customizeSearchButton() {
        btnSearchBook.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearchBook.setBackground(PRIMARY_COLOR);
        btnSearchBook.setForeground(TEXT_COLOR);
        btnSearchBook.setFocusPainted(false);
        btnSearchBook.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSearchBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSearchBook.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSearchBook.setBackground(SECONDARY_COLOR);
            }
        });
    }

    private void customizeSearchButton2() {
        btnSearchMember.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearchMember.setBackground(PRIMARY_COLOR);
        btnSearchMember.setForeground(TEXT_COLOR);
        btnSearchMember.setFocusPainted(false);
        btnSearchMember.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSearchMember.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSearchMember.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSearchMember.setBackground(SECONDARY_COLOR);
            }
        });
    }

    private void customizeChooseFileButton() {
        // Tạo nút với viền bo tròn nhẹ
        btnSearchMember = new JButton() {

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
        lblBookCover = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSearchBook = new javax.swing.JButton();
        jlabel_image = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField_Name = new javax.swing.JLabel();
        jTextField_Author = new javax.swing.JLabel();
        jTextField_Publisher2 = new javax.swing.JLabel();
        jLabel_Genre = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblMemberName = new javax.swing.JLabel();
        lblMemberEmail = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        btnSearchMember = new javax.swing.JButton();
        dateChooserBorrow = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        dateChooserDue = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnIssue = new javax.swing.JButton();
        Gender = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jlabel_image1 = new javax.swing.JLabel();
        lblMemberPhone = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel_Available = new javax.swing.JLabel();

        jLabel15.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*Vui lòng nhập đầy đủ");

        lblBookCover.setBackground(new java.awt.Color(255, 255, 255));
        lblBookCover.setForeground(new java.awt.Color(255, 255, 255));
        lblBookCover.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblBookCover.setOpaque(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mượn sách");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(464, 464, 464)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(23, 23, 23))
        );

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel2.setText("Nhập ISBN");

        btnSearchBook.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchBook.setText("Search");
        btnSearchBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchBookActionPerformed(evt);
            }
        });

        jlabel_image.setBackground(new java.awt.Color(255, 255, 255));
        jlabel_image.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jlabel_image.setOpaque(true);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên tác phẩm:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tác giả:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nhà xuất bản:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Thể loại:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Bìa Sách:");

        jTextField_Name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField_Name.setText("Tên tác phẩm");

        jTextField_Author.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField_Author.setText("Tác giả");

        jTextField_Publisher2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField_Publisher2.setText("Nhà xuất bản");

        jLabel_Genre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Genre.setText("Thể loại");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Số điện thoại:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Tên thành viên:");

        lblMemberName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMemberName.setText("Tên thành viên");

        lblMemberEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMemberEmail.setText("Email");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Email:");

        jLabel3.setText("Nhập ID thành viên:");

        txtMemberID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberIDActionPerformed(evt);
            }
        });

        btnSearchMember.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchMember.setText("Search");
        btnSearchMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMemberActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Ngày mượn");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Ngày hẹn trả:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Ghi chú:");

        txtNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoteActionPerformed(evt);
            }
        });

        btnCancel.setText("Hủy bỏ");

        btnIssue.setText("Mượn sách");
        btnIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIssueActionPerformed(evt);
            }
        });

        Gender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Gender.setText("Giới tính");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Giới tính:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Ảnh đại diện");

        jlabel_image1.setBackground(new java.awt.Color(255, 255, 255));
        jlabel_image1.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_image1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jlabel_image1.setOpaque(true);

        lblMemberPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMemberPhone.setText("Số điện thoại");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Có thể cho mượn:");

        jLabel_Available.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel_Available.setForeground(new java.awt.Color(51, 102, 255));
        jLabel_Available.setText("Có hoặc không");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 446, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnIssue)
                                        .addGap(86, 86, 86)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_Available, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13))
                                .addGap(32, 32, 32)
                                .addComponent(dateChooserDue, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(330, 330, 330))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchBook)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchMember)
                        .addGap(178, 178, 178))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(dateChooserBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel6))
                                                    .addGap(4, 4, 4)))
                                            .addComponent(jLabel4))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel_Genre, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jTextField_Publisher2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                                        .addComponent(jTextField_Author, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jlabel_image1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(lblMemberEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblMemberName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblMemberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(339, 339, 339))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnSearchBook)
                    .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnSearchMember))
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_Name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(lblMemberName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Author)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMemberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_Publisher2)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_Genre)
                                    .addComponent(jLabel17)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblMemberEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Gender)))))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel18)
                    .addComponent(jlabel_image1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel12)
                        .addComponent(jLabel_Available)))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(dateChooserDue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIssue)
                    .addComponent(btnCancel))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchBookActionPerformed
        // TODO add your handling code here:
        String isbn = txtSearch.getText().trim();

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập ISBN để tìm kiếm sách",
                    "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentISBN = isbn;

        Classes.Book selectedBook = book.getBookbyISBN(isbn);

        if (selectedBook != null) {
            // Populate fields with data from the selected book
            jTextField_Name.setText(selectedBook.getName());
            jTextField_Author.setText(selectedBook.getAuthor_id());
            jTextField_Publisher2.setText(selectedBook.getPublisher());
            jLabel_Genre.setText(selectedBook.getGenre_id());

            // Hiển thị ảnh
            byte[] image = selectedBook.getCover();
            func.displayImage(125, 80, image, null, jlabel_image);

            // Lấy số lượng sách và số lượng đã mượn
            int quantity = selectedBook.getQuantity();
            int issued = selectedBook.getIssued();
            int available = quantity - issued;

            // Kiểm tra khả năng cho mượn
            boolean canBorrow = issue.checkBookAvailability(currentISBN);

            // Hiển thị số lượng còn lại và trạng thái
            if (canBorrow) {
                jLabel_Available.setText("Còn (" + available + " quyển)");
                jLabel_Available.setForeground(new Color(46, 204, 113)); // Màu xanh lá
                btnIssue.setEnabled(true);
            } else {
                jLabel_Available.setText("Hết sách (0 quyển)");
                jLabel_Available.setForeground(new Color(231, 76, 60)); // Màu đỏ
                btnIssue.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy sách với ISBN đã nhập.",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            clearBookInfo();
        }

    }//GEN-LAST:event_btnSearchBookActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code he

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtMemberIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberIDActionPerformed

    private void btnSearchMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchMemberActionPerformed
        // TODO add your handling code here:

        Classes.Member member = new Classes.Member();

        Member SelectedMember;
        try {
            Integer id = Integer.valueOf(txtMemberID.getText());
            SelectedMember = member.getMemberById(id);

            if (SelectedMember != null) {

                lblMemberName.setText(SelectedMember.getName());
                lblMemberPhone.setText(SelectedMember.getPhone());
                lblMemberEmail.setText(SelectedMember.getEmail());
                Gender.setText(SelectedMember.getGender());

                currentMemberID = id;

                byte[] image = SelectedMember.getPicture();
                func.displayImage(125, 80, image, "", jlabel_image1);

            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy thành viên với ID " + id, "ID không hợp lệ", 3);
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập 1 ID hợp lệ", "ID không hợp lệ", 3);

        }
    }//GEN-LAST:event_btnSearchMemberActionPerformed

    private void txtNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoteActionPerformed

    private void btnIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIssueActionPerformed
        // TODO add your handling code here:
        try {
            // Kiểm tra đã chọn sách chưa
            if (currentISBN == null || currentISBN.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng tìm sách trước khi mượn!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (currentMemberID == 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng tìm thành viên trước khi mượn!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra sách có thể cho mượn không
            if (!issue.checkBookAvailability(currentISBN)) {
                JOptionPane.showMessageDialog(this,
                        "Hiện tại không còn quyển này trong kho",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy ghi chú
            String _note = txtNote.getText();

            // Lấy ngày mượn và ngày hẹn trả
            Date borrowDate = dateChooserBorrow.getDate();
            Date dueDate = dateChooserDue.getDate();

            if (borrowDate == null || dueDate == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ngày mượn và ngày hẹn trả!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra ngày hẹn trả phải sau ngày mượn
            if (dueDate.before(borrowDate)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày hẹn trả phải sau ngày mượn!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Format ngày thành chuỗi
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String _issue_date = dateFormat.format(borrowDate);
            String _return_date = dateFormat.format(dueDate);

            // Thực hiện cho mượn sách
            boolean success = issue.addIssue(currentISBN, currentMemberID, "Mượn sách", _issue_date, _return_date, _note);

            if (success) {
                // Cập nhật lại hiển thị trạng thái sách
                btnSearchBookActionPerformed(null);

                // Xóa thông tin đã nhập
                clearAll();
            }
        } catch (Exception ex) {
            Logger.getLogger(BorrowBookForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnIssueActionPerformed

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
            java.util.logging.Logger.getLogger(BorrowBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BorrowBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BorrowBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BorrowBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BorrowBookForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Gender;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnIssue;
    private javax.swing.JButton btnSearchBook;
    private javax.swing.JButton btnSearchMember;
    private com.toedter.calendar.JDateChooser dateChooserBorrow;
    private com.toedter.calendar.JDateChooser dateChooserDue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Available;
    private javax.swing.JLabel jLabel_Genre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jTextField_Author;
    private javax.swing.JLabel jTextField_Name;
    private javax.swing.JLabel jTextField_Publisher2;
    private javax.swing.JLabel jlabel_image;
    private javax.swing.JLabel jlabel_image1;
    private javax.swing.JLabel lblBookCover;
    private javax.swing.JLabel lblMemberEmail;
    private javax.swing.JLabel lblMemberName;
    private javax.swing.JLabel lblMemberPhone;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
