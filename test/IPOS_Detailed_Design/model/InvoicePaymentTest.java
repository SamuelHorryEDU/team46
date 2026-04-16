package IPOS_Detailed_Design.model;

import IPOS_Detailed_Design.model.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class InvoicePaymentTest {

    @Test
    void defaultConstructor_createsEmptyInvoice() {
        InvoicePayment invoice = new InvoicePayment();
        assertNull(invoice.getOrderId());
        assertNull(invoice.getPaymentStatus());
    }

    @Test
    void setInvoiceId_updatesInvoiceId() {
        InvoicePayment invoice = new InvoicePayment();
        invoice.setInvoiceId(1);
        assertEquals(1, invoice.getInvoiceId());
    }

    @Test
    void setOrderId_updatesOrderId() {
        InvoicePayment invoice = new InvoicePayment();
        invoice.setOrderId("ORD-1");
        assertEquals("ORD-1", invoice.getOrderId());
    }

    @Test
    void setIssueDate_updatesIssueDate() {
        InvoicePayment invoice = new InvoicePayment();
        LocalDate today = LocalDate.now();
        invoice.setIssueDate(today);
        assertEquals(today, invoice.getIssueDate());
    }

    @Test
    void setDueDate_updatesDueDate() {
        InvoicePayment invoice = new InvoicePayment();
        LocalDate due = LocalDate.now().plusDays(30);
        invoice.setDueDate(due);
        assertEquals(due, invoice.getDueDate());
    }

    @Test
    void setPaymentStatus_updatesPaymentStatus() {
        InvoicePayment invoice = new InvoicePayment();
        invoice.setPaymentStatus(PaymentStatus.PAID);
        assertEquals(PaymentStatus.PAID, invoice.getPaymentStatus());
    }
}