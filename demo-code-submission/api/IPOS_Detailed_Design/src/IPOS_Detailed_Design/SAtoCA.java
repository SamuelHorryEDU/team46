package IPOS_Detailed_Design;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SAtoCA implements I_SAtoCA {

    public SAtoCA() {
    }

    @Override
    public List<Order> getOrderHistory(LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }

    @Override
    public List<Product> getCatalogue() {
        return List.of();
    }

    @Override
    public OrderStatus getOrderStatus(String orderId) {
        return OrderStatus.PROCESSING;
    }

    @Override
    public BigDecimal getOutstandingBalance() {
        return BigDecimal.ZERO;
    }

    @Override
    public int checkStock(String itemID) {
        return 0;
    }

    @Override
    public void submitOrder(String merchantID, Map<String, Integer> items) {

    }

    @Override
    public boolean authenticateMerchant(String username, String password) {
        return false;
    }

    @Override
    public OrderConfirmation placeOrder(String merchantID, List<OrderItem> items) {
        return null;
    }
}