package IPOS_Detailed_Design.app;

import IPOS_Detailed_Design.api.I_SAtoCA;
import IPOS_Detailed_Design.model.Order;
import IPOS_Detailed_Design.model.OrderConfirmation;
import IPOS_Detailed_Design.model.OrderItem;
import IPOS_Detailed_Design.model.Product;
import IPOS_Detailed_Design.model.enums.OrderStatus;
import IPOS_Detailed_Design.service.AccountService;
import IPOS_Detailed_Design.service.OrderService;
import IPOS_Detailed_Design.dao.InvoiceDAO;
import IPOS_Detailed_Design.dao.UserDAO;
import IPOS_Detailed_Design.dao.OrderDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SAtoCA implements I_SAtoCA {

	private final AccountService accountService;
	private final OrderService orderService;
	private final OrderDAO orderDAO = new OrderDAO();
	private final InvoiceDAO invoiceDAO = new InvoiceDAO();
	private final UserDAO userDAO = new UserDAO();

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
		OrderConfirmation confirmation = orderService.placeOrder(merchantID, items);
		if (confirmation != null) {
			// Reconstruct the Order for persistence — all fields available here
			Order order = new Order();
			order.setOrderId(confirmation.getOrderId());
			order.setMerchantId(Integer.parseInt(merchantID));
			order.setOrderDate(confirmation.getOrderDateTime());
			order.setTotalAmount(confirmation.getTotalAmount());
			order.setStatus(OrderStatus.ACCEPTED);
			order.setEstimatedDelivery(confirmation.getEstimatedDelivery());
			order.setDispatchDetails("Pending dispatch");
			order.setItems(items);

			boolean saved = orderDAO.createOrder(order);
			if (saved) {
				invoiceDAO.createInvoice(confirmation.getOrderId());
				userDAO.increaseBalance(Integer.parseInt(merchantID), confirmation.getTotalAmount());
			} else {
				System.err.println("SAtoCA.placeOrder: DB write failed for " + confirmation.getOrderId());
			}
		}
		return confirmation;
	}
}