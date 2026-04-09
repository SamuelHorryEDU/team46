package IPOS_Detailed_Design.model;

import java.math.BigDecimal;

public class OrderItem {
    private String productId;
    private String orderID;
    private int quantity;
    private BigDecimal unitCost;

    public OrderItem() {
    }

    public OrderItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderID() { return orderID; }
    public void   setOrderID(String orderID)  { this.orderID = orderID; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }
}