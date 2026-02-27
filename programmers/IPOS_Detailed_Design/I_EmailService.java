package IPOS_Detailed_Design;

public interface I_EmailService {

	/**
	 * 
	 * @param recipient
	 * @param subject
	 * @param body
	 */
	abstract boolean sendEmail(string recipient, string subject, String body);

}