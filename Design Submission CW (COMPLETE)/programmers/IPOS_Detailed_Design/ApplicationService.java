package IPOS_Detailed_Design;

public class ApplicationService implements I_ApplicationService {

    public ApplicationService() {
    }

    @Override
    public ApprovalStatus submitCommercialApplication(Application application) {
        if (application == null) {
            return ApprovalStatus.REJECTED;
        }
        if (application.getCompanyName() == null || application.getCompanyName().isBlank()) {
            return ApprovalStatus.PENDING;
        }
        if (application.getTaxId() == null || application.getTaxId().isBlank()) {
            return ApprovalStatus.PENDING;
        }
        if (application.getAddress() == null || application.getAddress().isBlank()) {
            return ApprovalStatus.PENDING;
        }
        return ApprovalStatus.APPROVED;
    }
}
