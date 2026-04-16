package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MerchantTest {

    @Test
    void defaultConstructor_setsRoleToMerchant() {
        Merchant merchant = new Merchant();
        assertEquals(UserRole.MERCHANT, merchant.getRole());
    }

    @Test
    void parameterisedConstructor_setsUserIdUsernamePassword() {
        Merchant merchant = new Merchant(1, "merchant1", "pass123");
        assertEquals(1, merchant.getUserId());
        assertEquals("merchant1", merchant.getUsername());
        assertEquals("pass123", merchant.getPassword());
    }

    @Test
    void parameterisedConstructor_setsRoleToMerchant() {
        Merchant merchant = new Merchant(1, "merchant1", "pass123");
        assertEquals(UserRole.MERCHANT, merchant.getRole());
    }

    @Test
    void merchant_isInstanceOfUser() {
        Merchant merchant = new Merchant(1, "merchant1", "pass123");
        assertInstanceOf(IPOS_Detailed_Design.model.User.class, merchant);
    }
}