package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void defaultConstructor_setsRoleToAdmin() {
        Admin admin = new Admin();
        assertEquals(UserRole.ADMIN, admin.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        Admin admin = new Admin(2, "admin1", "pass123");
        assertEquals(2, admin.getUserId());
        assertEquals("admin1", admin.getUsername());
        assertEquals("pass123", admin.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToAdmin() {
        Admin admin = new Admin(2, "admin1", "pass123");
        assertEquals(UserRole.ADMIN, admin.getRole());
    }

    @Test
    void admin_isInstanceOfUser() {
        Admin admin = new Admin(2, "admin1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, admin);
    }
}