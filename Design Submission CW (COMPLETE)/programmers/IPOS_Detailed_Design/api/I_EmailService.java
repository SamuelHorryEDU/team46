package IPOS_Detailed_Design.api;

public interface I_EmailService {
	boolean sendEmail(String recipient, String subject, String body);
}