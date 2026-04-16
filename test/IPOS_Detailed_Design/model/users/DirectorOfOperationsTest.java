package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectorOfOperationsTest {

    @Test
    void defaultConstructor_setsRoleToDirector() {
        DirectorOfOperations director = new DirectorOfOperations();
        assertEquals(UserRole.DIRECTOR_OF_OPERATIONS, director.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        DirectorOfOperations director = new DirectorOfOperations(5, "director1", "pass123");
        assertEquals(5, director.getUserId());
        assertEquals("director1", director.getUsername());
        assertEquals("pass123", director.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToDirector() {
        DirectorOfOperations director = new DirectorOfOperations(5, "director1", "pass123");
        assertEquals(UserRole.DIRECTOR_OF_OPERATIONS, director.getRole());
    }

    @Test
    void director_isInstanceOfUser() {
        DirectorOfOperations director = new DirectorOfOperations(5, "director1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, director);
    }
}