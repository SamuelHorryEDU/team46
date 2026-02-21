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

}