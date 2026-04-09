package IPOS_Detailed_Design;

import java.math.BigDecimal;

public class Account {
    private String accountId;
    private String merchantId;
    private BigDecimal balance;
    private String status;

    public Account() {
    }

    public Account(String accountId, String merchantId, BigDecimal balance, String status) {
        this.accountId = accountId;
        this.merchantId = merchantId;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}