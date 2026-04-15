package IPOS_Detailed_Design.gui.dashboard;

import IPOS_Detailed_Design.dao.*;
import IPOS_Detailed_Design.model.Product;
import IPOS_Detailed_Design.model.Order;
import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.AccountStatus;
import IPOS_Detailed_Design.model.enums.DiscountPlanType;
import IPOS_Detailed_Design.model.enums.UserRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * DashboardController — wires the Dashboard GUI to the DAO/database layer.
 *
 * HOW TO USE:
 *   In Dashboard constructor, after initComponents(), call:
 *       new DashboardController(this, currentUser).init();
 *
 * This class accesses all the package-private fields that NetBeans generated
 * in Dashboard.java. Make sure Dashboard is in the same package.
 */
public class DashboardController {

    private final Dashboard view;
    private final User currentUser;

    // DAOs
    private final UserDAO userDAO = new UserDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    private javax.swing.table.TableRowSorter<DefaultTableModel> orderSorter;

    public DashboardController(Dashboard view, User user) {
        this.view = view;
        this.currentUser = user;
    }

    // ─────────────────────────────────────────────────────────────
    // INIT — call once after initComponents()
    // ─────────────────────────────────────────────────────────────

    public void init() {
        loadRoleHeader();
        applyRolePermissions();

        clearAllTables();
        loadDashboardStats();
        loadCatalogueTable();
        loadMerchantsTable();
        loadMerchantComboBox();
        loadUsersTable();
        loadOrdersTable();
        loadApplicationsTable();
        checkLowStockWarning();
        wireButtons();
    }

    private void clearAllTables() {
        ((DefaultTableModel) view.CatalogueTable.getModel()).setRowCount(0);
        ((DefaultTableModel) view.CatalogueTable1.getModel()).setRowCount(0);
        ((DefaultTableModel) view.jTable1.getModel()).setRowCount(0);
        ((DefaultTableModel) view.jTable3.getModel()).setRowCount(0);
    }

    // ─────────────────────────────────────────────────────────────
    // CURRENT USER / ROLE-BASED GUI
    // ─────────────────────────────────────────────────────────────

    private void loadRoleHeader() {
        if (currentUser == null) {
            view.roleTXT.setText("GUEST");
            view.nameTXT.setText("Unknown User");
            return;
        }

        view.roleTXT.setText(currentUser.getRole() != null ? currentUser.getRole().name() : "UNKNOWN");

        if (currentUser.getAccountHolderName() != null && !currentUser.getAccountHolderName().isBlank()) {
            view.nameTXT.setText(currentUser.getAccountHolderName());
        } else if (currentUser.getContactName() != null && !currentUser.getContactName().isBlank()) {
            view.nameTXT.setText(currentUser.getContactName());
        } else if (currentUser.getUsername() != null && !currentUser.getUsername().isBlank()) {
            view.nameTXT.setText(currentUser.getUsername());
        } else {
            view.nameTXT.setText("Unknown User");
        }
    }

