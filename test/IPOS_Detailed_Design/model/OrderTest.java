package IPOS_Detailed_Design.model;

import IPOS_Detailed_Design.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void defaultConstructor_createsEmptyOrder() {
        Order order = new Order();
        assertNull(order.getOrderId());
        assertNull(order.getStatus());
    }

    @Test
    void setOrderId_updatesOrderId() {
        Order order = new Order();
        order.setOrderId("ORD-1");
        assertEquals("ORD-1", order.getOrderId());
    }

    @Test
    void setMerchantId_updatesMerchantId() {
        Order order = new Order();
        order.setMerchantId(1);
        assertEquals(1, order.getMerchantId());
    }

    @Test
    void setTotalAmount_updatesTotalAmount() {
        Order order = new Order();
        order.setTotalAmount(new BigDecimal("49.99"));
        assertEquals(new BigDecimal("49.99"), order.getTotalAmount());
    }

    @Test
    void setStatus_updatesStatus() {
        Order order = new Order();
        order.setStatus(OrderStatus.ACCEPTED);
        assertEquals(OrderStatus.ACCEPTED, order.getStatus());
    }

    @Test
    void setOrderDate_updatesOrderDate() {
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();
        order.setOrderDate(now);
        assertEquals(now, order.getOrderDate());
    }

    @Test
    void setEstimatedDelivery_updatesEstimatedDelivery() {
        Order order = new Order();
        LocalDateTime delivery = LocalDateTime.now().plusDays(2);
        order.setEstimatedDelivery(delivery);
        assertEquals(delivery, order.getEstimatedDelivery());
    }

    @Test
    void setDispatchDetails_updatesDispatchDetails() {
        Order order = new Order();
        order.setDispatchDetails("Pending dispatch");
        assertEquals("Pending dispatch", order.getDispatchDetails());
    }

    @Test
    void setItems_updatesItems() {
        Order order = new Order();
        OrderItem item = new OrderItem("P001", 2);
        order.setItems(List.of(item));
        assertEquals(1, order.getItems().size());
    }

    @Test
    void defaultConstructor_itemsListIsEmpty() {
        Order order = new Order();
        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }
}
