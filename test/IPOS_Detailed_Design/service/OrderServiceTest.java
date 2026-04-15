package IPOS_Detailed_Design.service;

import IPOS_Detailed_Design.model.OrderConfirmation;
import IPOS_Detailed_Design.model.OrderItem;
import IPOS_Detailed_Design.model.Product;
import IPOS_Detailed_Design.model.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService();
    }

    @Test
    void getCatalogue_returnsNonEmptyList() {
        assertFalse(service.getCatalogue().isEmpty());
    }

    @Test
    void getCatalogue_returnsTwoProducts() {
        assertEquals(2, service.getCatalogue().size());
    }

    @Test
    void checkStock_validProductP001_returns120() {
        assertEquals(120, service.checkStock("P001"));
    }

    @Test
    void checkStock_validProductP002_returns80() {
        assertEquals(80, service.checkStock("P002"));
    }

    @Test
    void checkStock_invalidProductId_returnsZero() {
        assertEquals(0, service.checkStock("INVALID"));
    }

    @Test
    void submitOrder_nullMerchantId_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.submitOrder(null, Map.of("P001", 5))
        );
    }

    @Test
    void submitOrder_blankMerchantId_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.submitOrder("   ", Map.of("P001", 5))
        );
    }

    @Test
    void submitOrder_nullItems_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.submitOrder("1", null)
        );
    }

    @Test
    void submitOrder_emptyItems_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.submitOrder("1", Collections.emptyMap())
        );
    }

    @Test
    void placeOrder_nullMerchantId_throwsIllegalArgument() {
        OrderItem item = new OrderItem("P001", 2);
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(null, List.of(item))
        );
    }

    @Test
    void placeOrder_blankMerchantId_throwsIllegalArgument() {
        OrderItem item = new OrderItem("P001", 2);
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder("   ", List.of(item))
        );
    }

    @Test
    void placeOrder_nullItems_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder("1", null)
        );
    }

    @Test
    void placeOrder_emptyItems_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder("1", Collections.emptyList())
        );
    }

    @Test
    void placeOrder_validOrder_returnsConfirmationWithId() {
        OrderItem item = new OrderItem("P001", 2);
        OrderConfirmation confirmation = service.placeOrder("1", List.of(item));
        assertNotNull(confirmation);
        assertNotNull(confirmation.getOrderId());
    }

    @Test
    void placeOrder_validOrder_statusIsAccepted() {
        OrderItem item = new OrderItem("P001", 1);
        OrderConfirmation confirmation = service.placeOrder("1", List.of(item));
        assertEquals(OrderStatus.ACCEPTED, confirmation.getStatus());
    }

    @Test
    void placeOrder_twoUnitsOfP001_totalIs9point98() {
        OrderItem item = new OrderItem("P001", 2);
        OrderConfirmation confirmation = service.placeOrder("1", List.of(item));
        assertEquals(new BigDecimal("9.98"), confirmation.getTotalAmount());
    }

    @Test
    void getOrderStatus_unknownOrderId_returnsProcessing() {
        assertEquals(OrderStatus.PROCESSING, service.getOrderStatus("UNKNOWN-999"));
    }

    @Test
    void getOrderStatus_afterPlacingOrder_returnsAccepted() {
        OrderItem item = new OrderItem("P001", 1);
        OrderConfirmation confirmation = service.placeOrder("1", List.of(item));
        assertEquals(OrderStatus.ACCEPTED, service.getOrderStatus(confirmation.getOrderId()));
    }

    @Test
    void getOrderHistory_noOrdersPlaced_returnsEmptyList() {
        LocalDate today = LocalDate.now();
        assertTrue(service.getOrderHistory(today, today).isEmpty());
    }

    @Test
    void getOrderHistory_afterPlacingOrder_returnsOneOrder() {
        OrderItem item = new OrderItem("P001", 1);
        service.placeOrder("1", List.of(item));
        LocalDate today = LocalDate.now();
        assertEquals(1, service.getOrderHistory(today, today).size());
    }
}