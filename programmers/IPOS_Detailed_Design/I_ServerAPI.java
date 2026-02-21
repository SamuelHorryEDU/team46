package IPOS_Detailed_Design;

public interface I_ServerAPI {

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
	abstract boolean submitOrder(String merchantID, Map<String, Integer> items);

	/**
	 * 
	 * @param itemID
	 */
	int checkStock(String itemID);

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

}