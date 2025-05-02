/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Classes.DB;
import java.sql.Connection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class ReturnBook extends javax.swing.JFrame {

    Classes.Func_class func = new Classes.Func_class();
    Classes.Book book = new Classes.Book();
    Classes.Borrow borrow = new Classes.Borrow();
    Classes.Issue_Book issue = new Classes.Issue_Book();

    String imagePath = null;
    private final Color HEADER_BACKGROUND = new Color(41, 128, 185);  // Xanh dương đậm
    private final Color HEADER_FOREGROUND = Color.WHITE;
    private final Color TABLE_FOREGROUND = new Color(52, 73, 94);     // Màu chữ xám đậm
    private final Color ROW_EVEN = new Color(245, 245, 250);          // Màu dòng chẵn
    private final Color ROW_ODD = Color.WHITE;                        // Màu dòng lẻ
    private final Color SELECTION_BACKGROUND = new Color(179, 229, 252); // Màu khi chọn dòng
    private final Color STATUS_RETURN = new Color(46, 204, 113);      // Xanh lá (Trả sách)
    private final Color STATUS_LOST = new Color(231, 76, 60);         // Đỏ (Mất sách)
    private final Color STATUS_BORROW = new Color(52, 152, 219);      // Xanh dương (Cho mượn)

    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private String currentISBN;
    private int currentMemberID;

    /**
     * Creates new form AddMemberForm
     */
    // Trong constructor của AddMemberForm
    public ReturnBook() {
        this.book = new Classes.Book();
        initComponents();
        populateTableWithIssuedBooks("");
        setLocationRelativeTo(null);
        customizeDetailLabels();
        customizeTableHeader();
        customizeTableCells();
        adjustColumnWidths();
        addTableSelectionListener();
        addTableHoverEffect();
    }

    private void addTableSelectionListener() {
        jTable_Issue_Book.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTable_Issue_Book.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy dữ liệu từ dòng được chọn
                    String isbn = jTable_Issue_Book.getValueAt(selectedRow, 0).toString();
                    int memberId = Integer.parseInt(jTable_Issue_Book.getValueAt(selectedRow, 1).toString());
                    String status = jTable_Issue_Book.getValueAt(selectedRow, 2).toString();
                    String issueDate = jTable_Issue_Book.getValueAt(selectedRow, 3).toString();
                    String returnDate = jTable_Issue_Book.getValueAt(selectedRow, 4).toString();
                    String note = jTable_Issue_Book.getValueAt(selectedRow, 5) != null
                            ? jTable_Issue_Book.getValueAt(selectedRow, 5).toString() : "";

                    // Hiển thị thông tin sách
                    displayBookInfo(isbn);

                    // Hiển thị thông tin thành viên
                    displayMemberInfo(memberId);

                    // Hiển thị ngày và ghi chú
                    displayDatesAndNote(issueDate, returnDate, note);

                    // Lưu thông tin hiện tại
                    currentISBN = isbn;
                    currentMemberID = memberId;

                    // Cập nhật trạng thái nút dựa trên trạng thái sách
                    updateButtonStates(status);
                }
            }
        });
    }

    private void addTableHoverEffect() {
        jTable_Issue_Book.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                int row = jTable_Issue_Book.rowAtPoint(p);

                if (row >= 0) {
                    jTable_Issue_Book.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    jTable_Issue_Book.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void displayBookInfo(String isbn) {
        try {
            Classes.Book selectedBook = book.getBookbyISBN(isbn);
            if (selectedBook != null) {
                jLabel8.setText(selectedBook.getName());
                jLabel18.setText(selectedBook.getAuthor_id());
                byte[] image = selectedBook.getCover();
                func.displayImage(125, 80, image, "", jlabel_image);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin thành viên
     */
    private void displayMemberInfo(int memberId) {
        try {
            Classes.Member member = new Classes.Member();
            Classes.Member selectedMember = member.getMemberById(memberId);
            if (selectedMember != null) {
                lblMemberName.setText(selectedMember.getName());
                lblMemberPhone.setText(selectedMember.getPhone());
                lblMemberEmail.setText(selectedMember.getEmail());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Hiển thị ngày và ghi chú
     */
    private void displayDatesAndNote(String issueDate, String returnDate, String note) {
        try {
            // Hiển thị ngày mượn và ngày hẹn trả
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date issueDateObj = dateFormat.parse(issueDate);
            Date returnDateObj = dateFormat.parse(returnDate);

            dateChooserBorrow.setDate(issueDateObj);
            dateChooserDue.setDate(returnDateObj);

            // Hiển thị ghi chú
            txtNote.setText(note);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Cập nhật trạng thái của các nút dựa trên trạng thái sách
     */
    private void updateButtonStates(String status) {
        if (status.equalsIgnoreCase("Cho mượn") || status.equalsIgnoreCase("Mượn sách")) {
            btnIssue.setEnabled(true);
            btnLost.setEnabled(true);
        } else {
            btnIssue.setEnabled(false);
            btnLost.setEnabled(false);
        }
    }

    private void customizeTableCells() {
        // Thiết lập các thuộc tính cơ bản của bảng
        jTable_Issue_Book.setFont(TABLE_FONT);
        jTable_Issue_Book.setRowHeight(30);
        jTable_Issue_Book.setGridColor(new Color(220, 220, 220));
        jTable_Issue_Book.setSelectionBackground(SELECTION_BACKGROUND);
        jTable_Issue_Book.setSelectionForeground(TABLE_FOREGROUND);
        jTable_Issue_Book.setShowGrid(true);
        jTable_Issue_Book.setFillsViewportHeight(true);
        jTable_Issue_Book.setIntercellSpacing(new Dimension(5, 5));

        // Tạo renderer tùy chỉnh cho các ô
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // Thêm padding cho nội dung
                ((JLabel) comp).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                // Căn giữa nội dung (trừ cột ghi chú)
                if (column != 5) { // Giả sử cột ghi chú là cột 5
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                }

                // Nếu đang được chọn thì sử dụng màu chọn
                if (isSelected) {
                    comp.setBackground(SELECTION_BACKGROUND);
                    comp.setForeground(TABLE_FOREGROUND);
                } // Nếu không được chọn thì sử dụng màu dòng chẵn/lẻ
                else {
                    comp.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    comp.setForeground(TABLE_FOREGROUND);

                    // Tùy chỉnh màu sắc cho trạng thái
                    if (column == 2 && value != null) { // Cột trạng thái
                        String status = value.toString();
                        if (status.equalsIgnoreCase("Trả sách")) {
                            comp.setForeground(STATUS_RETURN);
                        } else if (status.equalsIgnoreCase("Mất sách")) {
                            comp.setForeground(STATUS_LOST);
                        } else if (status.equalsIgnoreCase("Cho mượn")
                                || status.equalsIgnoreCase("Mượn sách")) {
                            comp.setForeground(STATUS_BORROW);
                        }
                    }
                }

                return comp;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        for (int i = 0; i < jTable_Issue_Book.getColumnCount(); i++) {
            jTable_Issue_Book.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Tùy chỉnh border cho JScrollPane
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    }

    private void customizeTableHeader() {
        JTableHeader header = jTable_Issue_Book.getTableHeader();

        // Thiết lập màu nền và màu chữ
        header.setBackground(HEADER_BACKGROUND);
        header.setForeground(Color.black);

        // Thiết lập phông chữ
        header.setFont(HEADER_FONT);

        // Thêm đường viền dưới cho header
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, HEADER_BACKGROUND));

        // Tăng chiều cao của header
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // Không cho phép kéo thả cột
        header.setReorderingAllowed(false);

        // Không cho phép thay đổi kích thước cột bằng chuột
        header.setResizingAllowed(true);

    }

    private void adjustColumnWidths() {
        TableColumnModel columnModel = jTable_Issue_Book.getColumnModel();

        // Thiết lập độ rộng cột theo tỷ lệ phần trăm của bảng
        int tableWidth = jScrollPane1.getWidth();

        // Đặt độ rộng cố định cho các cột
        columnModel.getColumn(0).setPreferredWidth((int) (tableWidth * 0.2)); // Book ISBN
        columnModel.getColumn(1).setPreferredWidth((int) (tableWidth * 0.2));  // Member ID
        columnModel.getColumn(2).setPreferredWidth((int) (tableWidth * 0.2)); // Tình trạng
        columnModel.getColumn(3).setPreferredWidth((int) (tableWidth * 0.2)); // Ngày mượn
        columnModel.getColumn(4).setPreferredWidth((int) (tableWidth * 0.2)); // Ngày hẹn trả
        columnModel.getColumn(5).setPreferredWidth((int) (tableWidth * 0.3));  // Ghi chú
    }

    public void populateTableWithIssuedBooks(String status) {
        ArrayList<Classes.Issue_Book> issBookList = issue.IssueBooklist(status);
        String[] colNames = {"Book ISBN", "Member ID", "Tình trạng", "Ngày mượn", "Ngày hẹn trả", "Ghi chú"};

        Object[][] rows = new Object[issBookList.size()][colNames.length];

        for (int i = 0; i < issBookList.size(); i++) {
            rows[i][0] = issBookList.get(i).getBook_ISBN();
            rows[i][1] = issBookList.get(i).getMember_id();
            rows[i][2] = issBookList.get(i).getStatus();
            rows[i][3] = issBookList.get(i).getIssue_Date();
            rows[i][4] = issBookList.get(i).getReturn_Date();
            rows[i][5] = issBookList.get(i).getNote();

            // ...
        }

        DefaultTableModel model = new DefaultTableModel(rows, colNames);
        jTable_Issue_Book.setModel(model);
        customizeTableHeader();
        customizeTableCells();
        adjustColumnWidths();
        addTableSelectionListener();
        addTableHoverEffect();
    }

    private void customizeDetailLabels() {
        Font detailFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color detailColor = Color.DARK_GRAY;

    }

    private void clearFields() {
        // Xóa thông tin sách
        jLabel8.setText("Tên sách");
        jLabel18.setText("Tên tác giả");
        jlabel_image.setIcon(null);

        // Xóa thông tin thành viên
        lblMemberName.setText("Tên thành viên");
        lblMemberPhone.setText("Số điện thoại");
        lblMemberEmail.setText("Email");

        // Xóa ngày và ghi chú
        dateChooserBorrow.setDate(null);
        dateChooserDue.setDate(null);
        txtNote.setText("");

        // Xóa các biến lưu trữ
        currentISBN = null;
        currentMemberID = 0;
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
        jlabel_image = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblMemberName = new javax.swing.JLabel();
        lblMemberEmail = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnLost = new javax.swing.JButton();
        btnIssue = new javax.swing.JButton();
        lblMemberPhone = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Issue_Book = new javax.swing.JTable();
        lblMemberEmail1 = new javax.swing.JLabel();
        jComboBox_Status = new javax.swing.JComboBox<>();
        dateChooserBorrow = new com.toedter.calendar.JDateChooser();
        dateChooserDue = new com.toedter.calendar.JDateChooser();

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
        jLabel1.setText("Trả sách");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(674, 674, 674)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(24, 24, 24))
        );

        jlabel_image.setBackground(new java.awt.Color(255, 255, 255));
        jlabel_image.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jlabel_image.setOpaque(true);

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

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Ghi chú:");

        txtNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoteActionPerformed(evt);
            }
        });

        btnLost.setText("Mất");
        btnLost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLostActionPerformed(evt);
            }
        });

        btnIssue.setText("Trả sách");
        btnIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIssueActionPerformed(evt);
            }
        });

        lblMemberPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMemberPhone.setText("Số điện thoại");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tên sách");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Tên sách:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Bìa sách:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Tên tác giả");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Tên tác giả:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Ngày mượn");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Ngày hẹn trả:");

        jTable_Issue_Book.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_Issue_Book);

        lblMemberEmail1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMemberEmail1.setText("Phân loại:");

        jComboBox_Status.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox_Status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Trả sách", "Mượn sách", "Mất sách" }));
        jComboBox_Status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_StatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lblMemberName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMemberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMemberEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dateChooserBorrow, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                    .addComponent(dateChooserDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel16)
                            .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMemberEmail1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(btnIssue)
                .addGap(192, 192, 192)
                .addComponent(btnLost, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMemberEmail1)
                    .addComponent(jComboBox_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(lblMemberName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMemberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMemberEmail))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(dateChooserBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(dateChooserDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLost)
                    .addComponent(btnIssue))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIssueActionPerformed
        // TODO add your handling code here:
        Connection conn = null;
        try {
            // Kiểm tra đã chọn sách chưa
            if (currentISBN == null || currentISBN.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn sách cần trả từ danh sách!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra trạng thái hiện tại
            int selectedRow = jTable_Issue_Book.getSelectedRow();
            if (selectedRow != -1) {
                String currentStatus = jTable_Issue_Book.getValueAt(selectedRow, 2).toString();
                if (!currentStatus.equalsIgnoreCase("Mượn sách") && !currentStatus.equalsIgnoreCase("Cho mượn")) {
                    JOptionPane.showMessageDialog(this,
                            "Chỉ có thể trả sách có trạng thái 'Mượn sách' hoặc 'Cho mượn'!",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Lấy ghi chú
            String note = txtNote.getText();

            // Lấy ngày trả
            Date returnDate = dateChooserDue.getDate();
            if (returnDate == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ngày trả!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Định dạng ngày trả
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String returnDateStr = dateFormat.format(returnDate);

            // Tạo kết nối và bắt đầu giao dịch
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            // Cập nhật trạng thái mượn sách thành "Trả sách"
            String updateQuery = "UPDATE `borrow` SET `Status` = 'Trả sách', `note` = ?, `return_date` = ? "
                    + "WHERE `book_isbn` = ? AND `member_id` = ? AND "
                    + "(`Status` = 'Cho mượn' OR `Status` = 'Mượn sách')";

            try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                ps.setString(1, note);
                ps.setString(2, returnDateStr);
                ps.setString(3, currentISBN);
                ps.setInt(4, currentMemberID);

                int result = ps.executeUpdate();

                if (result > 0) {
                    // Cập nhật số lượng sách đã mượn, đảm bảo giá trị không âm
                    String updateBookQuery = "UPDATE `book` SET `issued` = GREATEST(0, `issued` - 1) WHERE `isbn` = ?";
                    try (PreparedStatement psBook = conn.prepareStatement(updateBookQuery)) {
                        psBook.setString(1, currentISBN);
                        psBook.executeUpdate();
                    }

                    // Hoàn tất giao dịch
                    conn.commit();

                    JOptionPane.showMessageDialog(this,
                            "Đã cập nhật trạng thái trả sách thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Làm mới bảng
                    String selectedStatus = jComboBox_Status.getSelectedItem().toString();
                    populateTableWithIssuedBooks(selectedStatus.equals("Tất cả") ? "" : selectedStatus);

                    // Xóa thông tin hiện tại
                    clearFields();
                } else {
                    // Rollback nếu không cập nhật được
                    conn.rollback();

                    JOptionPane.showMessageDialog(this,
                            "Không thể cập nhật trạng thái sách! Có thể sách đã được trả hoặc không tồn tại.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            try {
                // Rollback giao dịch nếu có lỗi
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ReturnBook.class.getName()).log(Level.SEVERE,
                        "Lỗi khi rollback giao dịch", rollbackEx);
            }

            Logger.getLogger(ReturnBook.class.getName()).log(Level.SEVERE,
                    "Lỗi khi cập nhật trạng thái sách: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                // Đóng kết nối
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                Logger.getLogger(ReturnBook.class.getName()).log(Level.SEVERE,
                        "Lỗi khi đóng kết nối", closeEx);
            }
        }
    }//GEN-LAST:event_btnIssueActionPerformed

    private void txtNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoteActionPerformed

    private void btnLostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLostActionPerformed
        // TODO add your handling code here:
        Connection conn = null;
        try {
            // Kiểm tra đã chọn sách chưa
            if (currentISBN == null || currentISBN.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn sách bị mất từ danh sách!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra trạng thái hiện tại
            int selectedRow = jTable_Issue_Book.getSelectedRow();
            if (selectedRow != -1) {
                String currentStatus = jTable_Issue_Book.getValueAt(selectedRow, 2).toString();
                if (!currentStatus.equalsIgnoreCase("Mượn sách") && !currentStatus.equalsIgnoreCase("Cho mượn")) {
                    JOptionPane.showMessageDialog(this,
                            "Chỉ có thể đánh dấu mất sách có trạng thái 'Mượn sách' hoặc 'Cho mượn'!",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Xác nhận với người dùng
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn đánh dấu sách này là bị mất không?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Lấy ghi chú
            String note = txtNote.getText();

            // Lấy ngày báo mất
            Date returnDate = new Date(); // Sử dụng ngày hiện tại

            // Định dạng ngày báo mất
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String returnDateStr = dateFormat.format(returnDate);

            // Tạo kết nối và bắt đầu giao dịch
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            // Cập nhật trạng thái mượn sách thành "Mất sách"
            String updateQuery = "UPDATE `borrow` SET `Status` = 'Mất sách', `note` = ?, `return_date` = ? "
                    + "WHERE `book_isbn` = ? AND `member_id` = ? AND "
                    + "(`Status` = 'Cho mượn' OR `Status` = 'Mượn sách')";

            try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                ps.setString(1, note);
                ps.setString(2, returnDateStr);
                ps.setString(3, currentISBN);
                ps.setInt(4, currentMemberID);

                int result = ps.executeUpdate();

                if (result > 0) {
                    // Cập nhật số lượng sách (giảm cả số lượng tổng và số lượng đã mượn)
                    String updateBookQuery = "UPDATE `book` SET `soluong` = GREATEST(0, `soluong` - 1), "
                            + "`issued` = GREATEST(0, `issued` - 1) WHERE `isbn` = ?";
                    try (PreparedStatement psBook = conn.prepareStatement(updateBookQuery)) {
                        psBook.setString(1, currentISBN);
                        psBook.executeUpdate();
                    }

                    // Hoàn tất giao dịch
                    conn.commit();

                    JOptionPane.showMessageDialog(this,
                            "Đã cập nhật trạng thái mất sách thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Làm mới bảng
                    String selectedStatus = jComboBox_Status.getSelectedItem().toString();
                    populateTableWithIssuedBooks(selectedStatus.equals("Tất cả") ? "" : selectedStatus);

                    // Xóa thông tin hiện tại
                    clearFields();
                } else {
                    // Rollback nếu không cập nhật được
                    conn.rollback();

                    JOptionPane.showMessageDialog(this,
                            "Không thể cập nhật trạng thái sách! Có thể sách đã được trả hoặc không tồn tại.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            try {
                // Rollback giao dịch nếu có lỗi
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ReturnBook.class.getName()).log(Level.SEVERE,
                        "Lỗi khi rollback giao dịch", rollbackEx);
            }

            Logger.getLogger(ReturnBook.class.getName()).log(Level.SEVERE,
                    "Lỗi khi cập nhật trạng thái sách: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                // Đóng kết nối
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                Logger.getLogger(ReturnBook.class.getName()).log(Level.SEVERE,
                        "Lỗi khi đóng kết nối", closeEx);
            }
        }
    }//GEN-LAST:event_btnLostActionPerformed

    private void jComboBox_StatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_StatusActionPerformed
        // TODO add your handling code here:

        String status = jComboBox_Status.getSelectedItem().toString();
        if (status.equals("Tất cả")) {
            status = "";
        }
        populateTableWithIssuedBooks(status);
    }//GEN-LAST:event_jComboBox_StatusActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIssue;
    private javax.swing.JButton btnLost;
    private com.toedter.calendar.JDateChooser dateChooserBorrow;
    private com.toedter.calendar.JDateChooser dateChooserDue;
    private javax.swing.JComboBox<String> jComboBox_Status;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Issue_Book;
    private javax.swing.JLabel jlabel_image;
    private javax.swing.JLabel lblBookCover;
    private javax.swing.JLabel lblMemberEmail;
    private javax.swing.JLabel lblMemberEmail1;
    private javax.swing.JLabel lblMemberName;
    private javax.swing.JLabel lblMemberPhone;
    private javax.swing.JTextField txtNote;
    // End of variables declaration//GEN-END:variables
}
