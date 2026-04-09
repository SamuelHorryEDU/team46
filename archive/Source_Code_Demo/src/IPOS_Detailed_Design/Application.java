package IPOS_Detailed_Design;

public class Application {
    private String companyName;
    private String taxId;
    private String address;

    public Application() {
    }

    public Application(String companyName, String taxId, String address) {
        this.companyName = companyName;
        this.taxId = taxId;
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}