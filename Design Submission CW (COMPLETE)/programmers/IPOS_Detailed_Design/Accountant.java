package IPOS_Detailed_Design;

public class Accountant extends User {

    public Accountant() {
        setRole(UserRole.ACCOUNTANT);
    }

    public Accountant(int userId, String username, String password) {
        super(userId, username, password, UserRole.ACCOUNTANT);
    }
}