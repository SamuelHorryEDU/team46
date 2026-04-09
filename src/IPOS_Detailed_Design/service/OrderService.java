package IPOS_Detailed_Design.service;

import IPOS_Detailed_Design.model.Product;
import IPOS_Detailed_Design.model.Order;
import IPOS_Detailed_Design.model.OrderItem;
import IPOS_Detailed_Design.model.OrderConfirmation;
import IPOS_Detailed_Design.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final List<Product> catalogue = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    public OrderService() {
        Product p1 = new Product("P001", "Paracetamol", new BigDecimal("4.99"), 120);
        p1.setDescription("Pain relief tablets");
        p1.setCategory("Medicine");
        p1.setPackageType("Box");
        p1.setUnit("Tablet");
        p1.setUnitsInPack(16);
        p1.setStockLimit(10);
        p1.setActive(true);

        Product p2 = new Product("P002", "Ibuprofen", new BigDecimal("6.49"), 80);
        p2.setDescription("Anti-inflammatory tablets");
        p2.setCategory("Medicine");
        p2.setPackageType("Box");
        p2.setUnit("Tablet");
        p2.setUnitsInPack(24);
        p2.setStockLimit(10);
        p2.setActive(true);

        catalogue.add(p1);
        catalogue.add(p2);
    }

    public List<Product> getCatalogue() {
        return catalogue;
    }

    public int checkStock(String itemId) {
        for (Product product : catalogue) {
            if (product.getProductId().equals(itemId)) {
                return product.getAvailability();
            }
        }
        return 0;
    }

    public void submitOrder(String merchantId, Map<String, Integer> items) {
        if (merchantId == null || merchantId.isBlank()) {
            throw new IllegalArgumentException("merchantId cannot be blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items cannot be empty");
        }
    }

    public OrderConfirmation placeOrder(String merchantId, List<OrderItem> items) {
        if (merchantId == null || merchantId.isBlank()) {
            throw new IllegalArgumentException("merchantId cannot be blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items cannot be empty");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            for (Product product : catalogue) {
                if (product.getProductId().equals(item.getProductId())) {
                    BigDecimal unitCost = product.getPackageCost();
                    item.setUnitCost(unitCost);
                    total = total.add(unitCost.multiply(BigDecimal.valueOf(item.getQuantity())));
                }
            }
        }

        String orderId = "ORD-" + (orders.size() + 1);

        Order order = new Order();
        order.setOrderId(orderId);
        order.setMerchantId(Integer.parseInt(merchantId));
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ACCEPTED);
        order.setTotalAmount(total);
        order.setEstimatedDelivery(LocalDateTime.now().plusDays(2));
        order.setDispatchDetails("Pending dispatch");
        order.setItems(items);

        orders.add(order);

        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.setOrderId(orderId);
        confirmation.setOrderDateTime(order.getOrderDate());
        confirmation.setTotalAmount(total);
        confirmation.setEstimatedDelivery(order.getEstimatedDelivery());
        confirmation.setStatus(order.getStatus());

        return confirmation;
    }

    public OrderStatus getOrderStatus(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order.getStatus();
            }
        }
        return OrderStatus.PROCESSING;
    }

    public List<Order> getOrderHistory(LocalDate fromDate, LocalDate toDate) {
        List<Order> result = new ArrayList<>();

        for (Order order : orders) {
            LocalDate orderLocalDate = order.getOrderDate().toLocalDate();

            if ((orderLocalDate.isEqual(fromDate) || orderLocalDate.isAfter(fromDate)) &&
                    (orderLocalDate.isEqual(toDate) || orderLocalDate.isBefore(toDate))) {
                result.add(order);
            }
        }

        return result;
    }
}