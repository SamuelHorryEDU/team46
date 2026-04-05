package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.UserRole;

public class Admin extends User {

    public Admin() {
        setRole(UserRole.ADMIN);
    }

    public Admin(int userId, String username, String password) {
        super(userId, username, password, UserRole.ADMIN);
    }
}