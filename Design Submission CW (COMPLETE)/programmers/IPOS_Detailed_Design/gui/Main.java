package IPOS_Detailed_Design;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SAtoCA api = new SAtoCA();

        System.out.println("Auth: " + api.authenticateMerchant("merchant1", "correctPass"));
        System.out.println("Catalogue size: " + api.getCatalogue().size());
        System.out.println("Stock P001: " + api.checkStock("P001"));

        OrderConfirmation confirmation =
                api.placeOrder("M001", List.of(new OrderItem("P001", 2)));

        System.out.println("Order ID: " + confirmation.getOrderId());
        System.out.println("Order Status: " + api.getOrderStatus(confirmation.getOrderId()));
        System.out.println("Balance: " + api.getOutstandingBalance());

        ApplicationService applicationService = new ApplicationService();
        System.out.println("Application result: " +
                applicationService.submitCommercialApplication(
                        new Application("Pharma Ltd", "TAX123", "12 High Street")
                ));
    }
}