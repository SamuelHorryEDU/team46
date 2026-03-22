package IPOS_Detailed_Design;

public interface I_ApplicationService {

	/**
	 * 
	 * @param application
	 * @param taxID
	 * @param address
	 */
	abstract ApprovalStatus submitCommercialApplication(Application application);

}