package IPOS_Detailed_Design;
import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;

public interface I_SAtoCA {

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 */
	List<IPOS_Detailed_Design.Order> getOrderHistory(LocalDate fromDate, LocalDate toDate);

	List<Product> getCatalogue();

	/**
	 * 
	 * @param orderId
	 */
	OrderStatus getOrderStatus(string orderId);

	BigDecimal getOutstandingBalance();

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	OrderConfirmation placeOrder(string merchantID, List<OrderItem> items);

	/**
	 * 
	 * @param username
	 * @param password
	 */
	boolean authenticateMerchant(string username, string password);

}