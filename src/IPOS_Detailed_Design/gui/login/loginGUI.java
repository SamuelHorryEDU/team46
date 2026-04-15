package IPOS_Detailed_Design.gui.login;

import IPOS_Detailed_Design.app.SAtoCA;
import IPOS_Detailed_Design.dao.UserDAO;
import IPOS_Detailed_Design.gui.dashboard.Dashboard;
import IPOS_Detailed_Design.model.User;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class loginGUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(loginGUI.class.getName());

    int mousepX;
    int mousepY;

    public loginGUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        exitButt = new javax.swing.JButton();
        page = new javax.swing.JPanel();
        pc_icon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        login_button = new javax.swing.JButton();
        seperator_u = new javax.swing.JSeparator();
        seperator_p = new javax.swing.JSeparator();
        icon_user = new javax.swing.JLabel();
        icon_pass = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IPOS SA");
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 400));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setToolTipText("");
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        exitButt.setText("X");
        exitButt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtMouseClicked(evt);
            }
        });
        exitButt.addActionListener(this::exitButtActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(exitButt))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(exitButt)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        page.setBackground(new java.awt.Color(255, 204, 102));

        jLabel3.setFont(new java.awt.Font("Colonna MT", 1, 48));
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("IPOS SA");

        javax.swing.GroupLayout pageLayout = new javax.swing.GroupLayout(page);
        page.setLayout(pageLayout);
        pageLayout.setHorizontalGroup(
                pageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pageLayout.createSequentialGroup()
                                .addContainerGap(116, Short.MAX_VALUE)
                                .addGroup(pageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pageLayout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(111, 111, 111))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pageLayout.createSequentialGroup()
                                                .addComponent(pc_icon)
                                                .addGap(134, 134, 134))))
        );
        pageLayout.setVerticalGroup(
                pageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pageLayout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(pc_icon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addContainerGap(66, Short.MAX_VALUE))
        );

        login.setBackground(new java.awt.Color(255, 255, 255));
        login.setPreferredSize(new java.awt.Dimension(400, 400));

        usernameField.setForeground(new java.awt.Color(102, 102, 102));
        usernameField.setText("Username");
        usernameField.setBorder(null);
        usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameFieldFocusGained(evt);
            }
        });
        usernameField.addActionListener(this::usernameFieldActionPerformed);

        passwordField.setText("Password");
        passwordField.setBorder(null);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldFocusGained(evt);
            }
        });
        passwordField.addActionListener(this::passwordFieldActionPerformed);

        login_button.setText("LOGIN");
        login_button.addActionListener(this::login_buttonActionPerformed);

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
                loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginLayout.createSequentialGroup()
                                .addContainerGap(86, Short.MAX_VALUE)
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(icon_user, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(icon_pass, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(login_button)
                                        .addComponent(usernameField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(passwordField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(seperator_u, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(seperator_p, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(72, 72, 72))
        );
        loginLayout.setVerticalGroup(
                loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginLayout.createSequentialGroup()
                                .addContainerGap(136, Short.MAX_VALUE)
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(usernameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(icon_user, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(15, 15, 15)
                                .addComponent(seperator_u, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(icon_pass))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(seperator_p, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(login_button)
                                .addGap(119, 119, 119))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(page, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(login, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                        .addComponent(page, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }
    // </editor-fold>

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void login_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Go directly to the DAO — bypasses the hardcoded AccountService
        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticateUser(username, password);

        if (user != null) {
            this.dispose();
            new Dashboard(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect credentials.");
        }
    }

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void exitButtActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void exitButtMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    private void passwordFieldFocusGained(java.awt.event.FocusEvent evt) {
        if (String.valueOf(passwordField.getPassword()).equals("Password")) {
            passwordField.setText("");
        }
    }

    private void usernameFieldFocusGained(java.awt.event.FocusEvent evt) {
        if (usernameField.getText().equals("Username")) {
            usernameField.setText("");
        }
    }

    private void formMouseClicked(java.awt.event.MouseEvent evt) {
    }

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {
        int cordX = evt.getXOnScreen();
        int cordY = evt.getYOnScreen();
        this.setLocation(cordX - mousepX, cordY - mousepY);
    }

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {
        mousepX = evt.getX();
        mousepY = evt.getY();
    }

    // Variables declaration
    private javax.swing.JButton exitButt;
    private javax.swing.JLabel icon_pass;
    private javax.swing.JLabel icon_user;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel login;
    private javax.swing.JButton login_button;
    private javax.swing.JPanel page;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pc_icon;
    private javax.swing.JSeparator seperator_p;
    private javax.swing.JSeparator seperator_u;
    private javax.swing.JTextField usernameField;
}