package IPOS_Detailed_Design;

public class WarehouseEmployee extends User {

    public WarehouseEmployee() {
        setRole(UserRole.WAREHOUSE_EMPLOYEE);
    }

    public WarehouseEmployee(int userId, String username, String password) {
        super(userId, username, password, UserRole.WAREHOUSE_EMPLOYEE);
    }
}