package IPOS_Detailed_Design;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<IPOS_Detailed_Design.OrderItem> items;
    private OrderStatus status;

    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() { return orderId; }
    public List<OrderItem> getItems() { return items; }
    public String getStatus() { return status; }


}
