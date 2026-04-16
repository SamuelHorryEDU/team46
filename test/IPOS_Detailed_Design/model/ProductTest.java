package IPOS_Detailed_Design.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void defaultConstructor_createsEmptyProduct() {
        Product product = new Product();
        assertNull(product.getProductId());
        assertNull(product.getName());
        assertNull(product.getPackageCost());
    }

    @Test
    void parameterisedConstructor_setsIdNameCostAvailability() {
        Product product = new Product("P001", "Paracetamol", new BigDecimal("4.99"), 120);
        assertEquals("P001", product.getProductId());
        assertEquals("Paracetamol", product.getName());
        assertEquals(new BigDecimal("4.99"), product.getPackageCost());
        assertEquals(120, product.getAvailability());
    }

    @Test
    void parameterisedConstructor_setsActiveTrueByDefault() {
        Product product = new Product("P001", "Paracetamol", new BigDecimal("4.99"), 120);
        assertTrue(product.isActive());
    }

    @Test
    void setProductId_updatesProductId() {
        Product product = new Product();
        product.setProductId("P002");
        assertEquals("P002", product.getProductId());
    }

    @Test
    void setName_updatesName() {
        Product product = new Product();
        product.setName("Ibuprofen");
        assertEquals("Ibuprofen", product.getName());
    }

    @Test
    void setPackageCost_updatesPackageCost() {
        Product product = new Product();
        product.setPackageCost(new BigDecimal("9.99"));
        assertEquals(new BigDecimal("9.99"), product.getPackageCost());
    }

    @Test
    void setAvailability_updatesAvailability() {
        Product product = new Product();
        product.setAvailability(50);
        assertEquals(50, product.getAvailability());
    }

    @Test
    void setActive_false_updatesActive() {
        Product product = new Product("P001", "Paracetamol", new BigDecimal("4.99"), 120);
        product.setActive(false);
        assertFalse(product.isActive());
    }
}