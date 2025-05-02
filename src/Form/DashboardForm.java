/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Classes.Book;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class DashboardForm extends javax.swing.JFrame    {

    Classes.Func_class func = new Classes.Func_class();
    Border ButtonBorder1 = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
    Border ButtonBorder0 = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(102, 102, 102));

    Classes.Book book = new Classes.Book();
    Classes.Issue_Book issue = new Classes.Issue_Book();

    /**
     * Creates new form DashboardForm
     */
    public DashboardForm() {

        initComponents();
        enhancedButtonHoverEffect();
        this.setLocationRelativeTo(null);
        displayImage(jLabel_Dashboard_Logo, "/Image/UET.jpg");
        displayImage(Online_Button, "/Image/Online_Button.png");
        displayBookCount();
        displayMemberCount();
        updateRecentBooks();
        loadDashboardData();

        Border panelHeaderBorder = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.white);
        jPanelHeader.setBorder(panelHeaderBorder);

        Border panelHeaderBorder_1 = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.white);
        Jpanel_Header_1.setBorder(panelHeaderBorder_1);

        Border panelHeaderBorder_2 = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.white);
        JPanel_Header_2.setBorder(panelHeaderBorder);

        Border panelHeaderBorder_3 = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.white);
        JPanel_Header_3.setBorder(panelHeaderBorder);

        Border panelHeaderBorder_4 = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.white);
        Jpanel_Header_4.setBorder(panelHeaderBorder);

    }

    /**
     * Hiển thị hình ảnh trên JLabel với kích thước tự động điều chỉnh
     *
     * @param label JLabel để hiển thị hình ảnh
     * @param imagePath Đường dẫn tới tệp hình ảnh
     */
    private void displayImage(JLabel label, String imagePath) {
        try {
            ImageIcon imgIcon = new ImageIcon(getClass().getResource(imagePath));
            Image image = imgIcon.getImage().getScaledInstance(
                    label.getWidth(),
                    label.getHeight(),
                    Image.SCALE_SMOOTH
            );
            label.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.err.println("Không thể tải hình ảnh: " + imagePath);
            e.printStackTrace();

        }

    }

    private void loadDashboardData() {
        // Lấy số lượng sách

        // Lấy số lượng sách đang mượn
        int borrowedBooksCount = issue.countBorrowedBooks();
        jLabel_BorrowedBooksCount.setText(String.valueOf(borrowedBooksCount));

        // Lấy danh sách sách mới nhất
    }

    private void displayBookCover(byte[] coverData, JLabel label) {
        try {
            // Kiểm tra xem có dữ liệu ảnh không
            if (coverData != null && coverData.length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(coverData);
                Image image = ImageIO.read(bis);

                // Lấy kích thước JLabel
                int width = label.getWidth();
                int height = label.getHeight();

                // Thay đổi kích thước ảnh để vừa với JLabel
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                // Hiển thị ảnh trong JLabel
                label.setIcon(new ImageIcon(scaledImage));
                label.setText(""); // Xóa text nếu có
            } else {
                // Nếu không có dữ liệu ảnh, hiển thị thông báo
                label.setIcon(null);
                label.setText("Không có ảnh");
            }
        } catch (IOException ex) {
            // Nếu có lỗi, hiển thị thông báo
            label.setIcon(null);
            label.setText("Lỗi hiển thị");
        }
    }

    public void updateRecentBooks() {
        Classes.Book book = new Classes.Book();
        ArrayList<Book> recentBooks = book.getRecentBooks(5); // Lấy 5 sách mới nhất

        // Tạo mảng các JLabel để dễ dàng truy cập
        JLabel[] coverLabels = {jLabel_Cover1, jLabel_Cover2, jLabel_Cover3, jLabel_Cover4, jLabel_Cover5};

        // Hiển thị bìa sách cho từng JLabel
        for (int i = 0; i < coverLabels.length; i++) {
            if (i < recentBooks.size()) {
                Book recentBook = recentBooks.get(i);
                displayBookCover(recentBook.getCover(), coverLabels[i]);
            } else {
                // Nếu không có đủ sách, xóa ảnh và hiển thị thông báo
                coverLabels[i].setIcon(null);
                coverLabels[i].setText("Trống");
            }
        }

    }

    private void displayBookCount() {
        Classes.Book book = new Classes.Book();
        int bookCount = book.countBooks();
        jLabelBookCount.setText(String.valueOf(bookCount));
    }

    private void displayMemberCount() {
        Classes.Member member = new Classes.Member();
        int bookCount = member.countMembers();
        jLabel_CountMember.setText(String.valueOf(bookCount));
    }

    private void enhancedButtonHoverEffect() {
        // Lấy tất cả các thành phần từ panel menu
        Component[] components = jPanel_Menu.getComponents();

        // Màu sắc được tối ưu hóa
        final Color DEFAULT_BG = new Color(65, 65, 65);       // Màu nền mặc định
        final Color HOVER_BG = new Color(80, 80, 80);         // Màu nền khi hover
        final Color BORDER_COLOR = new Color(0, 204, 204);    // Màu viền cyan
        final Color TEXT_COLOR = Color.WHITE;                 // Màu chữ
        final int BORDER_WIDTH = 2;                           // Độ rộng viền bên trái

        for (Component comp : components) {
            if (comp instanceof JButton button) {

                // Thiết lập kiểu dáng mặc định cho nút
                button.setForeground(TEXT_COLOR);
                button.setBackground(DEFAULT_BG);
                button.setBorderPainted(false);
                button.setFocusPainted(false);

                // Thêm hiệu ứng hover
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        // Hiệu ứng khi di chuột vào
                        button.setBackground(HOVER_BG);

                        // Tạo viền bên trái
                        button.setBorder(BorderFactory.createMatteBorder(0, BORDER_WIDTH, 0, 0, BORDER_COLOR));
                        button.setBorderPainted(true);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        // Hiệu ứng khi di chuột ra
                        button.setBackground(DEFAULT_BG);
                        button.setBorderPainted(false);

                        // Xóa viền khi chuột rời khỏi
                        button.setBorder(BorderFactory.createEmptyBorder());
                    }

                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        // Hiệu ứng khi nhấn nút (tối hơn một chút)
                        button.setBackground(new Color(50, 50, 50));
                    }

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent evt) {
                        // Trở về hiệu ứng hover sau khi nhả nút
                        if (button.contains(evt.getPoint())) {
                            button.setBackground(HOVER_BG);
                        } else {
                            button.setBackground(DEFAULT_BG);
                        }
                    }
                });
            }
        }
    }

    private void openAddMemberForm() {
        // Tạo một thể hiện của AddMemberForm
        AddMemberForm AddMemberForm = new AddMemberForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        AddMemberForm.setTitle("Add New Member"); // Đặt tiêu đề cho form
        AddMemberForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        AddMemberForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        AddMemberForm.setVisible(true);

    }

    private void openDeleteMemberForm() {
        // Tạo một thể hiện của AddMemberForm
        DeleteMemberForm DeleteMemberForm = new DeleteMemberForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        DeleteMemberForm.setTitle("Add New Member"); // Đặt tiêu đề cho form
        DeleteMemberForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        DeleteMemberForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        DeleteMemberForm.setVisible(true);

    }

    private void openEditMemberForm() {
        // Tạo một thể hiện của AddMemberForm
        EditMemberForm EditMemberForm = new EditMemberForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        EditMemberForm.setTitle("Edit Member"); // Đặt tiêu đề cho form
        EditMemberForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        EditMemberForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        EditMemberForm.setVisible(true);

    }

    private void openMemberListForm() {
        // Tạo một thể hiện của AddMemberForm
        MemberListForm MemberListForm = new MemberListForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        MemberListForm.setTitle("Edit Member"); // Đặt tiêu đề cho form
        MemberListForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        MemberListForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        MemberListForm.setVisible(true);

    }

    private void openAddBookForm() {
        // Tạo một thể hiện của AddMemberForm
        AddBookForm AddBookForm = new AddBookForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        AddBookForm.setTitle("Add a new book"); // Đặt tiêu đề cho form
        AddBookForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        AddBookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        AddBookForm.setVisible(true);

    }

    private void openDeleteBookForm() {
        // Tạo một thể hiện của AddMemberForm
        DeleteBook DeleteBook = new DeleteBook();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        DeleteBook.setTitle("Delete book"); // Đặt tiêu đề cho form
        DeleteBook.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        DeleteBook.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        DeleteBook.setVisible(true);

    }

    private void openEditBookForm() {
        // Tạo một thể hiện của AddMemberForm
        EditBookForm EditBookForm = new EditBookForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        EditBookForm.setTitle("Edit book"); // Đặt tiêu đề cho form
        EditBookForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        EditBookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        EditBookForm.setVisible(true);

    }

    private void openBookListForm() {
        // Tạo một thể hiện của AddMemberForm
        BookList BookList = new BookList();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        BookList.setTitle("Book List"); // Đặt tiêu đề cho form
        BookList.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        BookList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        BookList.setVisible(true);
    }

    private void openReturnBookForm() {
        // Tạo một thể hiện của AddMemberForm
        ReturnBook ReturnBook = new ReturnBook();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        ReturnBook.setTitle("Return Book"); // Đặt tiêu đề cho form
        ReturnBook.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        ReturnBook.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        ReturnBook.setVisible(true);
    }

    private void openBorrowBookForm() {
        // Tạo một thể hiện của AddMemberForm
        BorrowBookForm BorrowBookForm = new BorrowBookForm();

        // Thiết lập các thuộc tính cho form (tùy chọn)
        BorrowBookForm.setTitle("Borrow Book Form"); // Đặt tiêu đề cho form
        BorrowBookForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        BorrowBookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        BorrowBookForm.setVisible(true);
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
        jPanel_Menu = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabel_Dashboard_Logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Online_Button = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btn_addmember = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Jpanel_Header_1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelBookCount = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        JPanel_Header_2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_CountMember = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        JPanel_Header_3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_BorrowedBooksCount = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        Jpanel_Header_4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_Cover2 = new javax.swing.JLabel();
        jLabel_Cover3 = new javax.swing.JLabel();
        jLabel_Cover4 = new javax.swing.JLabel();
        jLabel_Cover5 = new javax.swing.JLabel();
        jLabel_Cover1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel_Menu.setBackground(new java.awt.Color(102, 102, 102));

        jPanelHeader.setBackground(new java.awt.Color(1, 50, 67));

        jLabel_Dashboard_Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Form/UET.jpg"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Admin");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Online");

        Online_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Form/UET.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeaderLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel_Dashboard_Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Online_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(143, 143, 143))
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel_Dashboard_Logo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Online_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Thành viên");

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Chỉnh sửa tài liệu");
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Thêm tài liệu");
        jButton6.setContentAreaFilled(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btn_addmember.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addmember.setForeground(new java.awt.Color(255, 255, 255));
        btn_addmember.setText("Thêm thành viên");
        btn_addmember.setBorderPainted(false);
        btn_addmember.setContentAreaFilled(false);
        btn_addmember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_addmemberMouseClicked(evt);
            }
        });
        btn_addmember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addmemberActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Danh sách tài liệu");
        jButton9.setContentAreaFilled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tài Liệu");

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Chỉnh sửa thành viên");
        jButton10.setContentAreaFilled(false);
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Xóa Thành Viên");
        jButton11.setContentAreaFilled(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Danh sách thành viên");
        jButton12.setContentAreaFilled(false);
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Mượn - Trả sách");

        jButton13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Mượn sách");
        jButton13.setContentAreaFilled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Trả sách");
        jButton14.setContentAreaFilled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Xóa tài liệu");
        jButton16.setContentAreaFilled(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_MenuLayout = new javax.swing.GroupLayout(jPanel_Menu);
        jPanel_Menu.setLayout(jPanel_MenuLayout);
        jPanel_MenuLayout.setHorizontalGroup(
            jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MenuLayout.createSequentialGroup()
                .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_MenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel_MenuLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_MenuLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btn_addmember, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_MenuLayout.setVerticalGroup(
            jPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MenuLayout.createSequentialGroup()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(btn_addmember, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel9)
                .addGap(24, 24, 24)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 255, 255));
        jPanel3.setForeground(new java.awt.Color(88, 226, 226));

        Jpanel_Header_1.setBackground(new java.awt.Color(153, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Sách");

        javax.swing.GroupLayout Jpanel_Header_1Layout = new javax.swing.GroupLayout(Jpanel_Header_1);
        Jpanel_Header_1.setLayout(Jpanel_Header_1Layout);
        Jpanel_Header_1Layout.setHorizontalGroup(
            Jpanel_Header_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpanel_Header_1Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel5)
                .addContainerGap(153, Short.MAX_VALUE))
        );
        Jpanel_Header_1Layout.setVerticalGroup(
            Jpanel_Header_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpanel_Header_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelBookCount.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabelBookCount.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBookCount.setText("0");
        jLabelBookCount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Jpanel_Header_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jLabelBookCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(Jpanel_Header_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabelBookCount)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 204, 51));

        JPanel_Header_2.setBackground(new java.awt.Color(255, 204, 153));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Thành viên");

        javax.swing.GroupLayout JPanel_Header_2Layout = new javax.swing.GroupLayout(JPanel_Header_2);
        JPanel_Header_2.setLayout(JPanel_Header_2Layout);
        JPanel_Header_2Layout.setHorizontalGroup(
            JPanel_Header_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanel_Header_2Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(96, 96, 96))
        );
        JPanel_Header_2Layout.setVerticalGroup(
            JPanel_Header_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanel_Header_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel_CountMember.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_CountMember.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel_CountMember.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_CountMember.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPanel_Header_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_CountMember)
                .addGap(170, 170, 170))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(JPanel_Header_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel_CountMember, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 71, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 102, 102));

        JPanel_Header_3.setBackground(new java.awt.Color(255, 153, 153));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Sách đang mượn");

        javax.swing.GroupLayout JPanel_Header_3Layout = new javax.swing.GroupLayout(JPanel_Header_3);
        JPanel_Header_3.setLayout(JPanel_Header_3Layout);
        JPanel_Header_3Layout.setHorizontalGroup(
            JPanel_Header_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanel_Header_3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel2)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        JPanel_Header_3Layout.setVerticalGroup(
            JPanel_Header_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanel_Header_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel_BorrowedBooksCount.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_BorrowedBooksCount.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel_BorrowedBooksCount.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_BorrowedBooksCount.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPanel_Header_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_BorrowedBooksCount)
                .addGap(167, 167, 167))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(JPanel_Header_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel_BorrowedBooksCount, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 71, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(204, 255, 153));
        jPanel6.setForeground(new java.awt.Color(204, 255, 153));

        Jpanel_Header_4.setBackground(new java.awt.Color(204, 255, 204));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Lastest update!");

        javax.swing.GroupLayout Jpanel_Header_4Layout = new javax.swing.GroupLayout(Jpanel_Header_4);
        Jpanel_Header_4.setLayout(Jpanel_Header_4Layout);
        Jpanel_Header_4Layout.setHorizontalGroup(
            Jpanel_Header_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpanel_Header_4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addContainerGap(908, Short.MAX_VALUE))
        );
        Jpanel_Header_4Layout.setVerticalGroup(
            Jpanel_Header_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpanel_Header_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel_Cover2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Cover2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Cover2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Cover2.setOpaque(true);

        jLabel_Cover3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Cover3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Cover3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Cover3.setOpaque(true);

        jLabel_Cover4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Cover4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Cover4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Cover4.setOpaque(true);

        jLabel_Cover5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Cover5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Cover5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Cover5.setOpaque(true);

        jLabel_Cover1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Cover1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Cover1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Cover1.setOpaque(true);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Jpanel_Header_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel_Cover1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel_Cover2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jLabel_Cover3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel_Cover4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jLabel_Cover5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(Jpanel_Header_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Cover2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cover3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cover4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cover5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cover1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
            .addComponent(jPanel_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        openReturnBookForm();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        openBorrowBookForm();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        openDeleteMemberForm();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        // TODO add your handling code here:
        openEditMemberForm();
    }//GEN-LAST:event_jButton10MouseClicked

    private void btn_addmemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addmemberActionPerformed

    }//GEN-LAST:event_btn_addmemberActionPerformed

    private void btn_addmemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addmemberMouseClicked
        // TODO add your handling code here:
        // Thêm ActionListener cho nút "Add Book"
        openAddMemberForm();
    }//GEN-LAST:event_btn_addmemberMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        // TODO add your handling code here:
        openMemberListForm();
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        openAddBookForm();
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        openEditBookForm();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        openDeleteBookForm();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        openBookListForm();
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new DashboardForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel_Header_2;
    private javax.swing.JPanel JPanel_Header_3;
    private javax.swing.JPanel Jpanel_Header_1;
    private javax.swing.JPanel Jpanel_Header_4;
    private javax.swing.JLabel Online_Button;
    private javax.swing.JButton btn_addmember;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelBookCount;
    private javax.swing.JLabel jLabel_BorrowedBooksCount;
    private javax.swing.JLabel jLabel_CountMember;
    private javax.swing.JLabel jLabel_Cover1;
    private javax.swing.JLabel jLabel_Cover2;
    private javax.swing.JLabel jLabel_Cover3;
    private javax.swing.JLabel jLabel_Cover4;
    private javax.swing.JLabel jLabel_Cover5;
    private javax.swing.JLabel jLabel_Dashboard_Logo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanel_Menu;
    // End of variables declaration//GEN-END:variables

}
