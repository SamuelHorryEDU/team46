package IPOS_Detailed_Design.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void defaultConstructor_createsEmptyOrderItem() {
        OrderItem item = new OrderItem();
        assertNull(item.getProductId());
        assertEquals(0, item.getQuantity());
    }

    @Test
    void parameterisedConstructor_setsProductIdAndQuantity() {
        OrderItem item = new OrderItem("P001", 5);
        assertEquals("P001", item.getProductId());
        assertEquals(5, item.getQuantity());
    }

    @Test
    void setProductId_updatesProductId() {
        OrderItem item = new OrderItem();
        item.setProductId("P002");
        assertEquals("P002", item.getProductId());
    }

    @Test
    void setQuantity_updatesQuantity() {
        OrderItem item = new OrderItem();
        item.setQuantity(10);
        assertEquals(10, item.getQuantity());
    }

    @Test
    void setUnitCost_updatesUnitCost() {
        OrderItem item = new OrderItem();
        item.setUnitCost(new BigDecimal("4.99"));
        assertEquals(new BigDecimal("4.99"), item.getUnitCost());
    }

    @Test
    void newOrderItem_unitCostIsNullByDefault() {
        OrderItem item = new OrderItem("P001", 2);
        assertNull(item.getUnitCost());
    }

    @Test
    void setOrderID_updatesOrderID() {
        OrderItem item = new OrderItem();
        item.setOrderID("ORD-1");
        assertEquals("ORD-1", item.getOrderID());
    }
}