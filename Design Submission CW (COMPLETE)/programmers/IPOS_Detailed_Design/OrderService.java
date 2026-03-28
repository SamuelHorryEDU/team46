package IPOS_Detailed_Design;

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
        catalogue.add(new Product("P001", "Paracetamol", new BigDecimal("4.99"), 120));
        catalogue.add(new Product("P002", "Ibuprofen", new BigDecimal("6.49"), 80));
    }

    public List<Product> getCatalogue() {
        return catalogue;
    }

    public int checkStock(String itemId) {
        for (Product product : catalogue) {
            if (product.getProductId().equals(itemId)) {
                return product.getStock();
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
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            for (Product product : catalogue) {
                if (product.getProductId().equals(item.getProductId())) {
                    total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }
            }
        }

        String orderId = "ORD-" + (orders.size() + 1);

        Order order = new Order();
        order.setOrderId(orderId);
        order.setMerchantId(merchantId);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.ACCEPTED);
        order.setItems(items);
        orders.add(order);

        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.setOrderId(orderId);
        confirmation.setOrderDateTime(LocalDateTime.now());
        confirmation.setStatus(OrderStatus.ACCEPTED);
        confirmation.setTotalAmount(total);

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
            if ((order.getOrderDate().isEqual(fromDate) || order.getOrderDate().isAfter(fromDate)) &&
                    (order.getOrderDate().isEqual(toDate) || order.getOrderDate().isBefore(toDate))) {
                result.add(order);
            }
        }
        return result;
    }
}