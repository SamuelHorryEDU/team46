package IPOS_Detailed_Design;

import java.math.BigDecimal;

public class DiscountPlan {
    private String planName;
    private BigDecimal discountRate;

    public DiscountPlan() {
    }

    public DiscountPlan(String planName, BigDecimal discountRate) {
        this.planName = planName;
        this.discountRate = discountRate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
}