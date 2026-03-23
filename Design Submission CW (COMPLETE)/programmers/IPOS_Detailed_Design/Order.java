package IPOS_Detailed_Design;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String merchantId;
    private LocalDate orderDate;
    private OrderStatus status;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(String orderId, String merchantId, LocalDate orderDate, OrderStatus status, List<OrderItem> items) {
        this.orderId = orderId;
        this.merchantId = merchantId;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}