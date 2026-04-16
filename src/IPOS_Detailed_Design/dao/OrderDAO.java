package IPOS_Detailed_Design.dao;

import IPOS_Detailed_Design.model.Order;
import IPOS_Detailed_Design.model.OrderItem;
import IPOS_Detailed_Design.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDAO — handles all database operations for Orders and OrderItems tables.
 *
 * Uses your exact field names from Order.java:
 *   getOrderId(), getMerchantId(), getStatus(), getItems() etc.
 */
public class OrderDAO {

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    /**
     * Save a new order and all its items to the database.
     * Also deducts stock for each item automatically.
     * Call this from OrderService.placeOrder() instead of just adding to the list.
     */
    public boolean createOrder(Order order) {
        String sql = "INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, " +
                "OrderStatus, EstimatedDelivery, DispatchDetails) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,    order.getOrderId());
            ps.setInt(2,       order.getMerchantId());
            ps.setTimestamp(3, order.getOrderDate() != null
                    ? Timestamp.valueOf(order.getOrderDate())
                    : Timestamp.valueOf(LocalDateTime.now()));
            ps.setBigDecimal(4, order.getTotalAmount());
            ps.setString(5,    order.getStatus() != null ? order.getStatus().name() : "ACCEPTED");
            ps.setTimestamp(6, order.getEstimatedDelivery() != null
                    ? Timestamp.valueOf(order.getEstimatedDelivery()) : null);
            ps.setString(7,    order.getDispatchDetails());

            boolean ok = ps.executeUpdate() > 0;
            if (ok && order.getItems() != null) {
                for (OrderItem item : order.getItems()) {
                    insertOrderItem(item, order.getOrderId());
                }
            }
            return ok;

        } catch (SQLException e) {
            System.err.println("OrderDAO.createOrder error: " + e.getMessage());
            return false;
        }
    }

    private void insertOrderItem(OrderItem item, String orderId) {
        String sql = "INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,    orderId);
            ps.setString(2,    item.getProductId());
            ps.setInt(3,       item.getQuantity());
            ps.setBigDecimal(4, item.getUnitCost());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("OrderDAO.insertOrderItem error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // READ
    // ─────────────────────────────────────────────

    public Order getOrderById(String orderId) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = mapOrder(rs);
                order.setItems(getItemsForOrder(orderId));
                return order;
            }
        } catch (SQLException e) {
            System.err.println("OrderDAO.getOrderById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all orders for a merchant.
     * Used in the merchant tracking view and balance calculations.
     */
    public List<Order> getOrdersByMerchant(int merchantId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE MerchantID = ? ORDER BY OrderDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = mapOrder(rs);
                o.setItems(getItemsForOrder(o.getOrderId()));
                orders.add(o);
            }
        } catch (SQLException e) {
            System.err.println("OrderDAO.getOrdersByMerchant error: " + e.getMessage());
        }
        return orders;
    }

    /**
     * Get orders for a merchant within a date range.
     * Matches OrderService.getOrderHistory(LocalDate from, LocalDate to).
     * Used for UC-SA-RPT-02 merchant orders report.
     */
    public List<Order> getOrderHistory(int merchantId, LocalDate fromDate, LocalDate toDate) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE MerchantID = ? " +
                "AND DATE(OrderDate) BETWEEN ? AND ? ORDER BY OrderDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ps.setDate(2, Date.valueOf(fromDate));
            ps.setDate(3, Date.valueOf(toDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = mapOrder(rs);
                o.setItems(getItemsForOrder(o.getOrderId()));
                orders.add(o);
            }
        } catch (SQLException e) {
            System.err.println("OrderDAO.getOrderHistory error: " + e.getMessage());
        }
        return orders;
    }

    /**
     * Get all orders not yet delivered or cancelled.
     * Powers the "orders not completed" view on dashboard — 1 mark on sheet.
     */
    public List<Order> getIncompleteOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE OrderStatus NOT IN ('DELIVERED','CANCELLED') " +
                "ORDER BY OrderDate ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) orders.add(mapOrder(rs));
        } catch (SQLException e) {
            System.err.println("OrderDAO.getIncompleteOrders error: " + e.getMessage());
        }
        return orders;
    }

    /**
     * Get all orders regardless of status — powers the "Show All" view.
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY OrderDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) orders.add(mapOrder(rs));
        } catch (SQLException e) {
            System.err.println("OrderDAO.getAllOrders error: " + e.getMessage());
        }
        return orders;
    }

    /**
     * Count pending orders — for the dashboard home panel pendingOrders label.
     */
    public int getPendingOrderCount() {
        String sql = "SELECT COUNT(*) FROM Orders WHERE OrderStatus NOT IN ('DELIVERED','CANCELLED')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("OrderDAO.getPendingOrderCount error: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get status of one order.
     * Matches OrderService.getOrderStatus(String orderId).
     */
    public OrderStatus getOrderStatus(String orderId) {
        String sql = "SELECT OrderStatus FROM Orders WHERE OrderID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try { return OrderStatus.valueOf(rs.getString("OrderStatus")); }
                catch (IllegalArgumentException e) { return OrderStatus.PROCESSING; }
            }
        } catch (SQLException e) {
            System.err.println("OrderDAO.getOrderStatus error: " + e.getMessage());
        }
        return null;
    }

    public List<OrderItem> getItemsForOrder(String orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems WHERE OrderID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderID(rs.getString("OrderID"));
                item.setProductId(rs.getString("ProductID"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setUnitCost(rs.getBigDecimal("UnitCost"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("OrderDAO.getItemsForOrder error: " + e.getMessage());
        }
        return items;
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────

    /**
     * Update order status and optionally save dispatch details.
     * UC-SA-ORD-04 — Warehouse staff updates order status.
     */
    public boolean updateOrderStatus(String orderId, OrderStatus status, String dispatchDetails) {
        String sql = "UPDATE Orders SET OrderStatus = ?, DispatchDetails = ? WHERE OrderID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setString(2, dispatchDetails);
            ps.setString(3, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("OrderDAO.updateOrderStatus error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // HELPER — map DB row to your Order object
    // ─────────────────────────────────────────────

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getString("OrderID"));
        o.setMerchantId(rs.getInt("MerchantID"));
        Timestamp ts = rs.getTimestamp("OrderDate");
        if (ts != null) o.setOrderDate(ts.toLocalDateTime());
        o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
        try { o.setStatus(OrderStatus.valueOf(rs.getString("OrderStatus"))); }
        catch (IllegalArgumentException e) { o.setStatus(OrderStatus.ACCEPTED); }
        Timestamp ed = rs.getTimestamp("EstimatedDelivery");
        if (ed != null) o.setEstimatedDelivery(ed.toLocalDateTime());
        o.setDispatchDetails(rs.getString("DispatchDetails"));
        return o;
    }
}