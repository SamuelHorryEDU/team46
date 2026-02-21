package IPOS_Detailed_Design;

public interface I_EmailAPI {

	/**
	 * 
	 * @param recipient
	 * @param subject
	 * @param body
	 */
	abstract void sendEmail(String recipient, String subject, String body);

	/**
	 * 
	 * @param recipients
	 * @param subject
	 * @param body
	 */
	abstract void sendBroadcast(String[] recipients, String subject, String body);

}