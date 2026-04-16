package IPOS_Detailed_Design.model;

import IPOS_Detailed_Design.model.enums.AccountStatus;
import IPOS_Detailed_Design.model.enums.DiscountPlanType;
import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void defaultConstructor_createsEmptyUser() {
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void parameterisedConstructor_setsAllFields() {
        User user = new User(1, "merchant1", "pass123", UserRole.MERCHANT);
        assertEquals(1, user.getUserId());
        assertEquals("merchant1", user.getUsername());
        assertEquals("pass123", user.getPassword());
        assertEquals(UserRole.MERCHANT, user.getRole());
    }

    @Test
    void parameterisedConstructor_setsAccountStatusToNormal() {
        User user = new User(1, "merchant1", "pass123", UserRole.MERCHANT);
        assertEquals(AccountStatus.NORMAL, user.getAccountStatus());
    }

    @Test
    void parameterisedConstructor_setsOutstandingBalanceToZero() {
        User user = new User(1, "merchant1", "pass123", UserRole.MERCHANT);
        assertEquals(BigDecimal.ZERO, user.getOutstandingBalance());
    }

    @Test
    void setUsername_updatesUsername() {
        User user = new User();
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    void setPassword_updatesPassword() {
        User user = new User();
        user.setPassword("newPass");
        assertEquals("newPass", user.getPassword());
    }

    @Test
    void setAccountStatus_updatesAccountStatus() {
        User user = new User(1, "merchant1", "pass123", UserRole.MERCHANT);
        user.setAccountStatus(AccountStatus.SUSPENDED);
        assertEquals(AccountStatus.SUSPENDED, user.getAccountStatus());
    }

    @Test
    void setOutstandingBalance_updatesOutstandingBalance() {
        User user = new User();
        user.setOutstandingBalance(new BigDecimal("250.00"));
        assertEquals(new BigDecimal("250.00"), user.getOutstandingBalance());
    }

    @Test
    void setCreditLimit_updatesCreditLimit() {
        User user = new User();
        user.setCreditLimit(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1000.00"), user.getCreditLimit());
    }

    @Test
    void setDiscountPlan_updatesDiscountPlan() {
        User user = new User();
        user.setDiscountPlan(DiscountPlanType.FIXED);
        assertEquals(DiscountPlanType.FIXED, user.getDiscountPlan());
    }
}
