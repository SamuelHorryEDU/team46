package IPOS_Detailed_Design.service;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.AccountStatus;
import IPOS_Detailed_Design.model.enums.DiscountPlanType;
import IPOS_Detailed_Design.model.enums.UserRole;
import IPOS_Detailed_Design.model.users.Accountant;
import IPOS_Detailed_Design.model.users.Admin;
import IPOS_Detailed_Design.model.users.DeliveryDepartmentEmployee;
import IPOS_Detailed_Design.model.users.DirectorOfOperations;
import IPOS_Detailed_Design.model.users.Merchant;
import IPOS_Detailed_Design.model.users.SeniorAccountant;
import IPOS_Detailed_Design.model.users.WarehouseEmployee;
import IPOS_Detailed_Design.dao.UserDAO;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final List<User> users = new ArrayList<>();
    private final UserDAO userDAO = new UserDAO();



    public AccountService() {
        Merchant merchant = new Merchant(1, "merchant1", "correctPass");
        merchant.setAccountNo("ACC001");
        merchant.setAccountHolderName("Pharma Ltd");
        merchant.setContactName("John Smith");
        merchant.setAddress("12 High Street");
        merchant.setPhone("07123456789");
        merchant.setCreditLimit(new BigDecimal("1000.00"));
        merchant.setDiscountPlan(DiscountPlanType.FIXED);
        merchant.setDiscountRate("10%");
        merchant.setAccountStatus(AccountStatus.NORMAL);
        merchant.setOutstandingBalance(new BigDecimal("250.00"));
        users.add(merchant);


        Admin admin = new Admin(2, "admin1", "adminPass");
        admin.setContactName("Admin User");
        users.add(admin);

        Accountant accountant = new Accountant(3, "accountant1", "accountPass");
        accountant.setContactName("Accountant User");
        users.add(accountant);

        SeniorAccountant seniorAccountant = new SeniorAccountant(4, "senior1", "seniorPass");
        seniorAccountant.setContactName("Senior Accountant");
        users.add(seniorAccountant);

        DirectorOfOperations director = new DirectorOfOperations(5, "director1", "directorPass");
        director.setContactName("Director User");
        users.add(director);

        WarehouseEmployee warehouseEmployee = new WarehouseEmployee(6, "warehouse1", "warehousePass");
        warehouseEmployee.setContactName("Warehouse User");
        users.add(warehouseEmployee);

        DeliveryDepartmentEmployee deliveryEmployee = new DeliveryDepartmentEmployee(7, "delivery1", "deliveryPass");
        deliveryEmployee.setContactName("Delivery User");
        users.add(deliveryEmployee);
    }

    public boolean authenticateMerchant(String username, String password) {
        for (User user : users) {
            if (user.getRole() == UserRole.MERCHANT &&
                    user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public BigDecimal getOutstandingBalance(int merchantId) {
        for (User user : users) {
            if (user.getRole() == UserRole.MERCHANT &&
                    user.getUserId() == merchantId) {
                return user.getOutstandingBalance();
            }
        }
        return BigDecimal.ZERO;
    }

    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Merchant getMerchantById(int merchantId) {
        for (User user : users) {
            if (user instanceof Merchant && user.getUserId() == merchantId) {
                return (Merchant) user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public List<Merchant> getAllMerchants() {
        List<Merchant> merchants = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Merchant) {
                merchants.add((Merchant) user);
            }
        }
        return merchants;
    }

    public boolean suspendMerchant(int merchantId) {
        Merchant merchant = getMerchantById(merchantId);
        if (merchant != null) {
            merchant.setAccountStatus(AccountStatus.SUSPENDED);
            return true;
        }
        return false;
    }

    public boolean markMerchantInDefault(int merchantId) {
        Merchant merchant = getMerchantById(merchantId);
        if (merchant != null) {
            merchant.setAccountStatus(AccountStatus.IN_DEFAULT);
            return true;
        }
        return false;
    }

    public boolean restoreMerchantToNormal(int merchantId) {
        Merchant merchant = getMerchantById(merchantId);
        if (merchant != null) {
            merchant.setAccountStatus(AccountStatus.NORMAL);
            return true;
        }
        return false;
    }

    public boolean updateOutstandingBalance(int merchantId, BigDecimal newBalance) {
        Merchant merchant = getMerchantById(merchantId);
        if (merchant != null) {
            merchant.setOutstandingBalance(newBalance);
            return true;
        }
        return false;
    }


}