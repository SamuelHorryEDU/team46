package IPOS_Detailed_Design;

public class Admin extends User {

    public Admin() {
        setRole(UserRole.ADMIN);
    }

    public Admin(int userId, String username, String password) {
        super(userId, username, password, UserRole.ADMIN);
    }
}