package IPOS_Detailed_Design;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface I_SAtoCA{

    List<Order> getOrderHistory(LocalDate fromDate, LocalDate toDate);

    List<Product> getCatalogue();

    OrderStatus getOrderStatus(String orderId);

    BigDecimal getOutstandingBalance();

    int checkStock(String itemID);

    void submitOrder(String merchantID, Map<String, Integer> items);

    boolean authenticateMerchant(String username, String password);

    OrderConfirmation placeOrder(String merchantID, List<OrderItem> items);
}

