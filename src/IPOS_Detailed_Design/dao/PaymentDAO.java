package IPOS_Detailed_Design.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PaymentDAO — Data Access Object for the Payments table.
 *
 * <p>Records and retrieves payments made by merchants to InfoPharma Ltd.
 * In the IPOS-SA prototype, payments are entered manually by accounting
 * staff after receiving confirmation of a bank transfer, cheque or card
 * payment from the merchant.</p>
 *
 * <p>After recording a payment via {@link #recordPayment}, the caller
 * must also call {@link UserDAO#reduceBalance(int, BigDecimal)} to update
 * the merchant's outstanding balance, and optionally trigger an account
 * status check via
 * {@link IPOS_Detailed_Design.service.AccountStatusService}.</p>
 *
 * @author Team 46
 * @version 1.0
 */
public class PaymentDAO {

    /**
     * Records a payment received from a merchant into the Payments table.
     *
     * <p>Corresponds to UC-SA-ORD-07 — accounting staff manually enters
     * payment details after receiving funds from the merchant. The payment
     * date is automatically set to the current date and time.</p>
     *
     * <p>This method only inserts the payment record. The caller is
     * responsible for also updating the merchant's outstanding balance
     * by calling {@link UserDAO#reduceBalance(int, BigDecimal)}.</p>
     *
     * @param merchantId  the UserID of the merchant making the payment
     * @param amount      the amount paid in GBP, must be greater than zero
     * @param method      the payment method used, one of:
     *                    {@code "Bank Transfer"}, {@code "Credit Card"},
     *                    {@code "Cash"}, or {@code "Cheque"}
     * @param reference   an optional reference number for the payment,
     *                    such as a bank transaction ID or cheque number;
     *                    may be {@code null}
     * @return {@code true} if the payment was recorded successfully,
     *         {@code false} otherwise
     */
    public boolean recordPayment(int merchantId, BigDecimal amount,
                                 String method, String reference) {
        String sql = "INSERT INTO Payments (MerchantID, PaymentDate, AmountPaid, " +
                "PaymentMethod, ReferenceNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,        merchantId);
            ps.setTimestamp(2,  Timestamp.valueOf(LocalDateTime.now()));
            ps.setBigDecimal(3, amount);
            ps.setString(4,     method);
            ps.setString(5,     reference);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("PaymentDAO.recordPayment error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the full payment history for a specific merchant,
     * most recent payment first.
     *
     * <p>Used to populate the payment history view in the Merchant
     * Balance panel, allowing accounting staff and the Director of
     * Operations to verify payments received from a merchant.</p>
     *
     * @param merchantId the UserID of the merchant
     * @return list of {@link PaymentRecord} objects for the merchant,
     *         ordered by payment date descending;
     *         empty list if no payments found
     */
    public List<PaymentRecord> getPaymentsByMerchant(int merchantId) {
        List<PaymentRecord> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE MerchantID = ? " +
                "ORDER BY PaymentDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaymentRecord p = new PaymentRecord();
                p.paymentId       = rs.getInt("PaymentID");
                p.merchantId      = rs.getInt("MerchantID");
                p.amountPaid      = rs.getBigDecimal("AmountPaid");
                p.paymentMethod   = rs.getString("PaymentMethod");
                p.referenceNumber = rs.getString("ReferenceNumber");
                Timestamp ts      = rs.getTimestamp("PaymentDate");
                if (ts != null) p.paymentDate = ts.toLocalDateTime();
                payments.add(p);
            }
        } catch (SQLException e) {
            System.err.println("PaymentDAO.getPaymentsByMerchant error: " + e.getMessage());
        }
        return payments;
    }

    /**
     * Simple data transfer object holding payment information
     * for use in the merchant balance panel and payment history views.
     *
     * <p>Fields are public for direct access since this is a
     * read-only data holder used only within the DAO and
     * controller layers.</p>
     */
    public static class PaymentRecord {

        /** The unique payment ID from the database. */
        public int paymentId;

        /** The UserID of the merchant who made this payment. */
        public int merchantId;

        /** The amount paid in GBP. */
        public BigDecimal amountPaid;

        /**
         * The payment method used.
         * One of: {@code "Bank Transfer"}, {@code "Credit Card"},
         * {@code "Cash"}, {@code "Cheque"}.
         */
        public String paymentMethod;

        /**
         * An optional reference number for the payment,
         * such as a bank transaction ID or cheque number.
         * May be {@code null} if no reference was provided.
         */
        public String referenceNumber;

        /** The date and time the payment was recorded in the system. */
        public LocalDateTime paymentDate;
    }
}