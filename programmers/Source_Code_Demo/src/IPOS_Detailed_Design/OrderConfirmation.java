package IPOS_Detailed_Design;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderConfirmation {
    private String orderId;
    private LocalDateTime orderDateTime;
    private BigDecimal totalAmount;
    private OrderStatus status;

    public OrderConfirmation() {
    }

    public OrderConfirmation(String orderId, LocalDateTime orderDateTime, BigDecimal totalAmount, OrderStatus status) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}