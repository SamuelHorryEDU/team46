package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.UserRole;

public class Accountant extends User {

    public Accountant() {
        setRole(UserRole.ACCOUNTANT);
    }

    public Accountant(int userId, String username, String password) {
        super(userId, username, password, UserRole.ACCOUNTANT);
    }
}