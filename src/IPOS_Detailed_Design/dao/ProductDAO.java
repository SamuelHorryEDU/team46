package IPOS_Detailed_Design.dao;

import IPOS_Detailed_Design.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO — handles all database operations for the Catalogue table.
 *
 * Replaces the in-memory List<Product> catalogue in OrderService with real MySQL calls.
 * Uses your exact Product field names: getProductId(), getAvailability(), getPackageCost() etc.
 */
public class ProductDAO {

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    public boolean addProduct(Product p) {
        String sql = "INSERT INTO Catalogue (ProductID, Name, Description, Category, PackageType, " +
                "Unit, UnitsInPack, PackageCost, Availability, StockLimit, IsActive) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,   p.getProductId());
            ps.setString(2,   p.getName());
            ps.setString(3,   p.getDescription());
            ps.setString(4,   p.getCategory());
            ps.setString(5,   p.getPackageType());
            ps.setString(6,   p.getUnit());
            ps.setInt(7,      p.getUnitsInPack());
            ps.setBigDecimal(8, p.getPackageCost());
            ps.setInt(9,      p.getAvailability());
            ps.setInt(10,     p.getStockLimit());
            ps.setBoolean(11, p.isActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ProductDAO.addProduct error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates the next available ProductID in the format "100-NNNNN",
     * incrementing from the highest numeric suffix currently in the Catalogue table.
     * Falls back to "100-00001" if the table is empty or no matching IDs exist.
     */
    public String getNextProductId() {
        String sql = "SELECT MAX(CAST(SUBSTRING_INDEX(ProductID, '-', -1) AS UNSIGNED)) FROM Catalogue";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getObject(1) != null) {
                int next = rs.getInt(1) + 1;
                return String.format("100-%05d", next);
            }
        } catch (SQLException e) {
            System.err.println("ProductDAO.getNextProductId error: " + e.getMessage());
        }
        return "100-00001";
    }

    // ─────────────────────────────────────────────
    // READ
    // ─────────────────────────────────────────────

    public Product getProductById(String productId) {
        String sql = "SELECT * FROM Catalogue WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("ProductDAO.getProductById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all active products — shown to merchants browsing the catalogue.
     * Replaces OrderService.getCatalogue() which returned a hardcoded list.
     */
    public List<Product> getAllActiveProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Catalogue WHERE IsActive = TRUE ORDER BY Name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("ProductDAO.getAllActiveProducts error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Search catalogue by product ID, name, description, or category.
     * Powers the search field in the Catalogue tab — UC-SA-CAT-02 (5 marks).
     */
    public List<Product> searchProducts(String keyword) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM Catalogue WHERE IsActive = TRUE AND " +
                "(ProductID LIKE ? OR Name LIKE ? OR Description LIKE ? OR Category LIKE ?)";
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
            System.err.println("ProductDAO.searchProducts error: " + e.getMessage());
        }
        return results;
    }

    /**
     * Get all products below their stock limit.
     * UC-SA-CAT-04 (automatic low stock alert on login) and UC-SA-CAT-05 (report).
     */
    public List<Product> getLowStockProducts() {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM Catalogue WHERE IsActive = TRUE AND Availability < StockLimit ORDER BY Name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) results.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("ProductDAO.getLowStockProducts error: " + e.getMessage());
        }
        return results;
    }

    /**
     * Check stock for one product — matches OrderService.checkStock(String itemId).
     */
    public int checkStock(String productId) {
        String sql = "SELECT Availability FROM Catalogue WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("Availability");
        } catch (SQLException e) {
            System.err.println("ProductDAO.checkStock error: " + e.getMessage());
        }
        return 0;
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────

    public boolean updateProduct(Product p) {
        String sql = "UPDATE Catalogue SET Name=?, Description=?, Category=?, PackageType=?, " +
                "Unit=?, UnitsInPack=?, PackageCost=?, Availability=?, StockLimit=?, IsActive=? " +
                "WHERE ProductID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,   p.getName());
            ps.setString(2,   p.getDescription());
            ps.setString(3,   p.getCategory());
            ps.setString(4,   p.getPackageType());
            ps.setString(5,   p.getUnit());
            ps.setInt(6,      p.getUnitsInPack());
            ps.setBigDecimal(7, p.getPackageCost());
            ps.setInt(8,      p.getAvailability());
            ps.setInt(9,      p.getStockLimit());
            ps.setBoolean(10, p.isActive());
            ps.setString(11,  p.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ProductDAO.updateProduct error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Add stock when warehouse receives new delivery.
     * UC-SA-CAT-03 — Add New Stock.
     */
    public boolean addStock(String productId, int quantity) {
        String sql = "UPDATE Catalogue SET Availability = Availability + ? WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ProductDAO.addStock error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deduct stock when an order is placed.
     * Returns false if there is not enough stock — order should be rejected.
     */
    public boolean deductStock(String productId, int quantity) {
        // The WHERE clause checks Availability >= quantity so we never go negative
        String sql = "UPDATE Catalogue SET Availability = Availability - ? " +
                "WHERE ProductID = ? AND Availability >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, productId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0; // returns false = insufficient stock
        } catch (SQLException e) {
            System.err.println("ProductDAO.deductStock error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // DELETE (soft delete — sets IsActive = false)
    // ─────────────────────────────────────────────

    public boolean deleteProduct(String productId) {
        String sql = "UPDATE Catalogue SET IsActive = FALSE WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ProductDAO.deleteProduct error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // HELPER — map DB row to your Product object
    // ─────────────────────────────────────────────

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getString("ProductID"));
        p.setName(rs.getString("Name"));
        p.setDescription(rs.getString("Description"));
        p.setCategory(rs.getString("Category"));
        p.setPackageType(rs.getString("PackageType"));
        p.setUnit(rs.getString("Unit"));
        p.setUnitsInPack(rs.getInt("UnitsInPack"));
        p.setPackageCost(rs.getBigDecimal("PackageCost"));
        p.setAvailability(rs.getInt("Availability"));
        p.setStockLimit(rs.getInt("StockLimit"));
        p.setActive(rs.getBoolean("IsActive"));
        return p;
    }
}