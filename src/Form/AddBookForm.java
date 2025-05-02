package Form;

import Classes.Book;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Acer
 */
public class AddBookForm extends javax.swing.JFrame {

    Classes.Func_class func = new Classes.Func_class();
    Classes.Member member = new Classes.Member();
    String imagePath = "";

    /**
     * Creates new form AddMemberForm
     */
    // Trong constructor của AddMemberForm
    public AddBookForm() {

        this.member = new Classes.Member();
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Add a new book");
        jlabel_image.setPreferredSize(new Dimension(124, 144)); // Kích thước mong muốn
        jlabel_image.setSize(new Dimension(124, 144)); // Đặt kích thước
        jlabel_image.setMaximumSize(new Dimension(124, 144)); // Kích thước tối đa
        jlabel_image.setMinimumSize(new Dimension(124, 144)); // Kích thước tối thiểu
        customizeaddButton();
        btnSearchISBN = new JButton("Tra cứu");
        btnSearchISBN.addActionListener(e -> fetchBookDataByISBN());

    }
    
     private void openDashBoardForm() {
        // Tạo một thể hiện của AddMemberForm
        DashboardForm DashboardForm = new DashboardForm();
        // Thiết lập các thuộc tính cho form (tùy chọn)
        DashboardForm.setTitle("DashBoard"); // Đặt tiêu đề cho form
        DashboardForm.setLocationRelativeTo(this); // Hiển thị form ở giữa màn hình
        DashboardForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form khi người dùng nhấn nút đóng

        // Hiển thị form
        DashboardForm.setVisible(true);

    }
    private final Color PRIMARY_COLOR = new Color(0, 102, 102);    // Xanh lục đậm (màu header)
    private final Color SECONDARY_COLOR = new Color(153, 204, 255); // Xanh dương nhạt (cho nút "Chọn tệp tin")
    private final Color TEXT_COLOR = Color.WHITE;                  // Màu chữ cho nút chính
    private final Color HOVER_COLOR = new Color(0, 128, 128);      // Màu khi hover nút chính
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    private void customizeaddButton() {
        btnAdd1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd1.setBackground(PRIMARY_COLOR);
        btnAdd1.setForeground(TEXT_COLOR);
        btnAdd1.setFocusPainted(false);
        btnAdd1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAdd1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd1.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd1.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private boolean verifyFields() {
        return !jTextField_ISBN.getText().isEmpty()
                && !jTextField_Name.getText().isEmpty()
                && !jTextField_Author.getText().isEmpty()
                && !jTextField_Publisher2.getText().isEmpty()
                && !jTextField_Genre.getText().isEmpty();
    }

    private void populateFields(JSONObject bookData) {
        try {
            jTextField_Name.setText(bookData.optString("title", ""));
            jTextField_Author.setText(
                    bookData.has("authors") ? bookData.getJSONArray("authors").join(", ").replace("\"", "") : "");
            jTextField_Publisher2.setText(bookData.optString("publisher", ""));
            jTextField_Description.setText(bookData.optString("description", ""));
            jTextField_Genre.setText(
                    bookData.has("categories") ? bookData.getJSONArray("categories").join(", ").replace("\"", "") : "");
            jTextField_Publisher1.setText(bookData.optString("publishedDate", ""));
        } catch (JSONException ex) {
            JOptionPane.showMessageDialog(this, "lỖI TẢI DỮ LIỆU: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchBookDataByISBN() {
        String isbn = jTextField_ISBN.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ISBN.", "CHÚ Ý", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new SwingWorker<Void, Void>() {
            JSONObject bookData;

            @Override
            protected Void doInBackground() throws Exception {
                try {
                    String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(apiUrl))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    JSONObject jsonResponse = new JSONObject(response.body());

                    if (jsonResponse.getInt("totalItems") > 0) {
                        bookData = jsonResponse.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
                    }
                } catch (IOException | InterruptedException | JSONException e) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(AddBookForm.this,
                            "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }

            @Override
            protected void done() {
                if (bookData != null) {
                    populateFields(bookData);
                } else {
                    JOptionPane.showMessageDialog(AddBookForm.this,
                            "không tìm thấy sách với ISBN " + isbn, "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }.execute();
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
        jlabel_image = new javax.swing.JLabel();
        jLabel_Name = new javax.swing.JLabel();
        jLabel_Phone = new javax.swing.JLabel();
        jLabel_Gender = new javax.swing.JLabel();
        jLabel_Email = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Name = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_Author = new javax.swing.JTextField();
        jTextField_Genre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_ID5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Description = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblImagePath = new javax.swing.JLabel();
        btnChooseFile = new javax.swing.JButton();
        btnAdd1 = new javax.swing.JButton();
        jTextField_Price = new javax.swing.JTextField();
        jSpinner_Quantity = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Publisher1 = new javax.swing.JTextField();
        jTextField_Publisher2 = new javax.swing.JTextField();
        btnSearchISBN = new javax.swing.JButton();
        jTextField_ISBN = new javax.swing.JTextField();

        jLabel15.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("*Vui lòng nhập đầy đủ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Add a new book");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(365, 365, 365)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(29, Short.MAX_VALUE))
        );

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nhập ISBN ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên tác phẩm:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tác giả:");

        jTextField_Genre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_GenreActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Số lượng:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nhà xuất bản:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Giá bìa:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Phát hành:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Mô tả:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Bìa sách");

        lblImagePath.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblImagePath.setForeground(new java.awt.Color(0, 51, 255));
        lblImagePath.setText("Đường dẫn...");

        btnChooseFile.setText("Chọn tệp tin");
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        btnAdd1.setText("Add");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Thể loại:");

        jTextField_Publisher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Publisher1ActionPerformed(evt);
            }
        });

        jTextField_Publisher2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Publisher2ActionPerformed(evt);
            }
        });

