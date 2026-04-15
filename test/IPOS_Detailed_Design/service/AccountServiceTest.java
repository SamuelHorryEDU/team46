package IPOS_Detailed_Design.service;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.AccountStatus;
import IPOS_Detailed_Design.model.users.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService();
    }

    @Test
    void authenticateMerchant_validCredentials_returnsTrue() {
        assertTrue(service.authenticateMerchant("merchant1", "correctPass"));
    }

    @Test
    void authenticateMerchant_wrongPassword_returnsFalse() {
        assertFalse(service.authenticateMerchant("merchant1", "wrongPass"));
    }

    @Test
    void authenticateMerchant_wrongUsername_returnsFalse() {
        assertFalse(service.authenticateMerchant("wrongUser", "correctPass"));
    }

    @Test
    void authenticateMerchant_nonMerchantUser_returnsFalse() {
        assertFalse(service.authenticateMerchant("admin1", "adminPass"));
    }

    @Test
    void authenticateUser_validCredentials_returnsUser() {
        User user = service.authenticateUser("admin1", "adminPass");
        assertNotNull(user);
        assertEquals("admin1", user.getUsername());
    }

    @Test
    void authenticateUser_wrongPassword_returnsNull() {
        assertNull(service.authenticateUser("admin1", "wrongPass"));
    }

    @Test
    void authenticateUser_nonExistentUser_returnsNull() {
        assertNull(service.authenticateUser("nobody", "pass"));
    }

    @Test
    void getOutstandingBalance_validMerchant_returnsCorrectBalance() {
        assertEquals(new BigDecimal("250.00"), service.getOutstandingBalance(1));
    }

    @Test
    void getOutstandingBalance_invalidId_returnsZero() {
        assertEquals(BigDecimal.ZERO, service.getOutstandingBalance(999));
    }

    @Test
    void getUserById_validId_returnsUser() {
        User user = service.getUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getUserId());
    }

    @Test
    void getUserById_invalidId_returnsNull() {
        assertNull(service.getUserById(999));
    }

    @Test
    void getUserByUsername_validUsername_returnsUser() {
        User user = service.getUserByUsername("merchant1");
        assertNotNull(user);
        assertEquals("merchant1", user.getUsername());
    }

    @Test
    void getUserByUsername_invalidUsername_returnsNull() {
        assertNull(service.getUserByUsername("nobody"));
    }

    @Test
    void getMerchantById_validMerchantId_returnsMerchant() {
        Merchant merchant = service.getMerchantById(1);
        assertNotNull(merchant);
        assertEquals(1, merchant.getUserId());
    }

    @Test
    void getMerchantById_nonMerchantId_returnsNull() {
        assertNull(service.getMerchantById(2));
    }

    @Test
    void getMerchantById_invalidId_returnsNull() {
        assertNull(service.getMerchantById(999));
    }

    @Test
    void getAllUsers_returnsAllSevenUsers() {
        List<User> users = service.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(7, users.size());
    }

    @Test
    void getAllMerchants_returnsOnlyMerchants() {
        List<Merchant> merchants = service.getAllMerchants();
        assertEquals(1, merchants.size());
    }

    @Test
    void suspendMerchant_validMerchant_statusBecomeSuspended() {
        assertTrue(service.suspendMerchant(1));
        assertEquals(AccountStatus.SUSPENDED, service.getMerchantById(1).getAccountStatus());
    }

    @Test
    void suspendMerchant_invalidId_returnsFalse() {
        assertFalse(service.suspendMerchant(999));
    }

    @Test
    void markMerchantInDefault_validMerchant_statusBecomesInDefault() {
        assertTrue(service.markMerchantInDefault(1));
        assertEquals(AccountStatus.IN_DEFAULT, service.getMerchantById(1).getAccountStatus());
    }

    @Test
    void markMerchantInDefault_invalidId_returnsFalse() {
        assertFalse(service.markMerchantInDefault(999));
    }

    @Test
    void restoreMerchantToNormal_afterSuspend_statusBecomesNormal() {
        service.suspendMerchant(1);
        assertTrue(service.restoreMerchantToNormal(1));
        assertEquals(AccountStatus.NORMAL, service.getMerchantById(1).getAccountStatus());
    }

    @Test
    void restoreMerchantToNormal_invalidId_returnsFalse() {
        assertFalse(service.restoreMerchantToNormal(999));
    }

    @Test
    void updateOutstandingBalance_validMerchant_balanceUpdated() {
        BigDecimal newBalance = new BigDecimal("500.00");
        assertTrue(service.updateOutstandingBalance(1, newBalance));
        assertEquals(newBalance, service.getOutstandingBalance(1));
    }

    @Test
    void updateOutstandingBalance_invalidId_returnsFalse() {
        assertFalse(service.updateOutstandingBalance(999, new BigDecimal("100.00")));
    }
}