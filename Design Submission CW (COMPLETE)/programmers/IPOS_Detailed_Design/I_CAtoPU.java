package IPOS_Detailed_Design;

public interface I_CAtoPU {

	/**
	 * 
	 * @param productId
	 */
	abstract int getStockLevel(string productId);

	/**
	 * 
	 * @param productId
	 * @param quantity
	 */
	abstract boolean deductStock(string productId, int quantity);

	List<StockItem> getAllStock();

}