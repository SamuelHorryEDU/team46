package IPOS_Detailed_Design;

public class Merchant extends User {

    public Merchant() {
        setRole(UserRole.MERCHANT);
    }

    public Merchant(int userId, String username, String password) {
        super(userId, username, password, UserRole.MERCHANT);
    }
}