    private void applyRolePermissions() {
        if (currentUser == null || currentUser.getRole() == null) {
            return;
        }

        switch (currentUser.getRole()) {
            case MERCHANT -> {
                view.usersB.setVisible(false);
                view.reportsB.setVisible(false);
                view.appB.setVisible(false);
            }

            case ACCOUNTANT, SENIOR_ACCOUNTANT -> {
                view.catB.setVisible(false);
                view.appB.setVisible(false);
            }

            case WAREHOUSE_EMPLOYEE -> {
                view.usersB.setVisible(false);
                view.reportsB.setVisible(false);
                view.appB.setVisible(false);
            }

            case DELIVERY_DEPARTMENT_EMPLOYEE -> {
                view.usersB.setVisible(false);
                view.catB.setVisible(false);
                view.reportsB.setVisible(false);
                view.appB.setVisible(false);
            }

            case DIRECTOR_OF_OPERATIONS, ADMIN -> {
                // full access
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // DASHBOARD HOME — stats panels
    // ─────────────────────────────────────────────────────────────

    public void loadDashboardStats() {
        int pendingCount = orderDAO.getPendingOrderCount();
        view.pendingOrders.setText(String.valueOf(pendingCount));

        int userCount = userDAO.getTotalUserCount();
        view.totalUsers.setText(String.valueOf(userCount));

        int appCount = getPendingApplicationCount();
        view.pendingApplications.setText(String.valueOf(appCount));
    }

    private int getPendingApplicationCount() {
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                     "SELECT COUNT(*) FROM PU_Applications WHERE ApplicationStatus = 'Pending'")) {
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (java.sql.SQLException e) {
            System.err.println("DashboardController.getPendingApplicationCount: " + e.getMessage());
        }
        return 0;
    }

    // ─────────────────────────────────────────────────────────────
    // LOW STOCK WARNING — shown on dashboard home
    // ─────────────────────────────────────────────────────────────

    public void checkLowStockWarning() {
        List<Product> lowStock = productDAO.getLowStockProducts();
        if (!lowStock.isEmpty()) {
            view.jLabel6.setText("⚠  WARNING: " + lowStock.size() + " catalogue item(s) are below minimum stock level!");
            view.jLabel6.setForeground(new java.awt.Color(200, 50, 50));
        } else {
            view.jLabel6.setText("All stock levels are healthy.");
            view.jLabel6.setForeground(new java.awt.Color(50, 150, 50));
        }
    }

    // ─────────────────────────────────────────────────────────────
    // CATALOGUE TABLE
    // ─────────────────────────────────────────────────────────────

    public void loadCatalogueTable() {
        List<Product> products = productDAO.getAllActiveProducts();
        DefaultTableModel model = (DefaultTableModel) view.CatalogueTable.getModel();
        model.setRowCount(0);

        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getName() + (p.getDescription() != null ? " - " + p.getDescription() : ""),
                    p.getPackageType(),
                    p.getUnit(),
                    p.getUnitsInPack(),
                    "£" + p.getPackageCost(),
                    p.getAvailability(),
                    p.getStockLimit()
            });
        }
    }

    public void searchCatalogue(String keyword) {
        List<Product> results = keyword == null || keyword.isBlank() || keyword.equals("Search..")
                ? productDAO.getAllActiveProducts()
                : productDAO.searchProducts(keyword);

        DefaultTableModel model = (DefaultTableModel) view.CatalogueTable.getModel();
        model.setRowCount(0);
        for (Product p : results) {
            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getName(),
                    p.getPackageType(),
                    p.getUnit(),
                    p.getUnitsInPack(),
                    "£" + p.getPackageCost(),
                    p.getAvailability(),
                    p.getStockLimit()
            });
        }
    }

    public void addCatalogueItem() {
        try {
            Product p = new Product();
            p.setProductId("P-" + System.currentTimeMillis());
            p.setDescription(view.jTextField1.getText().trim());
            p.setPackageType(view.packageField.getText().trim());
            p.setUnit(view.unitField.getText().trim());
            p.setUnitsInPack(Integer.parseInt(view.packageTypeField.getText().trim()));
            p.setPackageCost(new BigDecimal(view.costField.getText().trim()));
            p.setAvailability(Integer.parseInt(view.availabilityField.getText().trim()));
            p.setStockLimit(Integer.parseInt(view.jTextField2.getText().trim()));
            p.setActive(true);
            p.setName(view.jTextField1.getText().trim());

            boolean ok = productDAO.addProduct(p);
            if (ok) {
                JOptionPane.showMessageDialog(view, "Item added successfully!");
                loadCatalogueTable();
                loadDashboardStats();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter valid numbers for numeric fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void removeSelectedCatalogueItem() {
        int row = view.CatalogueTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Please select a row to remove.");
            return;
        }

        int modelRow = view.CatalogueTable.convertRowIndexToModel(row);
        String productId = (String) view.CatalogueTable.getModel().getValueAt(modelRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Soft-delete product " + productId + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            productDAO.deleteProduct(productId);
            loadCatalogueTable();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // MERCHANTS TABLE
    // ─────────────────────────────────────────────────────────────

    public void loadMerchantsTable() {
        List<User> merchants = userDAO.getAllMerchants();
        DefaultTableModel model = (DefaultTableModel) view.CatalogueTable1.getModel();
        model.setRowCount(0);

        for (User m : merchants) {
            model.addRow(new Object[]{
                    m.getAccountNo(),
                    m.getAccountHolderName(),
                    m.getContactName(),
                    m.getAddress(),
                    m.getPhone(),
                    m.getCreditLimit() != null ? "£" + m.getCreditLimit() : "-",
                    m.getDiscountPlan() != null ? m.getDiscountPlan().name() : "-",
                    m.getDiscountRate() != null ? m.getDiscountRate() : "-"
            });
        }
    }

    public void addMerchant() {
        String username = view.merchant_usernameField.getText().trim();
        String password = view.merchant_passwordField.getText().trim();
        String accHolder = view.accHolderNameField.getText().trim();
        String contact = view.contactNameField.getText().trim();
        String address = view.addressField.getText().trim();
        String phone = view.creditLimitField.getText().trim();
        String discRate = view.discountRateField.getText().trim();
        String discPlan = (String) view.discountPlanBox.getSelectedItem();

        if (username.isBlank() || password.isBlank() || accHolder.isBlank()) {
            JOptionPane.showMessageDialog(view, "Username, Password and Account Holder Name are required.");
            return;
        }

        User newMerchant = new User();
        newMerchant.setUsername(username);
        newMerchant.setPassword(password);
        newMerchant.setRole(UserRole.MERCHANT);
        newMerchant.setAccountHolderName(accHolder);
        newMerchant.setContactName(contact);
        newMerchant.setAddress(address);
        newMerchant.setPhone(phone);
        newMerchant.setDiscountPlan("Fixed".equals(discPlan) ? DiscountPlanType.FIXED : DiscountPlanType.FLEXIBLE);
        newMerchant.setDiscountRate(discRate);
        newMerchant.setAccountStatus(AccountStatus.NORMAL);
        newMerchant.setOutstandingBalance(BigDecimal.ZERO);

        String limitStr = JOptionPane.showInputDialog(view, "Enter credit limit (£):", "1000.00");
        BigDecimal creditLimit;
        try {
            creditLimit = new BigDecimal(limitStr == null ? "1000.00" : limitStr.trim());
        } catch (NumberFormatException ex) {
            creditLimit = new BigDecimal("1000.00");
        }
        newMerchant.setCreditLimit(creditLimit);

        int id = userDAO.createUser(newMerchant);
        if (id > 0) {
            JOptionPane.showMessageDialog(view, "Merchant created with ID: " + id);
            loadMerchantsTable();
            loadMerchantComboBox();
            loadDashboardStats();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to create merchant. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeSelectedMerchant() {
        int row = view.CatalogueTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Please select a merchant to remove.");
            return;
        }
        int modelRow = view.CatalogueTable1.convertRowIndexToModel(row);
        String accountNo = (String) view.CatalogueTable1.getModel().getValueAt(modelRow, 0);

        List<User> merchants = userDAO.getAllMerchants();
        int merchantId = -1;
        for (User m : merchants) {
            if (accountNo != null && accountNo.equals(m.getAccountNo())) {
                merchantId = m.getUserId();
                break;
            }
        }

        if (merchantId == -1) {
            JOptionPane.showMessageDialog(view, "Could not find merchant ID.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Delete merchant account " + accountNo + "? This cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            userDAO.deleteMerchant(merchantId);
            loadMerchantsTable();
            loadMerchantComboBox();
            loadDashboardStats();
        }
    }

    private void loadMerchantComboBox() {
        List<User> merchants = userDAO.getAllMerchants();
        view.jComboBox3.removeAllItems();
        view.jComboBox3.addItem("-- Select Merchant --");
        for (User m : merchants) {
            view.jComboBox3.addItem(m.getAccountNo() + " — " + m.getAccountHolderName());
        }
    }

    private void loadMerchantBalancePanel() {
        String selected = (String) view.jComboBox3.getSelectedItem();
        if (selected == null || selected.startsWith("--")) return;

        String accountNo = selected.split(" — ")[0].trim();
        List<User> merchants = userDAO.getAllMerchants();
        User merchant = merchants.stream()
                .filter(m -> accountNo.equals(m.getAccountNo()))
                .findFirst()
                .orElse(null);

        if (merchant == null) return;

        DefaultTableModel creditModel = (DefaultTableModel) view.jTable4.getModel();
        creditModel.setRowCount(0);
        BigDecimal limit = merchant.getCreditLimit() != null ? merchant.getCreditLimit() : BigDecimal.ZERO;
        BigDecimal balance = userDAO.getOutstandingBalance(merchant.getUserId());
        creditModel.addRow(new Object[]{"£" + limit, "£" + balance, "£" + limit.subtract(balance)});

        DefaultTableModel invoiceModel = (DefaultTableModel) view.jTable2.getModel();
        invoiceModel.setRowCount(0);
        view.jTable2.getColumnModel().getColumn(3).setHeaderValue("Status");
        view.jTable2.getTableHeader().repaint();

        String sql = "SELECT ip.InvoiceID, ip.OrderID, ip.IssueDate, o.TotalAmount, ip.PaymentStatus " +
                "FROM Invoices_Payments ip " +
                "JOIN Orders o ON ip.OrderID = o.OrderID " +
                "WHERE o.MerchantID = ? ORDER BY ip.IssueDate DESC";

        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchant.getUserId());
            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                invoiceModel.addRow(new Object[]{
                        rs.getDate("IssueDate").toLocalDate(),
                        "Inv#" + rs.getInt("InvoiceID") + " (" + rs.getString("OrderID") + ")",
                        "£" + rs.getBigDecimal("TotalAmount"),
                        rs.getString("PaymentStatus")
                });
            }
        } catch (java.sql.SQLException e) {
            System.err.println("loadMerchantBalancePanel error: " + e.getMessage());
        }
    }

    private void changeMerchantStatus(AccountStatus newStatus) {
        int row = view.CatalogueTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Select a merchant first.");
            return;
        }

        int modelRow = view.CatalogueTable1.convertRowIndexToModel(row);
        String accountNo = (String) view.CatalogueTable1.getModel().getValueAt(modelRow, 0);

        List<User> merchants = userDAO.getAllMerchants();
        int id = merchants.stream()
                .filter(m -> accountNo.equals(m.getAccountNo()))
                .mapToInt(User::getUserId)
                .findFirst()
                .orElse(-1);

        if (id == -1) return;

        boolean ok = userDAO.setAccountStatus(id, newStatus);
        if (ok) {
            loadMerchantsTable();
        } else {
            JOptionPane.showMessageDialog(view, "Status change failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void wireMerchantStatusMenu() {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem suspend = new JMenuItem("Suspend Merchant");
        JMenuItem inDef = new JMenuItem("Mark In Default");
        JMenuItem restore = new JMenuItem("Restore to Normal");

        suspend.addActionListener(e -> changeMerchantStatus(AccountStatus.SUSPENDED));
        inDef.addActionListener(e -> changeMerchantStatus(AccountStatus.IN_DEFAULT));
        restore.addActionListener(e -> changeMerchantStatus(AccountStatus.NORMAL));

        menu.add(suspend);
        menu.add(inDef);
        menu.add(restore);

        view.CatalogueTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            private void maybeShow(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int r = view.CatalogueTable1.rowAtPoint(e.getPoint());
                    if (r >= 0) view.CatalogueTable1.setRowSelectionInterval(r, r);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mousePressed(java.awt.event.MouseEvent e) { maybeShow(e); }
            public void mouseReleased(java.awt.event.MouseEvent e) { maybeShow(e); }
        });
    }

    // ─────────────────────────────────────────────────────────────
    // USERS TABLE
    // ─────────────────────────────────────────────────────────────

    public void loadUsersTable() {
        List<User> users = userDAO.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) view.jTable3.getModel();
        model.setRowCount(0);

        for (User u : users) {
            model.addRow(new Object[]{
                    u.getAccountNo() != null ? u.getAccountNo() : "-",
                    u.getUsername(),
                    "••••••••",
                    u.getRole() != null ? u.getRole().name() : "-"
            });
        }
    }

    public void addStaffUser() {
        String username = view.jTextField17.getText().trim();
        String password = view.jTextField18.getText().trim();
        String roleStr = (String) view.jComboBox2.getSelectedItem();

        if (username.isBlank() || password.isBlank() || "role...".equals(roleStr)) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields and select a role.");
            return;
        }

        UserRole selectedRole;
        if ("admin".equalsIgnoreCase(roleStr)) {
            selectedRole = UserRole.ADMIN;
        } else if ("manager".equalsIgnoreCase(roleStr) || "director".equalsIgnoreCase(roleStr)) {
            selectedRole = UserRole.DIRECTOR_OF_OPERATIONS;
        } else if ("accountant".equalsIgnoreCase(roleStr)) {
            selectedRole = UserRole.ACCOUNTANT;
        } else if ("senior accountant".equalsIgnoreCase(roleStr) || "senior_accountant".equalsIgnoreCase(roleStr)) {
            selectedRole = UserRole.SENIOR_ACCOUNTANT;
        } else if ("warehouse employee".equalsIgnoreCase(roleStr) || "warehouse_employee".equalsIgnoreCase(roleStr)) {
            selectedRole = UserRole.WAREHOUSE_EMPLOYEE;
        } else if ("delivery department employee".equalsIgnoreCase(roleStr) || "delivery_department_employee".equalsIgnoreCase(roleStr)) {
            selectedRole = UserRole.DELIVERY_DEPARTMENT_EMPLOYEE;
        } else {
            JOptionPane.showMessageDialog(view, "Unsupported role selected.");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(selectedRole);
        newUser.setAccountStatus(AccountStatus.NORMAL);
        newUser.setOutstandingBalance(BigDecimal.ZERO);

        int id = userDAO.createUser(newUser);
        if (id > 0) {
            JOptionPane.showMessageDialog(view, "User created successfully.");
            loadUsersTable();
            loadDashboardStats();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to create user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeSelectedUser() {
        int row = view.jTable3.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Please select a user to remove.");
            return;
        }

        int modelRow = view.jTable3.convertRowIndexToModel(row);
        String username = (String) view.jTable3.getModel().getValueAt(modelRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Remove user '" + username + "'?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try (java.sql.Connection conn = DatabaseConnection.getConnection();
                 java.sql.PreparedStatement ps = conn.prepareStatement(
                         "DELETE FROM Users WHERE Username = ?")) {
                ps.setString(1, username);
                ps.executeUpdate();
            } catch (java.sql.SQLException e) {
                System.err.println("Remove user error: " + e.getMessage());
            }
            loadUsersTable();
            loadDashboardStats();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // ORDERS TABLE
    // ─────────────────────────────────────────────────────────────

    public void loadOrdersTable() {
        List<Order> orders = orderDAO.getAllOrders();

        DefaultTableModel model = (DefaultTableModel) view.ordersTable.getModel();

        view.ordersTable.setRowSorter(null);
        orderSorter = null;
        model.setRowCount(0);

        for (Order o : orders) {
            model.addRow(new Object[]{
                    o.getOrderId(),
                    o.getMerchantId(),
                    o.getTotalAmount() != null ? "£" + o.getTotalAmount() : "-",
                    o.getOrderDate() != null ? o.getOrderDate().toLocalDate() : "-",
                    o.getStatus() != null ? o.getStatus().name() : "-"
            });
        }

        orderSorter = new javax.swing.table.TableRowSorter<>(model);
        view.ordersTable.setRowSorter(orderSorter);

        orderSorter.setRowFilter(javax.swing.RowFilter.notFilter(
                javax.swing.RowFilter.regexFilter("(?i)DELIVERED|CANCELLED", 4)));

        view.pendingOrders.setText(String.valueOf(orderDAO.getPendingOrderCount()));
    }

    public void updateSelectedOrderStatus() {
        if (view.ordersTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(view, "Select an order first.");
            return;
        }

        int modelRow = view.ordersTable.convertRowIndexToModel(view.ordersTable.getSelectedRow());
        String orderId = (String) view.ordersTable.getModel().getValueAt(modelRow, 0);

        String[] options = {"PROCESSING", "PACKED", "DISPATCHED", "DELIVERED", "CANCELLED"};
        String choice = (String) JOptionPane.showInputDialog(
                view,
                "Select new status for " + orderId + ":",
                "Update Status",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice != null) {
            String dispatch = null;
            if (choice.equals("DISPATCHED")) {
                dispatch = JOptionPane.showInputDialog(view, "Enter dispatch details (courier, ref no, etc):");
            }

            boolean ok = orderDAO.updateOrderStatus(
                    orderId,
                    IPOS_Detailed_Design.model.enums.OrderStatus.valueOf(choice),
                    dispatch
            );

            if (ok) {
                JOptionPane.showMessageDialog(view, "Order " + orderId + " updated to " + choice);
                loadOrdersTable();
            } else {
                JOptionPane.showMessageDialog(view, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filterOrders() {
        if (orderSorter == null) return;

        String text = view.orderSearchField.getText().trim();
        orderSorter.setRowFilter(text.isEmpty()
                ? null
                : javax.swing.RowFilter.regexFilter("(?i)" + text));
    }

    private void generateInvoiceForSelectedOrder() {
        int row = view.ordersTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Select an order first.");
            return;
        }

        int modelRow = view.ordersTable.convertRowIndexToModel(row);
        String orderId = (String) view.ordersTable.getModel().getValueAt(modelRow, 0);

        boolean ok = invoiceDAO.createInvoice(orderId);
        JOptionPane.showMessageDialog(view,
                ok ? "Invoice created for " + orderId
                        : "Invoice already exists or failed for " + orderId);
    }

    // ─────────────────────────────────────────────────────────────
    // APPLICATIONS TABLE
    // ─────────────────────────────────────────────────────────────

    public void loadApplicationsTable() {
        DefaultTableModel model = (DefaultTableModel) view.jTable1.getModel();
        model.setRowCount(0);

        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                     "SELECT ApplicationID, ApplicationType, ApplicantName, CompanyName, EmailAddress, ApplicationStatus " +
                             "FROM PU_Applications ORDER BY ApplicationDate DESC")) {
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ApplicationID"),
                        rs.getString("ApplicationStatus"),
                        rs.getString("ApplicantName"),
                        rs.getString("ApplicationType"),
                        rs.getString("EmailAddress")
                });
            }
        } catch (java.sql.SQLException e) {
            System.err.println("loadApplicationsTable error: " + e.getMessage());
        }
    }

    public void approveSelectedApplication() {
        int row = view.jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Select an application first.");
            return;
        }
        int modelRow = view.jTable1.convertRowIndexToModel(row);
        String appId = (String) view.jTable1.getModel().getValueAt(modelRow, 0);
        updateApplicationStatus(appId, "Approved");
    }

    public void rejectSelectedApplication() {
        int row = view.jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Select an application first.");
            return;
        }
        int modelRow = view.jTable1.convertRowIndexToModel(row);
        String appId = (String) view.jTable1.getModel().getValueAt(modelRow, 0);
        updateApplicationStatus(appId, "Rejected");
    }

    private void updateApplicationStatus(String appId, String status) {
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                     "UPDATE PU_Applications SET ApplicationStatus = ? WHERE ApplicationID = ?")) {
            ps.setString(1, status);
            ps.setString(2, appId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Application " + appId + " marked as " + status + ".");
            loadApplicationsTable();
            loadDashboardStats();
        } catch (java.sql.SQLException e) {
            System.err.println("updateApplicationStatus error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // PAYMENTS
    // ─────────────────────────────────────────────────────────────

    private void recordPayment() {
        String selected = (String) view.jComboBox3.getSelectedItem();
        if (selected == null || selected.startsWith("--")) {
            JOptionPane.showMessageDialog(view, "Select a merchant from the dropdown first.");
            return;
        }

        String accountNo = selected.split(" — ")[0].trim();

        List<User> merchants = userDAO.getAllMerchants();
        User merchant = merchants.stream()
                .filter(m -> accountNo.equals(m.getAccountNo()))
                .findFirst()
                .orElse(null);

        if (merchant == null) {
            JOptionPane.showMessageDialog(view, "Merchant not found.");
            return;
        }

        String amtStr = view.jTextField3.getText().trim().replace("£", "");
        String method = (String) view.jComboBox4.getSelectedItem();
        String ref = view.jTextField5.getText().trim();

        if (amtStr.isBlank()) {
            JOptionPane.showMessageDialog(view, "Enter a payment amount.");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amtStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid amount.");
            return;
        }

        boolean ok = paymentDAO.recordPayment(merchant.getUserId(), amount, method, ref);
        if (ok) {
            userDAO.reduceBalance(merchant.getUserId(), amount);

            BigDecimal remaining = userDAO.getOutstandingBalance(merchant.getUserId());
            if (remaining.compareTo(BigDecimal.ZERO) <= 0
                    && merchant.getAccountStatus() == AccountStatus.SUSPENDED) {
                userDAO.restoreMerchantToNormal(merchant.getUserId());
            }

            JOptionPane.showMessageDialog(view, "Payment of £" + amount + " recorded.");
            loadMerchantsTable();
            loadMerchantBalancePanel();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to record payment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─────────────────────────────────────────────────────────────
    // WIRE ALL BUTTON ACTIONS
    // ─────────────────────────────────────────────────────────────

    private void wireButtons() {
        view.searchField.addActionListener(e -> searchCatalogue(view.searchField.getText()));
        view.searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { searchCatalogue(view.searchField.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { searchCatalogue(view.searchField.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchCatalogue(view.searchField.getText()); }
        });

        view.addItemButton.addActionListener(e -> addCatalogueItem());
        view.rmvSelectedButton.addActionListener(e -> removeSelectedCatalogueItem());

        view.addMerchantButton.addActionListener(e -> addMerchant());
        view.jButton16.addActionListener(e -> removeSelectedMerchant());

        view.jButton14.addActionListener(e -> addStaffUser());
        view.jButton15.addActionListener(e -> removeSelectedUser());

        view.jToggleButton5.addActionListener(e -> approveSelectedApplication());
        view.jToggleButton6.addActionListener(e -> rejectSelectedApplication());

        view.systemTurnoverReport.addActionListener(e -> loadSystemTurnoverReport());
        view.merchantOrdersSummary.addActionListener(e -> loadMerchantOrdersSummaryPrompt());
        view.individualMerchantActvityButton.addActionListener(e -> loadIndividualMerchantActivityPrompt());
        view.stockTurnoverReport.addActionListener(e -> loadStockTurnoverReport());
        view.debtorReminders.addActionListener(e -> loadDebtorRemindersReport());

        view.generatePDFButton.addActionListener(e ->
                JOptionPane.showMessageDialog(view, "PDF export: copy the text above into a document or use a library like iText."));

        view.jButton8.addActionListener(e -> recordPayment());
        view.jButton2.addActionListener(e -> generateInvoiceForSelectedOrder());
        wireMerchantStatusMenu();

        view.jComboBox3.addActionListener(e -> loadMerchantBalancePanel());

        view.orderSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterOrders(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterOrders(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterOrders(); }
        });

        view.hideCompletedButton.addActionListener(e -> {
            if (orderSorter != null) {
                orderSorter.setRowFilter(javax.swing.RowFilter.notFilter(
                        javax.swing.RowFilter.regexFilter("(?i)DELIVERED|CANCELLED", 4)));
            }
        });

        view.unHideAllButton.addActionListener(e -> {
            if (orderSorter != null) {
                orderSorter.setRowFilter(null);
            }
        });
    }

    // ─────────────────────────────────────────────────────────────
    // REPORT GENERATORS — text loaded into reportTextPane
    // ─────────────────────────────────────────────────────────────

    private void loadSystemTurnoverReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("SYSTEM TURNOVER REPORT\n");
        sb.append("Generated: ").append(java.time.LocalDate.now()).append("\n");
        sb.append("──────────────────────────────────────────────────────\n\n");

        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                     "SELECT p.ProductID, p.Name, SUM(oi.Quantity) AS TotalSold, " +
                             "SUM(oi.Quantity * oi.UnitCost) AS Revenue " +
                             "FROM OrderItems oi JOIN Catalogue p ON oi.ProductID = p.ProductID " +
                             "GROUP BY p.ProductID, p.Name ORDER BY Revenue DESC")) {
            java.sql.ResultSet rs = ps.executeQuery();
            sb.append(String.format("%-15s %-30s %12s %15s%n", "Product ID", "Name", "Units Sold", "Revenue (£)"));
            sb.append("──────────────────────────────────────────────────────\n");
            BigDecimal total = BigDecimal.ZERO;
            while (rs.next()) {
                BigDecimal rev = rs.getBigDecimal("Revenue");
                if (rev == null) rev = BigDecimal.ZERO;
                total = total.add(rev);
                sb.append(String.format("%-15s %-30s %12d %15.2f%n",
                        rs.getString("ProductID"),
                        rs.getString("Name"),
                        rs.getInt("TotalSold"),
                        rev));
            }
            sb.append("──────────────────────────────────────────────────────\n");
            sb.append(String.format("%-48s TOTAL: £%.2f%n", "", total));
        } catch (java.sql.SQLException e) {
            sb.append("Error loading report: ").append(e.getMessage());
        }
        view.reportTextPane.setText(sb.toString());
    }

    private void loadMerchantOrdersSummaryPrompt() {
        String input = JOptionPane.showInputDialog(view,
                "Enter Merchant Account No (or leave blank for all merchants):");
        if (input == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("MERCHANT ORDERS SUMMARY\n");
        sb.append("Generated: ").append(java.time.LocalDate.now()).append("\n");
        sb.append("──────────────────────────────────────────────────────\n\n");

        String sql = input.isBlank()
                ? "SELECT o.OrderID, u.AccountHolderName, o.OrderDate, o.TotalAmount, o.OrderStatus " +
                "FROM Orders o JOIN Users u ON o.MerchantID = u.UserID ORDER BY o.OrderDate DESC"
                : "SELECT o.OrderID, u.AccountHolderName, o.OrderDate, o.TotalAmount, o.OrderStatus " +
                "FROM Orders o JOIN Users u ON o.MerchantID = u.UserID " +
                "WHERE u.AccountNo = ? ORDER BY o.OrderDate DESC";

        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            if (!input.isBlank()) ps.setString(1, input.trim());
            java.sql.ResultSet rs = ps.executeQuery();

            sb.append(String.format("%-12s %-25s %-22s %12s %12s%n",
                    "Order ID", "Merchant", "Date", "Amount £", "Status"));
            sb.append("──────────────────────────────────────────────────────\n");

            BigDecimal total = BigDecimal.ZERO;
            int count = 0;
            while (rs.next()) {
                BigDecimal amt = rs.getBigDecimal("TotalAmount");
                if (amt == null) amt = BigDecimal.ZERO;
                total = total.add(amt);
                count++;
                sb.append(String.format("%-12s %-25s %-22s %12.2f %12s%n",
                        rs.getString("OrderID"),
                        rs.getString("AccountHolderName"),
                        rs.getTimestamp("OrderDate"),
                        amt,
                        rs.getString("OrderStatus")));
            }

            sb.append("──────────────────────────────────────────────────────\n");
            sb.append(String.format("Total Orders: %d    Total Value: £%.2f%n", count, total));
        } catch (java.sql.SQLException e) {
            sb.append("Error loading report: ").append(e.getMessage());
        }

        view.reportTextPane.setText(sb.toString());
    }

    private void loadIndividualMerchantActivityPrompt() {
        String accountNo = JOptionPane.showInputDialog(view, "Enter Merchant Account No:");
        if (accountNo == null || accountNo.isBlank()) return;

        StringBuilder sb = new StringBuilder();
        sb.append("INDIVIDUAL MERCHANT ACTIVITY REPORT\n");
        sb.append("Account No: ").append(accountNo).append("\n");
        sb.append("Generated: ").append(java.time.LocalDate.now()).append("\n");
        sb.append("──────────────────────────────────────────────────────\n\n");

        try (java.sql.Connection conn = DatabaseConnection.getConnection()) {
            java.sql.PreparedStatement psMerchant = conn.prepareStatement(
                    "SELECT * FROM Users WHERE AccountNo = ?");
            psMerchant.setString(1, accountNo);
            java.sql.ResultSet rsMerchant = psMerchant.executeQuery();
            if (!rsMerchant.next()) {
                view.reportTextPane.setText("Merchant not found.");
                return;
            }

            sb.append("Name:    ").append(rsMerchant.getString("AccountHolderName")).append("\n");
            sb.append("Contact: ").append(rsMerchant.getString("ContactName")).append("\n");
            sb.append("Address: ").append(rsMerchant.getString("Address")).append("\n");
            sb.append("Balance: £").append(rsMerchant.getBigDecimal("OutstandingBalance")).append("\n\n");

            int merchantId = rsMerchant.getInt("UserID");

            java.sql.PreparedStatement psOrders = conn.prepareStatement(
                    "SELECT * FROM Orders WHERE MerchantID = ? ORDER BY OrderDate DESC");
            psOrders.setInt(1, merchantId);
            java.sql.ResultSet rsOrders = psOrders.executeQuery();

            while (rsOrders.next()) {
                String orderId = rsOrders.getString("OrderID");
                sb.append("Order: ").append(orderId)
                        .append("  Date: ").append(rsOrders.getTimestamp("OrderDate"))
                        .append("  Status: ").append(rsOrders.getString("OrderStatus"))
                        .append("  Total: £").append(rsOrders.getBigDecimal("TotalAmount")).append("\n");

                java.sql.PreparedStatement psItems = conn.prepareStatement(
                        "SELECT oi.*, c.Name FROM OrderItems oi " +
                                "JOIN Catalogue c ON oi.ProductID = c.ProductID WHERE oi.OrderID = ?");
                psItems.setString(1, orderId);
                java.sql.ResultSet rsItems = psItems.executeQuery();
                while (rsItems.next()) {
                    sb.append(String.format("    %-30s  Qty: %3d  Unit: £%.2f  Line: £%.2f%n",
                            rsItems.getString("Name"),
                            rsItems.getInt("Quantity"),
                            rsItems.getBigDecimal("UnitCost"),
                            rsItems.getBigDecimal("UnitCost").multiply(
                                    java.math.BigDecimal.valueOf(rsItems.getInt("Quantity")))));
                }
                sb.append("\n");
            }
        } catch (java.sql.SQLException e) {
            sb.append("Error: ").append(e.getMessage());
        }

        view.reportTextPane.setText(sb.toString());
    }

    private void loadStockTurnoverReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("STOCK TURNOVER REPORT\n");
        sb.append("Generated: ").append(java.time.LocalDate.now()).append("\n");
        sb.append("──────────────────────────────────────────────────────\n\n");

        List<Product> products = productDAO.getAllActiveProducts();
        List<Product> lowStock = productDAO.getLowStockProducts();

        sb.append(String.format("%-15s %-30s %12s %12s %10s%n",
                "Product ID", "Name", "Availability", "Stock Limit", "Status"));
        sb.append("──────────────────────────────────────────────────────\n");

        for (Product p : products) {
            String status = p.getAvailability() < p.getStockLimit() ? "⚠ LOW" : "OK";
            int recommended = (int) Math.max(0, p.getStockLimit() * 1.1 - p.getAvailability());
            sb.append(String.format("%-15s %-30s %12d %12d %10s%n",
                    p.getProductId(), p.getName(), p.getAvailability(), p.getStockLimit(), status));
            if (p.getAvailability() < p.getStockLimit()) {
                sb.append(String.format("    → Recommended reorder qty: %d%n", recommended));
            }
        }

        sb.append("\nTotal products below stock limit: ").append(lowStock.size());
        view.reportTextPane.setText(sb.toString());
    }

    private void loadDebtorRemindersReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("DEBTOR REMINDERS REPORT\n");
        sb.append("Generated: ").append(java.time.LocalDate.now()).append("\n");
        sb.append("──────────────────────────────────────────────────────\n\n");

        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                     "SELECT u.AccountNo, u.AccountHolderName, u.OutstandingBalance, " +
                             "u.AccountStatus, MAX(DATEDIFF(CURDATE(), ip.DueDate)) AS MaxDaysOverdue " +
                             "FROM Users u " +
                             "JOIN Orders o ON o.MerchantID = u.UserID " +
                             "JOIN Invoices_Payments ip ON ip.OrderID = o.OrderID " +
                             "WHERE u.Role = 'Merchant' AND ip.PaymentStatus != 'Paid' " +
                             "GROUP BY u.UserID HAVING MaxDaysOverdue > 0 " +
                             "ORDER BY MaxDaysOverdue DESC")) {
            java.sql.ResultSet rs = ps.executeQuery();

            sb.append(String.format("%-12s %-25s %15s %10s %12s%n",
                    "Account No", "Name", "Balance £", "Status", "Days Overdue"));
            sb.append("──────────────────────────────────────────────────────\n");

            boolean any = false;
            while (rs.next()) {
                any = true;
                sb.append(String.format("%-12s %-25s %15.2f %10s %12d%n",
                        rs.getString("AccountNo"),
                        rs.getString("AccountHolderName"),
                        rs.getBigDecimal("OutstandingBalance"),
                        rs.getString("AccountStatus"),
                        rs.getLong("MaxDaysOverdue")));
            }

            if (!any) sb.append("No overdue payments found.\n");
        } catch (java.sql.SQLException e) {
            sb.append("Error: ").append(e.getMessage());
        }

        view.reportTextPane.setText(sb.toString());
    }
}