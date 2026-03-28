package IPOS_Detailed_Design;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final List<Merchant> merchants = new ArrayList<>();

    public AccountService() {
        merchants.add(new Merchant("M001", "merchant1", "correctPass", "Pharma Ltd", new BigDecimal("250.00")));
    }

    public boolean authenticateMerchant(String username, String password) {
        for (Merchant merchant : merchants) {
            if (merchant.getUsername().equals(username) && merchant.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public BigDecimal getOutstandingBalance(String merchantId) {
        for (Merchant merchant : merchants) {
            if (merchant.getMerchantId().equals(merchantId)) {
                return merchant.getOutstandingBalance();
            }
        }
        return BigDecimal.ZERO;
    }
}