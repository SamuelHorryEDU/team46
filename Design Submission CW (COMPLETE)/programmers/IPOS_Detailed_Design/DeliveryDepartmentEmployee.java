package IPOS_Detailed_Design;

public class DeliveryDepartmentEmployee extends User {

    public DeliveryDepartmentEmployee() {
        setRole(UserRole.DELIVERY_DEPARTMENT_EMPLOYEE);
    }

    public DeliveryDepartmentEmployee(int userId, String username, String password) {
        super(userId, username, password, UserRole.DELIVERY_DEPARTMENT_EMPLOYEE);
    }
}