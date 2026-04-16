/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package IPOS_Detailed_Design.gui.dashboard;

import IPOS_Detailed_Design.model.User;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.logging.Logger;

/**
 *
 * @author lapis
 */
public class Dashboard extends JFrame {

    private static final Logger logger = Logger.getLogger(Dashboard.class.getName());

    int mousepX;
    int mousepY;
    private DashboardController controller;
    private User currentUser;

    public Dashboard() {
        this(null);
    }

    public Dashboard(User user) {
        this.currentUser = user;
        initComponents();
        this.controller = new DashboardController(this, currentUser);
        this.controller.init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jSeparator6 = new JSeparator();
        jLabel8 = new JLabel();
        TaskBar = new JPanel();
        exitButton = new JButton();
        dashboardTabs = new JTabbedPane();
        dashboardTab = new JPanel();
        warningLabel = new JLabel();
        jSeparator1 = new JSeparator();
        rolePanel = new JPanel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        roleTXT = new JLabel();
        nameTXT = new JLabel();
        pendingOrdersPan = new JPanel();
        jLabel5 = new JLabel();
        pendingOrders = new JLabel();
        pendingApplicationsPan = new JPanel();
        jLabel11 = new JLabel();
        pendingApplications = new JLabel();
        TotalUsersPan = new JPanel();
        jLabel7 = new JLabel();
        totalUsers = new JLabel();
        jPanel6 = new JPanel();
        jLabel6 = new JLabel();
        jButton3 = new JButton();
        catManagementTab = new JPanel();
        addItemTab = new JTabbedPane();
        jPanel7 = new JPanel();
        searchField = new JTextField();
        jScrollPane2 = new JScrollPane();
        CatalogueTable = new JTable();
        rmvSelectedButton = new JButton();
        addItem = new JPanel();
        packageTypeField = new JTextField();
        costField = new JTextField();
        availabilityField = new JTextField();
        unitField = new JTextField();
        packageField = new JTextField();
        addItemButton = new JButton();
        addItemLabel = new Label();
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        catLabel = new JLabel();

        // --- Orders section (teammate's full implementation) ---
        Orders = new JPanel();
        jLabel14 = new JLabel();
        ordersTab = new JTabbedPane();
        ordersPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        ordersTable = new JTable();
        orderSearchField = new JTextField();
        hideCompletedButton = new JButton();
        unHideAllButton = new JButton();
        jComboBox1 = new JComboBox<>();
        jLabel15 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jSeparator2 = new JSeparator();
        jSeparator3 = new JSeparator();
        // --- End Orders section ---

        reportsTab = new JPanel();
        jPanel1 = new JPanel();
        jLabel12 = new JLabel();
        scrollPane = new JScrollPane();
        reportTextPane = new JTextPane();
        systemTurnoverReport = new JToggleButton();
        individualMerchantActvityButton = new JToggleButton();
        debtorReminders = new JToggleButton();
        merchantOrdersSummary = new JToggleButton();
        stockTurnoverReport = new JToggleButton();
        generatePDFButton = new JButton();
        Applications = new JPanel();
        jLabel13 = new JLabel();
        jScrollPane4 = new JScrollPane();
        jTable1 = new JTable();
        jToggleButton5 = new JToggleButton();
        jToggleButton6 = new JToggleButton();
        userTab = new JPanel();
        UserLabel = new JLabel();
        userTabs = new JTabbedPane();
        Users = new JPanel();
        jTextField16 = new JTextField();
        jScrollPane6 = new JScrollPane();
        jTable3 = new JTable();
        jLabel16 = new JLabel();
        jTextField17 = new JTextField();
        jTextField18 = new JTextField();
        jButton14 = new JButton();
        jButton15 = new JButton();
        jComboBox2 = new JComboBox<>();
        jLabel10 = new JLabel();
        Merchants = new JPanel();
        jScrollPane3 = new JScrollPane();
        CatalogueTable1 = new JTable();
        jButton16 = new JButton();
        jLabel9 = new JLabel();
        addMerchants = new JPanel();
        addMerchantLabel = new JLabel();
        merchant_usernameField = new JTextField();
        merchant_passwordField = new JTextField();
        accHolderNameField = new JTextField();
        contactNameField = new JTextField();
        addressField = new JTextField();
        creditLimitField = new JTextField();
        discountPlanBox = new JComboBox<>();
        discountRateField = new JTextField();
        addMerchantButton = new JToggleButton();
        jLabel1 = new JLabel();
        jLabel4 = new JLabel();
        Dashboard = new JPanel();
        homeB = new JButton();
        usersB = new JButton();
        catB = new JButton();
        ordersB = new JButton();
        reportsB = new JButton();
        appB = new JButton();

        // --- New components (from updated GUI) ---
        jPanel2 = new JPanel();
        jLabel17 = new JLabel();
        jComboBox3 = new JComboBox<>();
        jScrollPane7 = new JScrollPane();
        jTable2 = new JTable();
        jScrollPane8 = new JScrollPane();
        jTable4 = new JTable();
        jLabel19 = new JLabel();
        jLabel20 = new JLabel();
        jLabel21 = new JLabel();
        jLabel22 = new JLabel();
        jTextField3 = new JTextField();
        jTextField5 = new JTextField();
        jSeparator5 = new JSeparator();
        jComboBox4 = new JComboBox<>();
        jButton8 = new JButton();
        jTabbedPane1 = new JTabbedPane();
        jPanel3 = new JPanel();
        jLabel18 = new JLabel();
        jTabbedPane2 = new JTabbedPane();
        jScrollPane9 = new JScrollPane();
        jTable5 = new JTable();
        jButton7 = new JButton();
        jButton5 = new JButton();
        jButton6 = new JButton();
        jSeparator4 = new JSeparator();
        jComboBox5 = new JComboBox<>();
        jTextField19 = new JTextField();
        jTextField20 = new JTextField();
        jLabel23 = new JLabel();

        jLabel8.setText("jLabel8");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setMinimumSize(new Dimension(1200, 600));
        setUndecorated(true);
        setResizable(false);

        // ===================== TASKBAR =====================
        TaskBar.setBackground(new Color(0, 0, 0));
        TaskBar.setMaximumSize(new Dimension(1200, 1200));
        TaskBar.setMinimumSize(new Dimension(960, 540));
        TaskBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                TaskBarMouseDragged(evt);
            }
        });
        TaskBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                TaskBarMousePressed(evt);
            }
        });

        exitButton.setText("X");
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });
        exitButton.addActionListener(this::exitButtonActionPerformed);

        GroupLayout TaskBarLayout = new GroupLayout(TaskBar);
        TaskBar.setLayout(TaskBarLayout);
        TaskBarLayout.setHorizontalGroup(
                TaskBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(TaskBarLayout.createSequentialGroup()
                                .addGap(1154, 1154, 1154)
                                .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        );
        TaskBarLayout.setVerticalGroup(
                TaskBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(TaskBarLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(exitButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // ===================== DASHBOARD TABS =====================
        dashboardTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        dashboardTabs.setMaximumSize(new Dimension(1000, 600));
        dashboardTabs.setMinimumSize(new Dimension(1000, 600));
        dashboardTabs.setPreferredSize(new Dimension(1000, 600));

        // ===================== DASHBOARD TAB =====================
        dashboardTab.setBackground(new Color(255, 255, 255));
        dashboardTab.setMaximumSize(new Dimension(1000, 600));
        dashboardTab.setMinimumSize(new Dimension(1000, 600));
        dashboardTab.setPreferredSize(new Dimension(1000, 600));

        warningLabel.setFont(new Font("Segoe UI", 1, 48));
        warningLabel.setText("DASHBOARD");

        jLabel2.setFont(new Font("sansserif", 1, 12));
        jLabel2.setText("ROLE");

        jLabel3.setFont(new Font("sansserif", 1, 12));
        jLabel3.setText("Name");

        roleTXT.setText("<ROLE>");
        nameTXT.setText("<NAME>");

        GroupLayout rolePanelLayout = new GroupLayout(rolePanel);
        rolePanel.setLayout(rolePanelLayout);
        rolePanelLayout.setHorizontalGroup(
                rolePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rolePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(rolePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(rolePanelLayout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(roleTXT))
                                        .addGroup(rolePanelLayout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nameTXT)))
                                .addContainerGap(128, Short.MAX_VALUE))
        );
        rolePanelLayout.setVerticalGroup(
                rolePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rolePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(rolePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(roleTXT))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(rolePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nameTXT))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new Font("Segoe UI", 1, 12));
        jLabel5.setText("Pending Orders");

        pendingOrders.setFont(new Font("Segoe UI", 0, 36));
        pendingOrders.setHorizontalAlignment(SwingConstants.RIGHT);
        pendingOrders.setText("0");

        GroupLayout pendingOrdersPanLayout = new GroupLayout(pendingOrdersPan);
        pendingOrdersPan.setLayout(pendingOrdersPanLayout);
        pendingOrdersPanLayout.setHorizontalGroup(
                pendingOrdersPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pendingOrdersPanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pendingOrdersPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pendingOrdersPanLayout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(0, 106, Short.MAX_VALUE))
                                        .addComponent(pendingOrders, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pendingOrdersPanLayout.setVerticalGroup(
                pendingOrdersPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pendingOrdersPanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pendingOrders, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jLabel11.setFont(new Font("Segoe UI", 1, 12));
        jLabel11.setText("Pending Applications");

        pendingApplications.setFont(new Font("Segoe UI", 0, 36));
        pendingApplications.setHorizontalAlignment(SwingConstants.RIGHT);
        pendingApplications.setText("0");

        GroupLayout pendingApplicationsPanLayout = new GroupLayout(pendingApplicationsPan);
        pendingApplicationsPan.setLayout(pendingApplicationsPanLayout);
        pendingApplicationsPanLayout.setHorizontalGroup(
                pendingApplicationsPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pendingApplicationsPanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pendingApplicationsPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pendingApplicationsPanLayout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(0, 75, Short.MAX_VALUE))
                                        .addComponent(pendingApplications, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pendingApplicationsPanLayout.setVerticalGroup(
                pendingApplicationsPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pendingApplicationsPanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel11)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pendingApplications, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jLabel7.setFont(new Font("Segoe UI", 1, 12));
        jLabel7.setText("Total Users");

        totalUsers.setFont(new Font("Segoe UI", 0, 36));
        totalUsers.setHorizontalAlignment(SwingConstants.RIGHT);
        totalUsers.setText("0");

        GroupLayout TotalUsersPanLayout = new GroupLayout(TotalUsersPan);
        TotalUsersPan.setLayout(TotalUsersPanLayout);
        TotalUsersPanLayout.setHorizontalGroup(
                TotalUsersPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(TotalUsersPanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(TotalUsersPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(TotalUsersPanLayout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(0, 130, Short.MAX_VALUE))
                                        .addComponent(totalUsers, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        TotalUsersPanLayout.setVerticalGroup(
                TotalUsersPanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(TotalUsersPanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalUsers, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jLabel6.setFont(new Font("sansserif", 3, 12));
        jLabel6.setText("<INSERT WARNING>");

        jButton3.setText("Refresh Data");

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addContainerGap(218, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addContainerGap(8, Short.MAX_VALUE))
        );

        GroupLayout dashboardTabLayout = new GroupLayout(dashboardTab);
        dashboardTab.setLayout(dashboardTabLayout);
        dashboardTabLayout.setHorizontalGroup(
                dashboardTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(dashboardTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(dashboardTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(dashboardTabLayout.createSequentialGroup()
                                                .addComponent(jSeparator1)
                                                .addContainerGap())
                                        .addGroup(dashboardTabLayout.createSequentialGroup()
                                                .addComponent(warningLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 527, Short.MAX_VALUE)
                                                .addComponent(rolePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(14, 14, 14))
                                        .addGroup(dashboardTabLayout.createSequentialGroup()
                                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton3)
                                                .addGap(16, 16, 16))))
                        .addGroup(dashboardTabLayout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(pendingOrdersPan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TotalUsersPan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pendingApplicationsPan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        dashboardTabLayout.setVerticalGroup(
                dashboardTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(dashboardTabLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(dashboardTabLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(warningLabel)
                                        .addComponent(rolePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(dashboardTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton3))
                                .addGap(137, 137, 137)
                                .addGroup(dashboardTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pendingOrdersPan, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pendingApplicationsPan, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(TotalUsersPan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(273, Short.MAX_VALUE))
        );

        dashboardTabs.addTab("dashboard", dashboardTab);

        // ===================== CATALOGUE TAB =====================
        catManagementTab.setBackground(new Color(255, 255, 255));
        catManagementTab.setMaximumSize(new Dimension(1000, 600));
        catManagementTab.setMinimumSize(new Dimension(1000, 600));
        catManagementTab.setPreferredSize(new Dimension(1000, 600));

        addItemTab.setBackground(new Color(255, 255, 255));

        searchField.setText("Search..");
        searchField.addActionListener(this::searchFieldActionPerformed);

        CatalogueTable.setAutoCreateRowSorter(true);
        CatalogueTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null}
                },
                new String[]{"Item ID", "Description", "Package Type", "Unit", "Units In Pack", "Package Cost,  £", "Availability, packs", "Stock Limit, packs"}
        ) {
            Class[] types = new Class[]{
                    Integer.class, String.class, Integer.class,
                    Integer.class, Integer.class, String.class,
                    Integer.class, Integer.class
            };
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
        });
        jScrollPane2.setViewportView(CatalogueTable);

        rmvSelectedButton.setText("Remove Selected Item");

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                                .addGap(0, 882, Short.MAX_VALUE)
                                                .addComponent(rmvSelectedButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane2)))
                                .addContainerGap())
                        .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 972, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap(36, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rmvSelectedButton)
                                .addGap(27, 27, 27))
                        .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(524, Short.MAX_VALUE)))
        );

        addItemTab.addTab("Catalogue", jPanel7);

        addItem.setBackground(new Color(255, 255, 255));

        packageTypeField.setText("Units in Pack");
        packageTypeField.addActionListener(this::packageTypeFieldActionPerformed);
        costField.setText("Cost");
        costField.addActionListener(this::costFieldActionPerformed);
        availabilityField.setText("Availability");
        availabilityField.addActionListener(this::availabilityFieldActionPerformed);
        unitField.setText("Unit");
        unitField.addActionListener(this::unitFieldActionPerformed);
        packageField.setText("Package Type");
        addItemButton.setText("Add Item");
        addItemLabel.setFont(new Font("Dialog", 1, 12));
        addItemLabel.setText("Add Item");
        jTextField1.setHorizontalAlignment(JTextField.LEFT);
        jTextField1.setText("Description");
        jTextField2.setText("Stock Limit, pack");
        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        GroupLayout addItemLayout = new GroupLayout(addItem);
        addItem.setLayout(addItemLayout);
        addItemLayout.setHorizontalGroup(
                addItemLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(addItemLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(addItemLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField1)
                                        .addComponent(addItemButton, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addItemLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(packageField, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                        .addComponent(unitField)
                                        .addComponent(packageTypeField)
                                        .addComponent(costField)
                                        .addComponent(availabilityField)
                                        .addComponent(jTextField2))
                                .addContainerGap(620, Short.MAX_VALUE))
        );
        addItemLayout.setVerticalGroup(
                addItemLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(addItemLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(addItemLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(packageField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(unitField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(packageTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(costField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(availabilityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(addItemButton)
                                .addContainerGap(246, Short.MAX_VALUE))
        );

        addItemTab.addTab("Add Item", addItem);

        catLabel.setFont(new Font("Segoe UI", 1, 48));
        catLabel.setText("CATALOGUE MANAGEMENT");

        GroupLayout catManagementTabLayout = new GroupLayout(catManagementTab);
        catManagementTab.setLayout(catManagementTabLayout);
        catManagementTabLayout.setHorizontalGroup(
                catManagementTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(catManagementTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(catManagementTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(catLabel)
                                        .addComponent(addItemTab, GroupLayout.PREFERRED_SIZE, 1058, GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6))
        );
        catManagementTabLayout.setVerticalGroup(
                catManagementTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(catManagementTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(catLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addItemTab, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dashboardTabs.addTab("catalogue", catManagementTab);

        // ===================== ORDERS TAB =====================
        Orders.setBackground(new Color(255, 255, 255));

        jLabel14.setFont(new Font("Segoe UI", 1, 48));
        jLabel14.setText("ORDERS");

        ordersTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String[]{"Order ID", "Merchant Name", "Total Amount (£)", "Order Date", "Current Status"}
        ) {
            Class[] types = new Class[]{
                    String.class, String.class, String.class, String.class, String.class
            };
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
        });
        ordersTable.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(ordersTable);

        orderSearchField.setText("Search....");

        hideCompletedButton.setText("Hide Delivered");
        hideCompletedButton.addActionListener(this::hideCompletedButtonActionPerformed);

        unHideAllButton.setText("Show All");
        unHideAllButton.addActionListener(this::unHideAllButtonActionPerformed);

        jComboBox1.setModel(new DefaultComboBoxModel<>(
                new String[]{"Accepted", "Ready to Dispatch", "Dispatched", "Delivered"}
        ));

        jLabel15.setFont(new Font("Segoe UI", 1, 12));
        jLabel15.setText("Update Delivery Status");

        jButton1.setText("Update Selected Status");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Generate Invoice");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        GroupLayout ordersPanelLayout = new GroupLayout(ordersPanel);
        ordersPanel.setLayout(ordersPanelLayout);
        ordersPanelLayout.setHorizontalGroup(
                ordersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ordersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 814, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(ordersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jComboBox1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jSeparator2)
                                                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel15)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(jSeparator3)
                                                        .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                                .addComponent(orderSearchField, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(hideCompletedButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(unHideAllButton)))
                                .addContainerGap())
        );
        ordersPanelLayout.setVerticalGroup(
                ordersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ordersPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(orderSearchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(hideCompletedButton)
                                        .addComponent(unHideAllButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ordersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                                .addGap(35, 35, 35))
                                        .addGroup(ordersPanelLayout.createSequentialGroup()
                                                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel15)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1)
                                                .addGap(5, 5, 5)
                                                .addComponent(jButton2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        ordersTab.addTab("Orders", ordersPanel);

        // ===================== MERCHANT BALANCE TAB =====================
        jLabel17.setFont(new Font("Segoe UI", 3, 12));
        jLabel17.setText("Merchant:");

        jComboBox3.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable2.setModel(new DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] { "Date", "Description", "Amount", "Balance" }
        ));
        jScrollPane7.setViewportView(jTable2);

        jTable4.setModel(new DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                new String [] { "Credit Limit", "Current Balance", "Remaining Credit" }
        ));
        jScrollPane8.setViewportView(jTable4);

        jLabel19.setFont(new Font("Segoe UI", 1, 12));
        jLabel19.setText("Record Payment");
        jLabel20.setText("Amount (£)");
        jLabel21.setText("Method");
        jLabel22.setText("Reference");
        jTextField3.setText("£0.00");
        jTextField5.setText("Ref #");
        jComboBox4.setModel(new DefaultComboBoxModel<>(new String[] { "Bank transfer", "Credit Card", "Debit Card", "Cash" }));
        jButton8.setText("Record payment");
        jButton8.addActionListener(this::jButton8ActionPerformed);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel19)
                                                        .addComponent(jButton8, GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel20)
                                                                        .addComponent(jLabel21)
                                                                        .addComponent(jLabel22))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jTextField5, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                                                        .addComponent(jComboBox4, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jTextField3))))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jSeparator5, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 15, Short.MAX_VALUE))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel17)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jComboBox3, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(jScrollPane8, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)))
                                .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 725, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel17)
                                        .addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jScrollPane8, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jSeparator5, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel19)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel20)
                                                        .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel21)
                                                        .addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel22)
                                                        .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton8)))
                                .addContainerGap(161, Short.MAX_VALUE))
        );

        ordersTab.addTab("Merchant Balance", jPanel2);

        GroupLayout OrdersLayout = new GroupLayout(Orders);
        Orders.setLayout(OrdersLayout);
        OrdersLayout.setHorizontalGroup(
                OrdersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(OrdersLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel14)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(ordersTab, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1070, Short.MAX_VALUE)
        );
        OrdersLayout.setVerticalGroup(
                OrdersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(OrdersLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel14)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ordersTab, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE))
        );

        dashboardTabs.addTab("orders", Orders);

        // ===================== REPORTS TAB =====================
        jPanel1.setBackground(new Color(255, 255, 255));

        jLabel12.setFont(new Font("Segoe UI", 1, 48));
        jLabel12.setText("REPORT");

        reportTextPane.setText("<REPORT TITLE>\n\n----------------------------------------------------\n\ninformation....");
        scrollPane.setViewportView(reportTextPane);

        systemTurnoverReport.setText("System Turnover Report");
        individualMerchantActvityButton.setText("Individual Merchant Activity");
        debtorReminders.setText("Debtor Reminders");
        merchantOrdersSummary.setText("Merchant Orders Summary");
        stockTurnoverReport.setText("Stock Turnover Report");
        generatePDFButton.setText("Generate pdf");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(systemTurnoverReport)
                                                        .addComponent(stockTurnoverReport))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(merchantOrdersSummary)
                                                        .addComponent(debtorReminders))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(individualMerchantActvityButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
                                                .addComponent(generatePDFButton, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(systemTurnoverReport)
                                        .addComponent(merchantOrdersSummary)
                                        .addComponent(individualMerchantActvityButton)
                                        .addComponent(generatePDFButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(debtorReminders)
                                        .addComponent(stockTurnoverReport))
                                .addContainerGap(210, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generate Report", jPanel1);

        // ===================== DEBTOR REMINDER TAB =====================
        jLabel18.setFont(new Font("Segoe UI", 1, 12));
        jLabel18.setText("Merchants with an outstanding balance. Send reminders to email them via SMTP");

        jTable5.setModel(new DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                new String [] { "Merchant", "Balance Owed", "Days Overdue" }
        ) {
            Class[] types = new Class [] {
                    String.class, String.class, String.class
            };
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
        });
        jScrollPane9.setViewportView(jTable5);

        jButton7.setText("Send Reminder");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane9, GroupLayout.PREFERRED_SIZE, 1021, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton7, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel18)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)
                                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Debtor Reminder", jPanel3);

        GroupLayout reportsTabLayout = new GroupLayout(reportsTab);
        reportsTab.setLayout(reportsTabLayout);
        reportsTabLayout.setHorizontalGroup(
                reportsTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 1064, Short.MAX_VALUE)
        );
        reportsTabLayout.setVerticalGroup(
                reportsTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, reportsTabLayout.createSequentialGroup()
                                .addGap(0, 27, Short.MAX_VALUE)
                                .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 626, GroupLayout.PREFERRED_SIZE))
        );

        dashboardTabs.addTab("reports", reportsTab);

        // ===================== APPLICATIONS TAB =====================
        jLabel13.setFont(new Font("Segoe UI", 1, 48));
        jLabel13.setText("Applications");

        jScrollPane4.setToolTipText("");
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String[]{"Applications ID", "Type", "Applicant", "Commercial/Non-Commercial", "Email Address"}
        ) {
            Class[] types = new Class[]{
                    String.class, String.class, String.class, String.class, String.class
            };
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
        });
        jScrollPane4.setViewportView(jTable1);

        jToggleButton5.setText("Approve");
        jToggleButton6.setText("Reject");

        GroupLayout ApplicationsLayout = new GroupLayout(Applications);
        Applications.setLayout(ApplicationsLayout);
        ApplicationsLayout.setHorizontalGroup(
                ApplicationsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ApplicationsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ApplicationsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
                                        .addGroup(ApplicationsLayout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, ApplicationsLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jToggleButton5)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jToggleButton6)))
                                .addContainerGap())
        );
        ApplicationsLayout.setVerticalGroup(
                ApplicationsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ApplicationsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 453, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ApplicationsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jToggleButton5)
                                        .addComponent(jToggleButton6))
                                .addContainerGap(95, Short.MAX_VALUE))
        );

        dashboardTabs.addTab("applications", Applications);

        // ===================== USERS TAB =====================
        userTab.setBackground(new Color(255, 255, 255));

        UserLabel.setFont(new Font("Segoe UI", 1, 48));
        UserLabel.setText("USER MANAGEMENT");

        userTabs.setBackground(new Color(255, 255, 255));
        Users.setBackground(new Color(255, 255, 255));

        jTextField16.setText("Search..");
        jTable3.setModel(new DefaultTableModel(
                new Object[][]{{null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}},
                new String[]{"AccountNo", "Username", "Password", "Role"}
        ));
        jScrollPane6.setViewportView(jTable3);

        jLabel16.setFont(new Font("Segoe UI", 1, 12));
        jLabel16.setText("Add User");
        jTextField17.setText("Username");
        jTextField18.setText("Password");
        jButton14.setText("Add User");
        jButton15.setText("Remove Selected User");
        jButton15.addActionListener(this::jButton15ActionPerformed);
        jComboBox2.setModel(new DefaultComboBoxModel<>(new String[]{"role...", "admin", "manager"}));
        jComboBox2.setToolTipText("Role");
        jLabel10.setFont(new Font("Segoe UI", 1, 48));
        jLabel10.setText("Users");

        jButton5.setText("Edit Selected User");
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jComboBox5.setModel(new DefaultComboBoxModel<>(new String[]{"role...", "admin", "manager"}));
        jComboBox5.setToolTipText("Role");
        jTextField19.setText("Password");
        jTextField20.setText("Username");
        jLabel23.setFont(new Font("Segoe UI", 1, 12));
        jLabel23.setText("Edit user");

        GroupLayout UsersLayout = new GroupLayout(Users);
        Users.setLayout(UsersLayout);
        UsersLayout.setHorizontalGroup(
                UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(UsersLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, UsersLayout.createSequentialGroup()
                                .addGroup(UsersLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(UsersLayout.createSequentialGroup()
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton15, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(UsersLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                                                        .addGroup(UsersLayout.createSequentialGroup()
                                                                .addComponent(jTextField16, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel16, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField17)
                                                .addComponent(jTextField18)
                                                .addComponent(jButton14)
                                                .addComponent(jComboBox2, 0, 200, Short.MAX_VALUE)
                                                .addComponent(jSeparator4))
                                        .addComponent(jLabel23, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField20)
                                                        .addComponent(jTextField19)
                                                        .addComponent(jComboBox5, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        UsersLayout.setVerticalGroup(
                UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(UsersLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10)
                                .addGap(1, 1, 1)
                                .addComponent(jTextField16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(UsersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(UsersLayout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton14)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel23)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField20, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton5)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton15)
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        userTabs.addTab("Users", Users);

        Merchants.setBackground(new Color(255, 255, 255));
        CatalogueTable1.setAutoCreateRowSorter(true);
        CatalogueTable1.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null}
                },
                new String[]{
                        "AccountNo", "AccountName", "ContactName", "Address",
                        "Phone", "Credit Limit", "Discount Plan", "Discount Rate", "Status"
                }
        ) {
            Class[] types = new Class[]{
                    String.class, String.class, String.class, String.class,
                    String.class, String.class, String.class, String.class, String.class
            };
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
        });
        jScrollPane3.setViewportView(CatalogueTable1);

        jButton16.setText("Remove Selected Row");
        jButton16.addActionListener(this::jButton16ActionPerformed);

        jLabel9.setFont(new Font("Segoe UI", 1, 48));
        jLabel9.setText("Merchants");

        jButton6.setText("Edit Selected Merchant");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        GroupLayout MerchantsLayout = new GroupLayout(Merchants);
        Merchants.setLayout(MerchantsLayout);
        MerchantsLayout.setHorizontalGroup(
                MerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, MerchantsLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                        .addGroup(MerchantsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9)
                                .addContainerGap(814, Short.MAX_VALUE))
                        .addGroup(MerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(MerchantsLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 1036, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        MerchantsLayout.setVerticalGroup(
                MerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, MerchantsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 390, Short.MAX_VALUE)
                                .addGroup(MerchantsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton16)
                                        .addComponent(jButton6))
                                .addGap(53, 53, 53))
                        .addGroup(MerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(MerchantsLayout.createSequentialGroup()
                                        .addGap(88, 88, 88)
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(88, Short.MAX_VALUE)))
        );

        userTabs.addTab("Merchants", Merchants);

        addMerchants.setBackground(new Color(255, 255, 255));
        addMerchantLabel.setFont(new Font("Segoe UI", 1, 48));
        addMerchantLabel.setText("Add merchant");
        merchant_usernameField.setText("Username");
        merchant_passwordField.setText("Password");
        accHolderNameField.setText("Account Holder Name");
        contactNameField.setText("Contact Name");
        addressField.setText("Address");
        creditLimitField.setText("Phone Number");
        discountPlanBox.setModel(new DefaultComboBoxModel<>(new String[]{"Fixed", "Flexible"}));
        discountRateField.setText("Discount Rate");
        addMerchantButton.setText("Add merchant");
        jLabel1.setFont(new Font("Segoe UI", 2, 12));
        jLabel1.setText("User Information");
        jLabel4.setFont(new Font("Segoe UI", 2, 12));
        jLabel4.setText("Account Details");

        GroupLayout addMerchantsLayout = new GroupLayout(addMerchants);
        addMerchants.setLayout(addMerchantsLayout);
        addMerchantsLayout.setHorizontalGroup(
                addMerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(addMerchantsLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(addMerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel1)
                                        .addGroup(addMerchantsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(addMerchantButton)
                                                .addGroup(addMerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(addMerchantLabel)
                                                        .addComponent(merchant_usernameField)
                                                        .addComponent(merchant_passwordField)
                                                        .addComponent(accHolderNameField)
                                                        .addComponent(contactNameField)
                                                        .addComponent(addressField)
                                                        .addComponent(creditLimitField)
                                                        .addComponent(discountPlanBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(discountRateField))))
                                .addContainerGap(717, Short.MAX_VALUE))
        );
        addMerchantsLayout.setVerticalGroup(
                addMerchantsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(addMerchantsLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(addMerchantLabel)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(merchant_usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(merchant_passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accHolderNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(contactNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(creditLimitField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discountPlanBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discountRateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addMerchantButton)
                                .addContainerGap(135, Short.MAX_VALUE))
        );

        userTabs.addTab("addMerchant", addMerchants);

        GroupLayout userTabLayout = new GroupLayout(userTab);
        userTab.setLayout(userTabLayout);
        userTabLayout.setHorizontalGroup(
                userTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(userTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(userTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(userTabs)
                                        .addGroup(userTabLayout.createSequentialGroup()
                                                .addComponent(UserLabel)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        userTabLayout.setVerticalGroup(
                userTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(userTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(UserLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userTabs)
                                .addContainerGap())
        );

        dashboardTabs.addTab("users", userTab);

        // ===================== SIDEBAR =====================
        Dashboard.setBackground(new Color(51, 51, 51));
        Dashboard.setPreferredSize(new Dimension(1200, 600));

        homeB.setBackground(new Color(51, 51, 51));
        homeB.setFont(new Font("Segoe UI Light", 0, 18));
        homeB.setForeground(new Color(255, 255, 255));
        homeB.setText("HOME");
        homeB.setToolTipText("");
        homeB.setBorder(null);
        homeB.addActionListener(this::homeBActionPerformed);

        usersB.setBackground(new Color(51, 51, 51));
        usersB.setFont(new Font("Segoe UI Light", 0, 18));
        usersB.setForeground(new Color(255, 255, 255));
        usersB.setText("USERS");
        usersB.setToolTipText("");
        usersB.setBorder(null);
        usersB.addActionListener(this::usersBActionPerformed);

        catB.setBackground(new Color(51, 51, 51));
        catB.setFont(new Font("Segoe UI Light", 0, 18));
        catB.setForeground(new Color(255, 255, 255));
        catB.setText("CATALOGUE");
        catB.setToolTipText("");
        catB.setBorder(null);
        catB.addActionListener(this::catBActionPerformed);

        ordersB.setBackground(new Color(51, 51, 51));
        ordersB.setFont(new Font("Segoe UI Light", 0, 18));
        ordersB.setForeground(new Color(255, 255, 255));
        ordersB.setText("ORDERS");
        ordersB.setToolTipText("");
        ordersB.setBorder(null);
        ordersB.addActionListener(this::ordersBActionPerformed);

        reportsB.setBackground(new Color(51, 51, 51));
        reportsB.setFont(new Font("Segoe UI Light", 0, 18));
        reportsB.setForeground(new Color(255, 255, 255));
        reportsB.setText("REPORTS");
        reportsB.setToolTipText("");
        reportsB.setBorder(null);
        reportsB.addActionListener(this::reportsBActionPerformed);

        appB.setBackground(new Color(51, 51, 51));
        appB.setFont(new Font("Segoe UI Light", 0, 18));
        appB.setForeground(new Color(255, 255, 255));
        appB.setText("APPLICATIONS");
        appB.setToolTipText("");
        appB.setBorder(null);
        appB.addActionListener(this::appBActionPerformed);

        GroupLayout DashboardLayout = new GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
                DashboardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(DashboardLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(DashboardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(homeB)
                                        .addComponent(catB)
                                        .addComponent(ordersB)
                                        .addComponent(usersB)
                                        .addComponent(reportsB)
                                        .addComponent(appB))
                                .addContainerGap(10, Short.MAX_VALUE))
        );
        DashboardLayout.setVerticalGroup(
                DashboardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(DashboardLayout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(homeB)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(catB)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ordersB)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reportsB)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(appB)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usersB)
                                .addContainerGap(383, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(Dashboard, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                        .addComponent(TaskBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(dashboardTabs, GroupLayout.PREFERRED_SIZE, 1070, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(dashboardTabs, GroupLayout.PREFERRED_SIZE, 688, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(39, 39, 39)
                                                .addComponent(Dashboard, GroupLayout.PREFERRED_SIZE, 661, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(TaskBar, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }
    // </editor-fold>

    // ===================== ACTION HANDLERS =====================

    private void homeBActionPerformed(ActionEvent evt) {
        dashboardTabs.setSelectedIndex(0);
    }

    private void usersBActionPerformed(ActionEvent evt) {
        if (dashboardTabs.getTabCount() > 5) {
            dashboardTabs.setSelectedIndex(5);
        }
    }

    private void catBActionPerformed(ActionEvent evt) {
        if (dashboardTabs.getTabCount() > 1) {
            dashboardTabs.setSelectedIndex(1);
        }
    }

    private void ordersBActionPerformed(ActionEvent evt) {
        if (dashboardTabs.getTabCount() > 2) {
            dashboardTabs.setSelectedIndex(2);
        }
    }

    private void reportsBActionPerformed(ActionEvent evt) {
        if (dashboardTabs.getTabCount() > 3) {
            dashboardTabs.setSelectedIndex(3);
        }
    }

    private void appBActionPerformed(ActionEvent evt) {
        if (dashboardTabs.getTabCount() > 4) {
            dashboardTabs.setSelectedIndex(4);
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        // handled by mouse click
    }

    private void exitButtonMouseClicked(MouseEvent evt) {
        System.exit(0);
    }

    private void TaskBarMouseDragged(MouseEvent evt) {
        int cordX = evt.getXOnScreen();
        int cordY = evt.getYOnScreen();
        this.setLocation(cordX - mousepX, cordY - mousepY);
    }

    private void TaskBarMousePressed(MouseEvent evt) {
        mousepX = evt.getX();
        mousepY = evt.getY();
    }

    private void jButton16ActionPerformed(ActionEvent evt) {
        // TODO: Remove selected merchant row
    }

    private void jButton15ActionPerformed(ActionEvent evt) {
        // TODO: Remove selected user
    }

    private void unitFieldActionPerformed(ActionEvent evt) { }
    private void availabilityFieldActionPerformed(ActionEvent evt) { }
    private void costFieldActionPerformed(ActionEvent evt) { }
    private void packageTypeFieldActionPerformed(ActionEvent evt) { }
    private void searchFieldActionPerformed(ActionEvent evt) { }
    private void jTextField2ActionPerformed(ActionEvent evt) { }

    // --- Orders action handlers ---

    private void hideCompletedButtonActionPerformed(ActionEvent evt) {
        // TODO: Filter out rows where status == "Completed"
    }

    private void unHideAllButtonActionPerformed(ActionEvent evt) {
        // TODO: Reset table filter, show all rows
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        if (controller != null) controller.updateSelectedOrderStatus();
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        // TODO: Generate invoice for selected order
    }

    private void jButton5ActionPerformed(ActionEvent evt) {
        // TODO: Edit selected user
    }

    private void jButton6ActionPerformed(ActionEvent evt) {
        // TODO: Edit selected merchant
    }

    private void jButton7ActionPerformed(ActionEvent evt) {
        // TODO: Send debtor reminder email to selected merchant
    }

    private void jButton8ActionPerformed(ActionEvent evt) {
        // TODO: Record payment for selected merchant
    }

    // ===================== PUBLIC ACCESSORS FOR CONTROLLER =====================

    public JTable getOrdersTable() { return ordersTable; }
    public JComboBox<String> getOrderStatusComboBox() { return jComboBox1; }
    public JTextField getOrderSearchField() { return orderSearchField; }
    public JLabel getPendingOrdersLabel() { return pendingOrders; }
    public JLabel getPendingApplicationsLabel() { return pendingApplications; }
    public JLabel getTotalUsersLabel() { return totalUsers; }
    public JLabel getRoleTXT() { return roleTXT; }
    public JLabel getNameTXT() { return nameTXT; }
    public JTable getCatalogueTable() { return CatalogueTable; }
    public JTable getMerchantsTable() { return CatalogueTable1; }
    public JTable getUsersTable() { return jTable3; }
    public JTable getApplicationsTable() { return jTable1; }

    // ===================== MAIN =====================

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        User user = null;
        EventQueue.invokeLater(() -> new Dashboard(user).setVisible(true));
    }

    // ===================== VARIABLE DECLARATIONS =====================

    JPanel Applications;
    JTable CatalogueTable;
    JTable CatalogueTable1;
    JPanel Dashboard;
    JPanel Merchants;
    JPanel Orders;
    JPanel TaskBar;
    JPanel TotalUsersPan;
    JLabel UserLabel;
    JPanel Users;
    JTextField accHolderNameField;
    JPanel addItem;
    JButton addItemButton;
    Label addItemLabel;
    JTabbedPane addItemTab;
    JToggleButton addMerchantButton;
    JLabel addMerchantLabel;
    JPanel addMerchants;
    JTextField addressField;
    JButton appB;
    JTextField availabilityField;
    JButton catB;
    JLabel catLabel;
    JPanel catManagementTab;
    JTextField contactNameField;
    JTextField costField;
    JTextField creditLimitField;
    JPanel dashboardTab;
    JTabbedPane dashboardTabs;
    JToggleButton debtorReminders;
    JComboBox<String> discountPlanBox;
    JTextField discountRateField;
    JButton exitButton;
    JButton generatePDFButton;
    JButton homeB;
    JToggleButton individualMerchantActvityButton;
    JButton jButton1;
    JButton jButton14;
    JButton jButton15;
    JButton jButton16;
    JButton jButton2;
    JButton jButton3;
    JButton jButton5;
    JButton jButton6;
    JButton jButton7;
    JButton jButton8;
    JComboBox<String> jComboBox1;
    JComboBox<String> jComboBox2;
    JComboBox<String> jComboBox3;
    JComboBox<String> jComboBox4;
    JComboBox<String> jComboBox5;
    JLabel jLabel1;
    JLabel jLabel10;
    JLabel jLabel11;
    JLabel jLabel12;
    JLabel jLabel13;
    JLabel jLabel14;
    JLabel jLabel15;
    JLabel jLabel16;
    JLabel jLabel17;
    JLabel jLabel18;
    JLabel jLabel19;
    JLabel jLabel2;
    JLabel jLabel20;
    JLabel jLabel21;
    JLabel jLabel22;
    JLabel jLabel23;
    JLabel jLabel3;
    JLabel jLabel4;
    JLabel jLabel5;
    JLabel jLabel6;
    JLabel jLabel7;
    JLabel jLabel8;
    JLabel jLabel9;
    JPanel jPanel1;
    JPanel jPanel2;
    JPanel jPanel3;
    JPanel jPanel6;
    JPanel jPanel7;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JScrollPane jScrollPane3;
    JScrollPane jScrollPane4;
    JScrollPane jScrollPane6;
    JScrollPane jScrollPane7;
    JScrollPane jScrollPane8;
    JScrollPane jScrollPane9;
    JSeparator jSeparator1;
    JSeparator jSeparator2;
    JSeparator jSeparator3;
    JSeparator jSeparator4;
    JSeparator jSeparator5;
    JSeparator jSeparator6;
    JTabbedPane jTabbedPane1;
    JTabbedPane jTabbedPane2;
    JTable jTable1;
    JTable jTable2;
    JTable jTable3;
    JTable jTable4;
    JTable jTable5;
    JTextField jTextField1;
    JTextField jTextField16;
    JTextField jTextField17;
    JTextField jTextField18;
    JTextField jTextField19;
    JTextField jTextField2;
    JTextField jTextField20;
    JTextField jTextField3;
    JTextField jTextField5;
    JToggleButton jToggleButton5;
    JToggleButton jToggleButton6;
    JToggleButton merchantOrdersSummary;
    JTextField merchant_passwordField;
    JTextField merchant_usernameField;
    JLabel nameTXT;
    JTextField orderSearchField;
    JButton ordersB;
    JPanel ordersPanel;
    JTabbedPane ordersTab;
    JTable ordersTable;
    JTextField packageField;
    JTextField packageTypeField;
    JLabel pendingApplications;
    JPanel pendingApplicationsPan;
    JLabel pendingOrders;
    JPanel pendingOrdersPan;
    JTextPane reportTextPane;
    JButton reportsB;
    JPanel reportsTab;
    JButton rmvSelectedButton;
    JPanel rolePanel;
    JLabel roleTXT;
    JScrollPane scrollPane;
    JTextField searchField;
    JToggleButton stockTurnoverReport;
    JToggleButton systemTurnoverReport;
    JLabel totalUsers;
    JButton unHideAllButton;
    JButton hideCompletedButton;
    JTextField unitField;
    JPanel userTab;
    JTabbedPane userTabs;
    JButton usersB;
    JLabel warningLabel;
}