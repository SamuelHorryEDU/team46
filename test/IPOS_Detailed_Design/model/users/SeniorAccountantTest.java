package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeniorAccountantTest {

    @Test
    void defaultConstructor_setsRoleToSeniorAccountant() {
        SeniorAccountant senior = new SeniorAccountant();
        assertEquals(UserRole.SENIOR_ACCOUNTANT, senior.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        SeniorAccountant senior = new SeniorAccountant(4, "senior1", "pass123");
        assertEquals(4, senior.getUserId());
        assertEquals("senior1", senior.getUsername());
        assertEquals("pass123", senior.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToSeniorAccountant() {
        SeniorAccountant senior = new SeniorAccountant(4, "senior1", "pass123");
        assertEquals(UserRole.SENIOR_ACCOUNTANT, senior.getRole());
    }

    @Test
    void seniorAccountant_isInstanceOfUser() {
        SeniorAccountant senior = new SeniorAccountant(4, "senior1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, senior);
    }
}