package IPOS_Detailed_Design;

public class OrderConfirmation {
    private int orderId;
    private String message;
    private double totalAmount;

    public OrderConfirmation(int orderId, String message, double totalAmount) {
        this.orderId = orderId;
        this.message = message;
        this.totalAmount = totalAmount;
    }

    public void printConfirmation() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Message: " + message);
        System.out.println("Total: £" + totalAmount);
    }

    // Getters
    public int getOrderId() { return orderId; }
    public String getMessage() { return message; }
    public double getTotalAmount() { return totalAmount; }
}
