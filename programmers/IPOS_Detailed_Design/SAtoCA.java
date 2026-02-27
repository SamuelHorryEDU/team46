package IPOS_Detailed_Design;

import Design_Model.IPOS_Detailed_Design.I_ServerAPI.*;

public class SAtoCA implements submitOrder, I_SAtoCA {

	public SAtoCA() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param merchantID
	 * @param items
	 */
	public OrderConfirmation placeOrder(string merchantID, List<OrderItem> items) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public boolean authenticateMerchant(string username, string password) {
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