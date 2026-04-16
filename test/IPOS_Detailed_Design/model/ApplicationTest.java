package IPOS_Detailed_Design.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void defaultConstructor_createsEmptyApplication() {
        Application app = new Application();
        assertNull(app.getCompanyName());
        assertNull(app.getTaxId());
        assertNull(app.getAddress());
    }

    @Test
    void parameterisedConstructor_setsAllFields() {
        Application app = new Application("Pharma Ltd", "TAX123", "123 High Street");
        assertEquals("Pharma Ltd", app.getCompanyName());
        assertEquals("TAX123", app.getTaxId());
        assertEquals("123 High Street", app.getAddress());
    }

    @Test
    void setCompanyName_updatesCompanyName() {
        Application app = new Application();
        app.setCompanyName("Pharma Ltd");
        assertEquals("Pharma Ltd", app.getCompanyName());
    }

    @Test
    void setTaxId_updatesTaxId() {
        Application app = new Application();
        app.setTaxId("TAX123");
        assertEquals("TAX123", app.getTaxId());
    }

    @Test
    void setAddress_updatesAddress() {
        Application app = new Application();
        app.setAddress("123 High Street");
        assertEquals("123 High Street", app.getAddress());
    }
}