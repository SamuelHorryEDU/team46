package IPOS_Detailed_Design.service;

import IPOS_Detailed_Design.model.Application;
import IPOS_Detailed_Design.model.enums.ApprovalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationServiceTest {

    private ApplicationService service;

    @BeforeEach
    void setUp() {
        service = new ApplicationService();
    }

    @Test
    void submitCommercialApplication_nullApplication_returnsRejected() {
        assertEquals(ApprovalStatus.REJECTED, service.submitCommercialApplication(null));
    }

    @Test
    void submitCommercialApplication_nullCompanyName_returnsPending() {
        Application app = new Application();
        app.setCompanyName(null);
        app.setTaxId("TAX123");
        app.setAddress("123 Street");
        assertEquals(ApprovalStatus.PENDING, service.submitCommercialApplication(app));
    }

    @Test
    void submitCommercialApplication_blankCompanyName_returnsPending() {
        Application app = new Application();
        app.setCompanyName("   ");
        app.setTaxId("TAX123");
        app.setAddress("123 Street");
        assertEquals(ApprovalStatus.PENDING, service.submitCommercialApplication(app));
    }

    @Test
    void submitCommercialApplication_nullTaxId_returnsPending() {
        Application app = new Application();
        app.setCompanyName("Pharma Ltd");
        app.setTaxId(null);
        app.setAddress("123 Street");
        assertEquals(ApprovalStatus.PENDING, service.submitCommercialApplication(app));
    }

    @Test
    void submitCommercialApplication_blankTaxId_returnsPending() {
        Application app = new Application();
        app.setCompanyName("Pharma Ltd");
        app.setTaxId("   ");
        app.setAddress("123 Street");
        assertEquals(ApprovalStatus.PENDING, service.submitCommercialApplication(app));
    }

    @Test
    void submitCommercialApplication_nullAddress_returnsPending() {
        Application app = new Application();
        app.setCompanyName("Pharma Ltd");
        app.setTaxId("TAX123");
        app.setAddress(null);
        assertEquals(ApprovalStatus.PENDING, service.submitCommercialApplication(app));
    }

    @Test
    void submitCommercialApplication_blankAddress_returnsPending() {
        Application app = new Application();
        app.setCompanyName("Pharma Ltd");
        app.setTaxId("TAX123");
        app.setAddress("   ");
        assertEquals(ApprovalStatus.PENDING, service.submitCommercialApplication(app));
    }

    @Test
    void submitCommercialApplication_allFieldsValid_returnsApproved() {
        Application app = new Application();
        app.setCompanyName("Pharma Ltd");
        app.setTaxId("TAX123");
        app.setAddress("123 High Street");
        assertEquals(ApprovalStatus.APPROVED, service.submitCommercialApplication(app));
    }
}