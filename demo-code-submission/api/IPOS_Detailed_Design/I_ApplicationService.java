package IPOS_Detailed_Design;

public interface I_ApplicationService {

	/**
	 * 
	 * @param application
	 */
	abstract ApprovalStatus submitCommercialApplication(Application application);

}