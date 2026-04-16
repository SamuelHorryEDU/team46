package IPOS_Detailed_Design.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// ═══════════════════════════════════════════════════════════
// PaymentDAO — Payments table
// Records payments made by merchants (entered by accountant)
// ═══════════════════════════════════════════════════════════
public class PaymentDAO {

    /**
     * Record a payment from a merchant.
     * UC-SA-ORD-07 — Accounting staff records payment details.
     * After calling this, also call UserDAO.reduceBalance() to update the merchant's balance.
     */
    public boolean recordPayment(int merchantId, BigDecimal amount, String method, String reference) {
        String sql = "INSERT INTO Payments (MerchantID, PaymentDate, AmountPaid, PaymentMethod, ReferenceNumber) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,       merchantId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBigDecimal(3, amount);
            ps.setString(4,    method);   // e.g. "Bank Transfer", "Cheque"
            ps.setString(5,    reference);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("PaymentDAO.recordPayment error: " + e.getMessage());
            return false;
        }
    }

    /** Get all payments for a merchant — shown in their payment history. */
    public List<PaymentRecord> getPaymentsByMerchant(int merchantId) {
        List<PaymentRecord> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE MerchantID = ? ORDER BY PaymentDate DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, merchantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaymentRecord p = new PaymentRecord();
                p.paymentId      = rs.getInt("PaymentID");
                p.merchantId     = rs.getInt("MerchantID");
                p.amountPaid     = rs.getBigDecimal("AmountPaid");
                p.paymentMethod  = rs.getString("PaymentMethod");
                p.referenceNumber= rs.getString("ReferenceNumber");
                Timestamp ts     = rs.getTimestamp("PaymentDate");
                if (ts != null) p.paymentDate = ts.toLocalDateTime();
                payments.add(p);
            }
        } catch (SQLException e) {
            System.err.println("PaymentDAO.getPaymentsByMerchant error: " + e.getMessage());
        }
        return payments;
    }

    public static class PaymentRecord {
        public int           paymentId;
        public int           merchantId;
        public BigDecimal    amountPaid;
        public String        paymentMethod;
        public String        referenceNumber;
        public LocalDateTime paymentDate;
    }
}
