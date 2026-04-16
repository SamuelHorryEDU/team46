package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseEmployeeTest {

    @Test
    void defaultConstructor_setsRoleToWarehouseEmployee() {
        WarehouseEmployee employee = new WarehouseEmployee();
        assertEquals(UserRole.WAREHOUSE_EMPLOYEE, employee.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        WarehouseEmployee employee = new WarehouseEmployee(6, "warehouse1", "pass123");
        assertEquals(6, employee.getUserId());
        assertEquals("warehouse1", employee.getUsername());
        assertEquals("pass123", employee.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToWarehouseEmployee() {
        WarehouseEmployee employee = new WarehouseEmployee(6, "warehouse1", "pass123");
        assertEquals(UserRole.WAREHOUSE_EMPLOYEE, employee.getRole());
    }

    @Test
    void warehouseEmployee_isInstanceOfUser() {
        WarehouseEmployee employee = new WarehouseEmployee(6, "warehouse1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, employee);
    }
}