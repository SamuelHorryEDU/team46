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
 * OrderDAO — Data Access Object for the Orders and OrderItems tables.
 *
 * <p>Provides all CRUD operations for orders placed by merchants
 * in the IPOS-SA system. Handles order creation, status updates,
 * order item insertion and retrieval of order history.</p>
 *
 */
public class OrderDAO {

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    /**
     * Saves a new order and all its line items to the database.
     *
     * <p>Inserts the order header into the Orders table, then inserts
     * each associated OrderItem into the OrderItems table.
     * Stock deduction should be handled separately via ProductDAO.</p>
     *
     * @param order the Order object to persist, including its list of items
     * @return {@code true} if the order was saved successfully,
     *         {@code false} otherwise
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

    /**
     * Inserts a single order line item into the OrderItems table.
     *
     * <p>Called internally by {@link #createOrder(Order)} for each
     * item in the order.</p>
     *
     * @param item    the OrderItem to insert
     * @param orderId the parent order ID this item belongs to
     */
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

    /**
     * Retrieves a single order by its unique order ID, including all line items.
     *
     * @param orderId the unique order identifier (e.g. "ORD-2026-0001")
     * @return the matching {@link Order} with items populated,
     *         or {@code null} if not found
     */
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
     * Retrieves all orders placed by a specific merchant, most recent first.
     *
     * @param merchantId the UserID of the merchant
     * @return list of {@link Order} objects for the merchant,
     *         empty list if none found
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
     * Retrieves orders for a merchant within a specified date range.
     *
     * <p>Used to generate the merchant orders summary report
     * (UC-SA-RPT-02).</p>
     *
     * @param merchantId the UserID of the merchant
     * @param fromDate   the start date of the range (inclusive)
     * @param toDate     the end date of the range (inclusive)
     * @return list of {@link Order} objects within the date range,
     *         empty list if none found
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
     * Retrieves all orders that have not yet been delivered or cancelled.
     *
     * <p>Powers the incomplete orders view on the dashboard,
     * satisfying the requirement to observe orders taken but not completed.</p>
     *
     * @return list of active {@link Order} objects ordered by date ascending,
     *         empty list if none found
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
     * Retrieves all orders regardless of status, most recent first.
     *
     * <p>Used by the "Show All" toggle in the orders tab to display
     * the complete order history including delivered and cancelled orders.</p>
     *
     * @return list of all {@link Order} objects, empty list if none found
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
     * Counts all orders that are not yet delivered or cancelled.
     *
     * <p>Used to populate the "Pending Orders" counter on the
     * dashboard home panel.</p>
     *
     * @return the number of pending/active orders, or 0 if none
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
     * Retrieves the current status of a single order.
     *
     * @param orderId the unique order identifier
     * @return the {@link OrderStatus} of the order, or {@code null} if not found
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

    /**
     * Retrieves all line items associated with a specific order.
     *
     * <p>Called internally when building a full Order object with items,
     * and used in report generation to display itemised order details.</p>
     *
     * @param orderId the unique order identifier
     * @return list of {@link OrderItem} objects for the order,
     *         empty list if none found
     */
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
     * Updates the status of an existing order and optionally records dispatch details.
     *
     * <p>Used by warehouse staff and delivery staff to progress an order
     * through the workflow: ACCEPTED → PROCESSING → PACKED →
     * DISPATCHED → DELIVERED. Dispatch details (courier name, reference
     * number, estimated delivery time) are recorded when status is
     * set to DISPATCHED.</p>
     *
     * @param orderId         the unique order identifier to update
     * @param status          the new {@link OrderStatus} to apply
     * @param dispatchDetails optional dispatch information such as courier
     *                        name and reference number; may be {@code null}
     * @return {@code true} if the update was successful, {@code false} otherwise
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
    // HELPER
    // ─────────────────────────────────────────────

    /**
     * Maps a single database result set row to an {@link Order} object.
     *
     * <p>Called internally by all read methods to convert raw SQL
     * results into typed Order objects. Does not populate the items
     * list — callers must call {@link #getItemsForOrder(String)}
     * separately if needed.</p>
     *
     * @param rs the {@link ResultSet} positioned at the row to map
     * @return a populated {@link Order} object
     * @throws SQLException if a database access error occurs
     */
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