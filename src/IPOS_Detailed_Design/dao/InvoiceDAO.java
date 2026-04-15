package IPOS_Detailed_Design.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * InvoiceDAO — handles Invoices_Payments table.
 * An invoice is created automatically every time an order is placed.
 */
public class InvoiceDAO {

    public boolean createInvoice(String orderId) {
        // Invoice due date = end of current calendar month (as per the brief)
        LocalDate today    = LocalDate.now();
        LocalDate dueDate  = today.withDayOfMonth(today.lengthOfMonth());

        String sql = "INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus) " +
                "VALUES (?, ?, ?, 'Pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ps.setDate(2, Date.valueOf(today));
            ps.setDate(3, Date.valueOf(dueDate));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("InvoiceDAO.createInvoice error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get invoices for a specific merchant within a date range.
     * UC-SA-RPT-04 — Invoice list report per merchant.
     */
    public List<InvoiceRecord> getInvoicesByMerchant(int merchantId, LocalDate from, LocalDate to) {
        List<InvoiceRecord> invoices = new ArrayList<>();
        String sql = "SELECT ip.*, o.TotalAmount, o.MerchantID FROM Invoices_Payments ip " +
                "JOIN Orders o ON ip.OrderID = o.OrderID " +
                "WHERE o.MerchantID = ? AND ip.IssueDate BETWEEN ? AND ? " +
                "ORDER BY ip.IssueDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) invoices.add(mapInvoice(rs));
        } catch (SQLException e) {
            System.err.println("InvoiceDAO.getInvoicesByMerchant error: " + e.getMessage());
        }
        return invoices;
    }

    /**
     * Get all invoices for all merchants for a period.
     * UC-SA-RPT-05 — All invoices report.
     */
    public List<InvoiceRecord> getAllInvoicesForPeriod(LocalDate from, LocalDate to) {
        List<InvoiceRecord> invoices = new ArrayList<>();
        String sql = "SELECT ip.*, o.TotalAmount, o.MerchantID FROM Invoices_Payments ip " +
                "JOIN Orders o ON ip.OrderID = o.OrderID " +
                "WHERE ip.IssueDate BETWEEN ? AND ? ORDER BY ip.IssueDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) invoices.add(mapInvoice(rs));
        } catch (SQLException e) {
            System.err.println("InvoiceDAO.getAllInvoicesForPeriod error: " + e.getMessage());
        }
        return invoices;
    }

    /**
     * How many days overdue is a merchant's oldest unpaid invoice?
     * Used by AccountStatusService to decide NORMAL / SUSPENDED / IN_DEFAULT.
     */
    public long getMaxDaysOverdue(int merchantId) {
        String sql = "SELECT MAX(DATEDIFF(CURDATE(), ip.DueDate)) " +
                "FROM Invoices_Payments ip " +
                "JOIN Orders o ON ip.OrderID = o.OrderID " +
                "WHERE o.MerchantID = ? AND ip.PaymentStatus != 'Paid'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Math.max(0, rs.getLong(1));
        } catch (SQLException e) {
            System.err.println("InvoiceDAO.getMaxDaysOverdue error: " + e.getMessage());
        }
        return 0;
    }

    public boolean markAsPaid(int invoiceId) {
        String sql = "UPDATE Invoices_Payments SET PaymentStatus = 'Paid' WHERE InvoiceID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("InvoiceDAO.markAsPaid error: " + e.getMessage());
            return false;
        }
    }

    private InvoiceRecord mapInvoice(ResultSet rs) throws SQLException {
        InvoiceRecord inv = new InvoiceRecord();
        inv.invoiceId     = rs.getInt("InvoiceID");
        inv.orderId       = rs.getString("OrderID");
        inv.merchantId    = rs.getInt("MerchantID");
        inv.totalAmount   = rs.getBigDecimal("TotalAmount");
        Date id = rs.getDate("IssueDate");
        if (id != null) inv.issueDate = id.toLocalDate();
        Date dd = rs.getDate("DueDate");
        if (dd != null) inv.dueDate = dd.toLocalDate();
        inv.paymentStatus = rs.getString("PaymentStatus");
        return inv;
    }

    /** Simple data holder for invoice info shown in reports and tables. */
    public static class InvoiceRecord {
        public int        invoiceId;
        public String     orderId;
        public int        merchantId;
        public BigDecimal totalAmount;
        public LocalDate  issueDate;
        public LocalDate  dueDate;
        public String     paymentStatus;

        public long daysOverdue() {
            if ("Paid".equals(paymentStatus) || dueDate == null) return 0;
            return Math.max(0, java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now()));
        }
    }
}


