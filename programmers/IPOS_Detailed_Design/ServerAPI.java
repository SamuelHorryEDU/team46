package IPOS_Detailed_Design;

import Design_Model.IPOS_Detailed_Design.I_ServerAPI.*;

public class ServerAPI implements submitOrder, I_ServerAPI {

	public ServerAPI() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param itemID
	 */
	public int checkStock(String itemID) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	public boolean submitOrder(String merchantID, Map<String, Integer> items) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public void authenticateMerchant(String username, String password) {
		throw new UnsupportedOperationException();
	}

	public BigDecimal getOutstandingBalance() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orderId
	 */
	public OrderStatus getOrderStatus(String orderId) {
		throw new UnsupportedOperationException();
	}

	public List<Product> getCatalogue() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 */
	public List<Order> getOrderHistory(LocalDate fromDate, LocalDate toDate) {
		throw new UnsupportedOperationException();
	}

}