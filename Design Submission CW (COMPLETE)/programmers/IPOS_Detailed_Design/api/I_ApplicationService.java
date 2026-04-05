package IPOS_Detailed_Design.api;

import IPOS_Detailed_Design.model.Application;
import IPOS_Detailed_Design.model.enums.ApprovalStatus;

public interface I_ApplicationService {
	ApprovalStatus submitCommercialApplication(Application application);
}