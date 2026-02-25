package IPOS_Detailed_Design;

public interface I_ServerAPI {

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
	OrderStatus getOrderStatus(String orderId);

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
	boolean authenticateMerchant(String username, String password);

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	void placeOrder(String merchantID, List<OrderItem> items);

}