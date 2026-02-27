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
	boolean authenticateMerchant(string username, string password);

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	OrderConfirmation placeOrder(string merchantID, List<OrderItem> items);

}