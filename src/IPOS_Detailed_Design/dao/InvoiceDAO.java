package IPOS_Detailed_Design.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * InvoiceDAO — Data Access Object for the Invoices_Payments table.
 *
 * <p>Handles all database operations related to invoices raised against
 * merchant orders in the IPOS-SA system. An invoice is created automatically
 * each time a new order is accepted, with a due date set to the end of the
 * current calendar month in accordance with InfoPharma's credit terms.</p>
 *
 * <p>Also provides the overdue calculation used by {@link IPOS_Detailed_Design.service.AccountStatusService}
 * to automatically transition merchant accounts between NORMAL, SUSPENDED
 * and IN_DEFAULT states.</p>
 *
 * @author Team 46
 * @version 1.0
 */
public class InvoiceDAO {

    /**
     * Creates a new invoice for the specified order.
     *
     * <p>The invoice issue date is set to today, and the due date is set
     * to the last day of the current calendar month, in line with
     * InfoPharma's end-of-month payment terms. The initial payment
     * status is set to {@code Pending}.</p>
     *
     * <p>If an invoice already exists for this order the method returns
     * {@code false} without creating a duplicate.</p>
     *
     * @param orderId the unique order identifier to raise an invoice against
     *                (e.g. {@code "ORD-2026-0001"})
     * @return {@code true} if the invoice was created successfully,
     *         {@code false} if it already exists or an error occurred
     */
    public boolean createInvoice(String orderId) {
        // Check if invoice already exists for this order
        String checkSql = "SELECT COUNT(*) FROM Invoices_Payments WHERE OrderID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement check = conn.prepareStatement(checkSql)) {
            check.setString(1, orderId);
            java.sql.ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Invoice already exists for order: " + orderId);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("InvoiceDAO.createInvoice check error: " + e.getMessage());
            return false;
        }

        // Invoice due date = end of current calendar month (as per the brief)
        LocalDate today   = LocalDate.now();
        LocalDate dueDate = today.withDayOfMonth(today.lengthOfMonth());

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
     * Retrieves all invoices for a specific merchant within a date range.
     *
     * <p>Used to generate the invoice list report per merchant
     * (UC-SA-RPT-04), allowing staff to view all invoices raised
     * against a merchant for a given period.</p>
     *
     * @param merchantId the UserID of the merchant
     * @param from       the start date of the reporting period (inclusive)
     * @param to         the end date of the reporting period (inclusive)
     * @return list of {@link InvoiceRecord} objects for the merchant
     *         within the period, empty list if none found
     */
    public List<InvoiceRecord> getInvoicesByMerchant(int merchantId,
                                                     LocalDate from,
                                                     LocalDate to) {
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
     * Retrieves all invoices raised by InfoPharma within a date range.
     *
     * <p>Used to generate the full invoice report across all merchants
     * (UC-SA-RPT-05), giving the Director of Operations a complete
     * view of all outstanding and paid invoices for a period.</p>
     *
     * @param from the start date of the reporting period (inclusive)
     * @param to   the end date of the reporting period (inclusive)
     * @return list of {@link InvoiceRecord} objects for all merchants
     *         within the period, empty list if none found
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
     * Calculates the maximum number of days any unpaid invoice is overdue
     * for a given merchant.
     *
     * <p>This is the key method used by
     * {@link IPOS_Detailed_Design.service.AccountStatusService}
     * to automatically manage merchant account status transitions:</p>
     * <ul>
     *   <li>0 days overdue → account remains or returns to NORMAL</li>
     *   <li>1–15 days overdue → reminder generated, account stays NORMAL</li>
     *   <li>16–30 days overdue → account automatically SUSPENDED</li>
     *   <li>31+ days overdue → account flagged as IN_DEFAULT</li>
     * </ul>
     *
     * @param merchantId the UserID of the merchant to check
     * @return the number of days the oldest unpaid invoice is overdue,
     *         or {@code 0} if all invoices are paid or not yet due
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

    /**
     * Marks a specific invoice as paid.
     *
     * <p>Called by the accounting department after a merchant's
     * payment has been received and verified. Updating an invoice
     * to Paid may trigger an automatic account status restoration
     * via {@link IPOS_Detailed_Design.service.AccountStatusService}.</p>
     *
     * @param invoiceId the primary key of the invoice to mark as paid
     * @return {@code true} if the update was successful,
     *         {@code false} otherwise
     */
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

    /**
     * Maps a single database result set row to an {@link InvoiceRecord} object.
     *
     * <p>Called internally by all read methods to convert raw SQL
     * results into typed InvoiceRecord objects.</p>
     *
     * @param rs the {@link ResultSet} positioned at the row to map
     * @return a populated {@link InvoiceRecord} object
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Simple data transfer object holding invoice information
     * for use in reports and the merchant balance panel.
     *
     * <p>Fields are public for direct access since this is a
     * read-only data holder used only within the DAO and
     * controller layers.</p>
     */
    public static class InvoiceRecord {

        /** The unique invoice ID from the database. */
        public int invoiceId;

        /** The order ID this invoice was raised against. */
        public String orderId;

        /** The UserID of the merchant this invoice belongs to. */
        public int merchantId;

        /** The total monetary value of the invoice in GBP. */
        public BigDecimal totalAmount;

        /** The date the invoice was issued. */
        public LocalDate issueDate;

        /** The date by which payment is due (end of calendar month). */
        public LocalDate dueDate;

        /** The current payment status: Pending, Paid, or Overdue. */
        public String paymentStatus;

        /**
         * Calculates how many days overdue this invoice currently is.
         *
         * @return the number of days past the due date, or {@code 0}
         *         if the invoice is paid or not yet due
         */
        public long daysOverdue() {
            if ("Paid".equals(paymentStatus) || dueDate == null) return 0;
            return Math.max(0,
                    java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now()));
        }
    }
}