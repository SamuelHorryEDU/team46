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
 *       new DashboardController(this).init();
 *
 * This class accesses all the package-private fields that NetBeans generated
 * in Dashboard.java. Make sure Dashboard is in the same package.
 */
public class DashboardController {

    private final Dashboard view;

    // DAOs
    private final UserDAO    userDAO    = new UserDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO   orderDAO   = new OrderDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    public DashboardController(Dashboard view) {
        this.view = view;
    }

    // ─────────────────────────────────────────────────────────────
    // INIT — call once after initComponents()
    // ─────────────────────────────────────────────────────────────

    public void init() {
        clearAllTables();
        loadDashboardStats();
        loadCatalogueTable();
        loadMerchantsTable();
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
    // DASHBOARD HOME — stats panels
    // ─────────────────────────────────────────────────────────────

    public void loadDashboardStats() {
        int pendingCount = orderDAO.getPendingOrderCount();
        view.pendingOrders.setText(String.valueOf(pendingCount));

        int userCount = userDAO.getTotalUserCount();
        view.totalUsers.setText(String.valueOf(userCount));

        // Pending applications count from PU_Applications table
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
        model.setRowCount(0); // clear existing rows

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

    /** Search catalogue — triggered by searchField action or document listener */
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

    /** Add item — reads from the addItem tab fields */
    public void addCatalogueItem() {
        try {
            Product p = new Product();
            // Generate a simple ID — in production you'd let the DB auto-generate or use a sequence
            p.setProductId("P-" + System.currentTimeMillis());
            p.setDescription(view.jTextField1.getText().trim());
            p.setPackageType(view.packageField.getText().trim());
            p.setUnit(view.unitField.getText().trim());
            p.setUnitsInPack(Integer.parseInt(view.packageTypeField.getText().trim()));
            p.setPackageCost(new BigDecimal(view.costField.getText().trim()));
            p.setAvailability(Integer.parseInt(view.availabilityField.getText().trim()));
            p.setStockLimit(Integer.parseInt(view.jTextField2.getText().trim()));
            p.setActive(true);
            p.setName(view.jTextField1.getText().trim()); // description used as name here

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

    /** Remove selected row from catalogue */
    public void removeSelectedCatalogueItem() {
        int row = view.CatalogueTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Please select a row to remove.");
            return;
        }
        // Convert to model index in case table is sorted
        int modelRow = view.CatalogueTable.convertRowIndexToModel(row);
        String productId = (String) view.CatalogueTable.getModel().getValueAt(modelRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Soft-delete product " + productId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
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

    /** Add merchant from the addMerchants tab */
    public void addMerchant() {
        String username  = view.merchant_usernameField.getText().trim();
        String password  = view.merchant_passwordField.getText().trim();
        String accHolder = view.accHolderNameField.getText().trim();
        String contact   = view.contactNameField.getText().trim();
        String address   = view.addressField.getText().trim();
        String phone     = view.creditLimitField.getText().trim(); // field is labelled Phone Number
        String discRate  = view.discountRateField.getText().trim();
        String discPlan  = (String) view.discountPlanBox.getSelectedItem();

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
        newMerchant.setCreditLimit(new BigDecimal("1000.00")); // default credit limit

        int id = userDAO.createUser(newMerchant);
        if (id > 0) {
            JOptionPane.showMessageDialog(view, "Merchant created with ID: " + id);
            loadMerchantsTable();
            loadDashboardStats();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to create merchant. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Remove selected merchant */
    public void removeSelectedMerchant() {
        int row = view.CatalogueTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Please select a merchant to remove.");
            return;
        }
        int modelRow = view.CatalogueTable1.convertRowIndexToModel(row);
        String accountNo = (String) view.CatalogueTable1.getModel().getValueAt(modelRow, 0);

        // Find merchant by account number
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

        int confirm = JOptionPane.showConfirmDialog(view,
                "Delete merchant account " + accountNo + "? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userDAO.deleteMerchant(merchantId);
            loadMerchantsTable();
            loadDashboardStats();
        }
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
                    "••••••••",  // never show plain text password
                    u.getRole() != null ? u.getRole().name() : "-"
            });
        }
    }

    /** Add a non-merchant staff user */
    public void addStaffUser() {
        String username = view.jTextField17.getText().trim();
        String password = view.jTextField18.getText().trim();
        String roleStr  = (String) view.jComboBox2.getSelectedItem();

        if (username.isBlank() || password.isBlank() || "role...".equals(roleStr)) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields and select a role.");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("admin".equals(roleStr) ? UserRole.ADMIN : UserRole.DIRECTOR_OF_OPERATIONS);
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

    /** Remove selected staff user */
    public void removeSelectedUser() {
        int row = view.jTable3.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Please select a user to remove.");
            return;
        }
        // Simple delete by username
        int modelRow = view.jTable3.convertRowIndexToModel(row);
        String username = (String) view.jTable3.getModel().getValueAt(modelRow, 1);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Remove user '" + username + "'?", "Confirm", JOptionPane.YES_NO_OPTION);
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
    // ORDERS TABLE (incomplete orders panel)
    // ─────────────────────────────────────────────────────────────

    public void loadOrdersTable() {
        // The Orders tab currently just has a label — we add a table dynamically
        List<Order> incompleteOrders = orderDAO.getIncompleteOrders();
        // Update pending orders count on dashboard home too
        view.pendingOrders.setText(String.valueOf(incompleteOrders.size()));
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

    /** Approve selected application */
    public void approveSelectedApplication() {
        int row = view.jTable1.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(view, "Select an application first."); return; }
        int modelRow = view.jTable1.convertRowIndexToModel(row);
        String appId = (String) view.jTable1.getModel().getValueAt(modelRow, 0);
        updateApplicationStatus(appId, "Approved");
    }

    /** Reject selected application */
    public void rejectSelectedApplication() {
        int row = view.jTable1.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(view, "Select an application first."); return; }
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
    // WIRE ALL BUTTON ACTIONS
    // ─────────────────────────────────────────────────────────────

    private void wireButtons() {

        // Catalogue tab — search field
        view.searchField.addActionListener(e -> searchCatalogue(view.searchField.getText()));
        view.searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { searchCatalogue(view.searchField.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { searchCatalogue(view.searchField.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchCatalogue(view.searchField.getText()); }
        });

        // Catalogue tab — add & remove buttons
        view.addItemButton.addActionListener(e -> addCatalogueItem());
        view.rmvSelectedButton.addActionListener(e -> removeSelectedCatalogueItem());

        // Merchants tab — add & remove buttons
        view.addMerchantButton.addActionListener(e -> addMerchant());
        view.jButton16.addActionListener(e -> removeSelectedMerchant());

        // Users tab — add & remove buttons
        view.jButton14.addActionListener(e -> addStaffUser());
        view.jButton15.addActionListener(e -> removeSelectedUser());

        // Applications tab — approve & reject
        view.jToggleButton5.addActionListener(e -> approveSelectedApplication());
        view.jToggleButton6.addActionListener(e -> rejectSelectedApplication());

        // Reports tab — each toggle button loads the relevant report text
        view.systemTurnoverReport.addActionListener(e -> loadSystemTurnoverReport());
        view.merchantOrdersSummary.addActionListener(e -> loadMerchantOrdersSummaryPrompt());
        view.individualMerchantActvityButton.addActionListener(e -> loadIndividualMerchantActivityPrompt());
        view.stockTurnoverReport.addActionListener(e -> loadStockTurnoverReport());
        view.debtorReminders.addActionListener(e -> loadDebtorRemindersReport());

        // Reports tab — generate PDF placeholder
        view.generatePDFButton.addActionListener(e ->
                JOptionPane.showMessageDialog(view, "PDF export: copy the text above into a document or use a library like iText."));
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
        if (input == null) return; // cancelled

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
            // Merchant header
            java.sql.PreparedStatement psMerchant = conn.prepareStatement(
                    "SELECT * FROM Users WHERE AccountNo = ?");
            psMerchant.setString(1, accountNo);
            java.sql.ResultSet rsMerchant = psMerchant.executeQuery();
            if (!rsMerchant.next()) { view.reportTextPane.setText("Merchant not found."); return; }

            sb.append("Name:    ").append(rsMerchant.getString("AccountHolderName")).append("\n");
            sb.append("Contact: ").append(rsMerchant.getString("ContactName")).append("\n");
            sb.append("Address: ").append(rsMerchant.getString("Address")).append("\n");
            sb.append("Balance: £").append(rsMerchant.getBigDecimal("OutstandingBalance")).append("\n\n");

            int merchantId = rsMerchant.getInt("UserID");

            // Orders + items
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