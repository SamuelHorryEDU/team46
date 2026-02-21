package IPOS_Detailed_Design;

public interface I_WebAPI {

	/**
	 * 
	 * @param companyName
	 * @param taxID
	 * @param address
	 */
	abstract boolean submitCommercialApplication(String companyName, String taxID, String address);

	/**
	 * 
	 * @param username
	 */
	abstract boolean isMemberValid(String username);

}