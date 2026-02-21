public interface I_InventoryAPI {

	/**
	 * 
	 * @param item
	 */
	abstract int stockAvailability(string item);

	/**
	 * 
	 * @param item
	 * @param quantity
	 */
	abstract boolean withdrawItem(string item, int quantity);

	/**
	 * 
	 * @param keyword
	 */
	abstract string[] searchStock(string keyword);

}