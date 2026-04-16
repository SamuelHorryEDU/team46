package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryDepartmentEmployeeTest {

    @Test
    void defaultConstructor_setsRoleToDelivery() {
        DeliveryDepartmentEmployee employee = new DeliveryDepartmentEmployee();
        assertEquals(UserRole.DELIVERY_DEPARTMENT_EMPLOYEE, employee.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        DeliveryDepartmentEmployee employee = new DeliveryDepartmentEmployee(7, "delivery1", "pass123");
        assertEquals(7, employee.getUserId());
        assertEquals("delivery1", employee.getUsername());
        assertEquals("pass123", employee.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToDelivery() {
        DeliveryDepartmentEmployee employee = new DeliveryDepartmentEmployee(7, "delivery1", "pass123");
        assertEquals(UserRole.DELIVERY_DEPARTMENT_EMPLOYEE, employee.getRole());
    }

    @Test
    void deliveryEmployee_isInstanceOfUser() {
        DeliveryDepartmentEmployee employee = new DeliveryDepartmentEmployee(7, "delivery1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, employee);
    }
}