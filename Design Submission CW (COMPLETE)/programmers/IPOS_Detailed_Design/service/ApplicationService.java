package IPOS_Detailed_Design.api;

import IPOS_Detailed_Design.model.Application;
import IPOS_Detailed_Design.model.enums.ApprovalStatus;

public class ApplicationService implements I_ApplicationService {

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