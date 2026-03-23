package IPOS_Detailed_Design;
import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Map;

public interface I_SAtoCA {

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 */
	List<Order> getOrderHistory(LocalDate fromDate, LocalDate toDate);

	List<Product> getCatalogue();

	/**
	 * 
	 * @param orderId
	 */
	<string> OrderStatus getOrderStatus(string orderId);

	BigDecimal getOutstandingBalance();

	/**
	 * 
	 * @param itemID
	 */
	int checkStock(String itemID);

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	void submitOrder(String merchantID, Map<String, Integer> items);

	/**
	 * 
	 * @param username
	 * @param password
	 */
	<string> boolean authenticateMerchant(string username, string password);

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	<string> OrderConfirmation placeOrder(string merchantID, List<OrderItem> items);

}