        btnSearchISBN.setText("Search");
        btnSearchISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchISBNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField_ID5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_ISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSearchISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_Author, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel6))
                                            .addGap(21, 21, 21)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField_Genre, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_Publisher2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jSpinner_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_Publisher1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnChooseFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlabel_image, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lblImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_Description, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(130, 130, 130)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel_Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Phone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(358, 358, 358)
                    .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(592, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_Name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Gender)
                        .addGap(234, 234, 234))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSearchISBN)
                                    .addComponent(jTextField_ISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField_Author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jTextField_Publisher2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField_Genre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField_ID5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jSpinner_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jTextField_Publisher1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(195, 195, 195)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(67, 67, 67)
                                                .addComponent(lblImagePath))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jlabel_image, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnChooseFile))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField_Description, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(168, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(603, Short.MAX_VALUE)
                    .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(52, 52, 52)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        // TODO add your handling code here:

        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Chọn tệp ảnh của bạn");
        filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Image", "png", "jpg", "jpeg");
        filechooser.addChoosableFileFilter(extensionFilter);

        int fileState = filechooser.showOpenDialog(this);
        if (fileState == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = filechooser.getSelectedFile();
                this.imagePath = selectedFile.getAbsolutePath(); // Lưu đường dẫn vào biến thành viên

                // Hiển thị đường dẫn
                lblImagePath.setText(this.imagePath);

                // Hiển thị ảnh
                func.displayImage(125, 80, null, this.imagePath, jlabel_image);
                jlabel_image.setPreferredSize(new Dimension(125, 80));

                // In ra console để debug
                System.out.println("Selected file path: " + this.imagePath);
                System.out.println("File exists: " + selectedFile.exists());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
      if (!verifyFields()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc.", "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        String priceText = jTextField_Price.getText().trim();
        if (!priceText.matches("\\d+(\\.\\d+)?")) { // Kiểm tra định dạng số
            JOptionPane.showMessageDialog(this, "Giá bìa phải là một số hợp lệ.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Double price = Double.parseDouble(priceText);
        Integer quantity = Integer.parseInt(jSpinner_Quantity.getValue().toString());

        // Các trường khác
        String isbn = jTextField_ISBN.getText();
        String name = jTextField_Name.getText();
        String author = jTextField_Author.getText();
        String genre = jTextField_Genre.getText();
        String publisher = jTextField_Publisher2.getText();
        String description = jTextField_Description.getText();
        String date = jTextField_Publisher1.getText();

        Path path = Paths.get(imagePath);
        byte[] img = Files.readAllBytes(path);

        Classes.Book book = new Classes.Book();
        book.addbook(isbn, name, author, genre, quantity, publisher, price, date, description, img);

        JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng hoặc giá bìa không hợp lệ.", "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi đọc tệp ảnh.", "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    } if (!verifyFields()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc.", "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        String priceText = jTextField_Price.getText().trim();
        if (!priceText.matches("\\d+(\\.\\d+)?")) { // Kiểm tra định dạng số
            JOptionPane.showMessageDialog(this, "Giá bìa phải là một số hợp lệ.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Double price = Double.parseDouble(priceText);
        Integer quantity = Integer.parseInt(jSpinner_Quantity.getValue().toString());

        // Các trường khác
        String isbn = jTextField_ISBN.getText();
        String name = jTextField_Name.getText();
        String author = jTextField_Author.getText();
        String genre = jTextField_Genre.getText();
        String publisher = jTextField_Publisher2.getText();
        String description = jTextField_Description.getText();
        String date = jTextField_Publisher1.getText();

        Path path = Paths.get(imagePath);
        byte[] img = Files.readAllBytes(path);

        Classes.Book book = new Classes.Book();
        book.addbook(isbn, name, author, genre, quantity, publisher, price, date, description, img);

     
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng hoặc giá bìa không hợp lệ.", "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi đọc tệp ảnh.", "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }
    
      DashboardForm Dashboard = new DashboardForm();
      Dashboard.dispose();
      openDashBoardForm();
      
      
    
    
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void jTextField_GenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_GenreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_GenreActionPerformed

    private void jTextField_Publisher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Publisher1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_Publisher1ActionPerformed

    private void jTextField_Publisher2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Publisher2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_Publisher2ActionPerformed

    private void btnSearchISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchISBNActionPerformed
        // TODO add your handling code here:
        fetchBookDataByISBN();
    }//GEN-LAST:event_btnSearchISBNActionPerformed

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
            java.util.logging.Logger.getLogger(AddBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new AddBookForm().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnSearchISBN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Email;
    private javax.swing.JLabel jLabel_Gender;
    private javax.swing.JLabel jLabel_Name;
    private javax.swing.JLabel jLabel_Phone;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSpinner_Quantity;
    private javax.swing.JTextField jTextField_Author;
    private javax.swing.JTextField jTextField_Description;
    private javax.swing.JTextField jTextField_Genre;
    private javax.swing.JTextField jTextField_ID5;
    private javax.swing.JTextField jTextField_ISBN;
    private javax.swing.JTextField jTextField_Name;
    private javax.swing.JTextField jTextField_Price;
    private javax.swing.JTextField jTextField_Publisher1;
    private javax.swing.JTextField jTextField_Publisher2;
    private javax.swing.JLabel jlabel_image;
    private javax.swing.JLabel lblImagePath;
    // End of variables declaration//GEN-END:variables

}
