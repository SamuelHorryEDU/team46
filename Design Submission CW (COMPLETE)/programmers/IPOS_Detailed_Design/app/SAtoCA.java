package IPOS_Detailed_Design.app;

import IPOS_Detailed_Design.api.I_SAtoCA;
import IPOS_Detailed_Design.model.Order;
import IPOS_Detailed_Design.model.OrderConfirmation;
import IPOS_Detailed_Design.model.OrderItem;
import IPOS_Detailed_Design.model.Product;
import IPOS_Detailed_Design.model.enums.OrderStatus;
import IPOS_Detailed_Design.service.AccountService;
import IPOS_Detailed_Design.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SAtoCA implements I_SAtoCA {

	private final AccountService accountService;
	private final OrderService orderService;

	public SAtoCA() {
		this.accountService = new AccountService();
		this.orderService = new OrderService();
	}

	@Override
	public List<Order> getOrderHistory(LocalDate fromDate, LocalDate toDate) {
		return orderService.getOrderHistory(fromDate, toDate);
	}

	@Override
	public List<Product> getCatalogue() {
		return orderService.getCatalogue();
	}

	@Override
	public OrderStatus getOrderStatus(String orderId) {
		return orderService.getOrderStatus(orderId);
	}

	@Override
	public BigDecimal getOutstandingBalance() {
		return accountService.getOutstandingBalance(1);
	}

	@Override
	public int checkStock(String itemID) {
		return orderService.checkStock(itemID);
	}

	@Override
	public void submitOrder(String merchantID, Map<String, Integer> items) {
		orderService.submitOrder(merchantID, items);
	}

	@Override
	public boolean authenticateMerchant(String username, String password) {
		return accountService.authenticateMerchant(username, password);
	}

	@Override
	public OrderConfirmation placeOrder(String merchantID, List<OrderItem> items) {
		return orderService.placeOrder(merchantID, items);
	}
}