package IPOS_Detailed_Design.dao;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.AccountStatus;
import IPOS_Detailed_Design.model.enums.DiscountPlanType;
import IPOS_Detailed_Design.model.enums.UserRole;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO — handles all database operations for the Users table.
 *
 * This replaces the in-memory List<User> in AccountService with real MySQL calls.
 * The method names match what AccountService already uses so swapping is easy.
 */
public class UserDAO {

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    /**
     * Insert a new user into the Users table.
     * Returns the generated UserID, or -1 if it failed.
     */
    public int createUser(User user) {
        String sql = "INSERT INTO Users (Username, Password, Role, AccountNo, AccountHolderName, " +
                "ContactName, Address, Phone, CreditLimit, DiscountPlan, DiscountRate, " +
                "AccountStatus, OutstandingBalance) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1,  user.getUsername());
            ps.setString(2,  user.getPassword());
            ps.setString(3,  roleToDb(user.getRole()));
            ps.setString(4,  user.getAccountNo());
            ps.setString(5,  user.getAccountHolderName());
            ps.setString(6,  user.getContactName());
            ps.setString(7,  user.getAddress());
            ps.setString(8,  user.getPhone());
            ps.setBigDecimal(9,  user.getCreditLimit());
            ps.setString(10, user.getDiscountPlan() != null ? user.getDiscountPlan().name() : null);
            ps.setString(11, user.getDiscountRate());
            ps.setString(12, statusToDb(user.getAccountStatus()));
            ps.setBigDecimal(13, user.getOutstandingBalance() != null
                    ? user.getOutstandingBalance() : BigDecimal.ZERO);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("UserDAO.createUser error: " + e.getMessage());
        }
        return -1;
    }

    // ─────────────────────────────────────────────
    // READ
    // ─────────────────────────────────────────────

    /**
     * Get a user by their UserID.
     * Matches AccountService.getUserById(int userId).
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("UserDAO.getUserById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get a user by their username.
     * Matches AccountService.getUserByUsername(String username).
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("UserDAO.getUserByUsername error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Authenticate a user — returns the User object if credentials match, null if not.
     * Replaces the loop in AccountService.authenticateUser().
     */
    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("UserDAO.authenticateUser error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all users.
     * Matches AccountService.getAllUsers().
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY Role, Username";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) users.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("UserDAO.getAllUsers error: " + e.getMessage());
        }
        return users;
    }

    /**
     * Get all merchants only.
     * Matches AccountService.getAllMerchants().
     */
    public List<User> getAllMerchants() {
        List<User> merchants = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Role = 'Merchant' ORDER BY AccountHolderName";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) merchants.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("UserDAO.getAllMerchants error: " + e.getMessage());
        }
        return merchants;
    }

    /**
     * Search merchants by name, account number, contact name, or username.
     * Powers the search bar in the Dashboard — worth 5 marks on the marking sheet.
     */
    public List<User> searchMerchants(String keyword) {
        List<User> results = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Role = 'Merchant' AND (" +
                "AccountHolderName LIKE ? OR AccountNo LIKE ? OR " +
                "ContactName LIKE ? OR Username LIKE ?)";
        String like = "%" + keyword + "%";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) results.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("UserDAO.searchMerchants error: " + e.getMessage());
        }
        return results;
    }

    /**
     * Count total number of users — for the dashboard home panel totalUsers label.
     */
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("UserDAO.getTotalUserCount error: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get outstanding balance for a merchant.
     * Matches AccountService.getOutstandingBalance(int merchantId).
     */
    public BigDecimal getOutstandingBalance(int merchantId) {
        String sql = "SELECT OutstandingBalance FROM Users WHERE UserID = ? AND Role = 'Merchant'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getBigDecimal("OutstandingBalance");
        } catch (SQLException e) {
            System.err.println("UserDAO.getOutstandingBalance error: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    // ─────────────────────────────────────────────
    // UPDATE — account status
    // These match AccountService.suspendMerchant() etc.
    // ─────────────────────────────────────────────

    /** UC-SA-ACC-05a: Suspend merchant (15-30 days overdue). */
    public boolean suspendMerchant(int merchantId) {
        return setAccountStatus(merchantId, AccountStatus.SUSPENDED);
    }

    /** UC-SA-ACC-05b: Flag as in default (>30 days overdue). */
    public boolean markMerchantInDefault(int merchantId) {
        return setAccountStatus(merchantId, AccountStatus.IN_DEFAULT);
    }

    /** UC-SA-ACC-05c: Restore suspended account to normal after payment. */
    public boolean restoreMerchantToNormal(int merchantId) {
        return setAccountStatus(merchantId, AccountStatus.NORMAL);
    }

    /** UC-SA-ACC-05d: Manual status change by Director of Operations. */
    public boolean setAccountStatus(int merchantId, AccountStatus status) {
        String sql = "UPDATE Users SET AccountStatus = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statusToDb(status));
            ps.setInt(2, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.setAccountStatus error: " + e.getMessage());
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // UPDATE — balance
    // ─────────────────────────────────────────────

    /**
     * Update outstanding balance.
     * Matches AccountService.updateOutstandingBalance().
     */
    public boolean updateOutstandingBalance(int merchantId, BigDecimal newBalance) {
        String sql = "UPDATE Users SET OutstandingBalance = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setInt(2, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.updateOutstandingBalance error: " + e.getMessage());
        }
        return false;
    }

    /** Add to balance when order is placed. */
    public boolean increaseBalance(int merchantId, BigDecimal amount) {
        String sql = "UPDATE Users SET OutstandingBalance = OutstandingBalance + ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, amount);
            ps.setInt(2, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.increaseBalance error: " + e.getMessage());
        }
        return false;
    }

    /** Reduce balance when payment recorded. Cannot go below zero. */
    public boolean reduceBalance(int merchantId, BigDecimal amount) {
        String sql = "UPDATE Users SET OutstandingBalance = GREATEST(0, OutstandingBalance - ?) WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, amount);
            ps.setInt(2, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.reduceBalance error: " + e.getMessage());
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // UPDATE — credit limit and discount
    // ─────────────────────────────────────────────

    public boolean updateCreditLimit(int merchantId, BigDecimal newLimit) {
        String sql = "UPDATE Users SET CreditLimit = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newLimit);
            ps.setInt(2, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.updateCreditLimit error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateDiscountPlan(int merchantId, DiscountPlanType plan, String rate) {
        String sql = "UPDATE Users SET DiscountPlan = ?, DiscountRate = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plan.name());
            ps.setString(2, rate);
            ps.setInt(3, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.updateDiscountPlan error: " + e.getMessage());
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────

    /**
     * Delete a merchant. Their orders/invoices/payments cascade delete
     * if you add ON DELETE CASCADE to the schema foreign keys.
     */
    public boolean deleteMerchant(int merchantId) {
        String sql = "DELETE FROM Users WHERE UserID = ? AND Role = 'Merchant'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserDAO.deleteMerchant error: " + e.getMessage());
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // HELPERS — map DB row to your User object
    // ─────────────────────────────────────────────

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setRole(roleFromDb(rs.getString("Role")));
        user.setAccountNo(rs.getString("AccountNo"));
        user.setAccountHolderName(rs.getString("AccountHolderName"));
        user.setContactName(rs.getString("ContactName"));
        user.setAddress(rs.getString("Address"));
        user.setPhone(rs.getString("Phone"));
        user.setCreditLimit(rs.getBigDecimal("CreditLimit"));

        String dp = rs.getString("DiscountPlan");
        if (dp != null) {
            try { user.setDiscountPlan(DiscountPlanType.valueOf(dp)); }
            catch (IllegalArgumentException ignored) {}
        }

        user.setDiscountRate(rs.getString("DiscountRate"));
        user.setAccountStatus(statusFromDb(rs.getString("AccountStatus")));
        user.setOutstandingBalance(rs.getBigDecimal("OutstandingBalance"));
        return user;
    }

    // DB stores "Merchant", "Admin" etc — map to your UserRole enum
    private UserRole roleFromDb(String dbValue) {
        if (dbValue == null) return UserRole.MERCHANT;
        return switch (dbValue) {
            case "Admin"                        -> UserRole.ADMIN;
            case "Director of Operations"       -> UserRole.DIRECTOR_OF_OPERATIONS;
            case "Senior accountant"            -> UserRole.SENIOR_ACCOUNTANT;
            case "Accountant"                   -> UserRole.ACCOUNTANT;
            case "Warehouse employee"           -> UserRole.WAREHOUSE_EMPLOYEE;
            case "Delivery department employee" -> UserRole.DELIVERY_DEPARTMENT_EMPLOYEE;
            default                             -> UserRole.MERCHANT;
        };
    }

    private String roleToDb(UserRole role) {
        if (role == null) return "Merchant";
        return switch (role) {
            case ADMIN                        -> "Admin";
            case DIRECTOR_OF_OPERATIONS       -> "Director of Operations";
            case SENIOR_ACCOUNTANT            -> "Senior accountant";
            case ACCOUNTANT                   -> "Accountant";
            case WAREHOUSE_EMPLOYEE           -> "Warehouse employee";
            case DELIVERY_DEPARTMENT_EMPLOYEE -> "Delivery department employee";
            default                           -> "Merchant";
        };
    }

    // DB stores "Normal", "Suspended", "In_Default" — map to your AccountStatus enum
    private AccountStatus statusFromDb(String dbValue) {
        if (dbValue == null) return AccountStatus.NORMAL;
        return switch (dbValue) {
            case "Suspended" -> AccountStatus.SUSPENDED;
            case "In_Default" -> AccountStatus.IN_DEFAULT;
            default -> AccountStatus.NORMAL;
        };
    }

    private String statusToDb(AccountStatus status) {
        if (status == null) return "Normal";
        return switch (status) {
            case SUSPENDED -> "Suspended";
            case IN_DEFAULT -> "In_Default";
            default -> "Normal";
        };
    }
}