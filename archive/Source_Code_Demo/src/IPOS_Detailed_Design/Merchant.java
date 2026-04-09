package IPOS_Detailed_Design;

import java.math.BigDecimal;

public class Merchant {
    private String merchantId;
    private String username;
    private String password;
    private String companyName;
    private BigDecimal outstandingBalance;

    public Merchant() {
    }

    public Merchant(String merchantId, String username, String password, String companyName, BigDecimal outstandingBalance) {
        this.merchantId = merchantId;
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.outstandingBalance = outstandingBalance;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
}