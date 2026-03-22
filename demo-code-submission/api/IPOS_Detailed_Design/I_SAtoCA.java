package IPOS_Detailed_Design;

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