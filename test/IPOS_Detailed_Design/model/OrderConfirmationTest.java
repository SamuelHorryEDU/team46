package IPOS_Detailed_Design.model;

import IPOS_Detailed_Design.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class OrderConfirmationTest {

    @Test
    void defaultConstructor_createsEmptyConfirmation() {
        OrderConfirmation confirmation = new OrderConfirmation();
        assertNull(confirmation.getOrderId());
        assertNull(confirmation.getStatus());
    }

    @Test
    void setOrderId_updatesOrderId() {
        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.setOrderId("ORD-1");
        assertEquals("ORD-1", confirmation.getOrderId());
    }

    @Test
    void setTotalAmount_updatesTotalAmount() {
        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.setTotalAmount(new BigDecimal("49.99"));
        assertEquals(new BigDecimal("49.99"), confirmation.getTotalAmount());
    }

    @Test
    void setStatus_updatesStatus() {
        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.setStatus(OrderStatus.ACCEPTED);
        assertEquals(OrderStatus.ACCEPTED, confirmation.getStatus());
    }

    @Test
    void setOrderDateTime_updatesOrderDateTime() {
        OrderConfirmation confirmation = new OrderConfirmation();
        LocalDateTime now = LocalDateTime.now();
        confirmation.setOrderDateTime(now);
        assertEquals(now, confirmation.getOrderDateTime());
    }

    @Test
    void setEstimatedDelivery_updatesEstimatedDelivery() {
        OrderConfirmation confirmation = new OrderConfirmation();
        LocalDateTime delivery = LocalDateTime.now().plusDays(2);
        confirmation.setEstimatedDelivery(delivery);
        assertEquals(delivery, confirmation.getEstimatedDelivery());
    }
}