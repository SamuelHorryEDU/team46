package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.UserRole;

public class SeniorAccountant extends User {

    public SeniorAccountant() {
        setRole(UserRole.SENIOR_ACCOUNTANT);
    }

    public SeniorAccountant(int userId, String username, String password) {
        super(userId, username, password, UserRole.SENIOR_ACCOUNTANT);
    }
}