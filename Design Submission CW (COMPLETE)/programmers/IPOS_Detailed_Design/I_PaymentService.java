package IPOS_Detailed_Design;

public interface I_PaymentService {

	/**
	 * 
	 * @param amount
	 * @param currency
	 * @param cardNumber
	 * @param expiryDate
	 */
	abstract String makePayment(BigDecimal amount, string currency, string cardNumber, int expiryDate);

}