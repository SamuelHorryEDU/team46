package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountantTest {

    @Test
    void defaultConstructor_setsRoleToAccountant() {
        Accountant accountant = new Accountant();
        assertEquals(UserRole.ACCOUNTANT, accountant.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        Accountant accountant = new Accountant(3, "accountant1", "pass123");
        assertEquals(3, accountant.getUserId());
        assertEquals("accountant1", accountant.getUsername());
        assertEquals("pass123", accountant.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToAccountant() {
        Accountant accountant = new Accountant(3, "accountant1", "pass123");
        assertEquals(UserRole.ACCOUNTANT, accountant.getRole());
    }

    @Test
    void accountant_isInstanceOfUser() {
        Accountant accountant = new Accountant(3, "accountant1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, accountant);
    }
}