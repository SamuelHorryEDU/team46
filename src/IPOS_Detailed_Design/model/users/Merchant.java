package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.UserRole;

public class Merchant extends User {

    public Merchant() {
        setRole(UserRole.MERCHANT);
    }

    public Merchant(int userId, String username, String password) {
        super(userId, username, password, UserRole.MERCHANT);
    }
}