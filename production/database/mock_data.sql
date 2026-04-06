-- =========================================================================
-- IPOS-SA MOCK DATA SEED SCRIPT (UPDATED FOR V3 SCHEMA)
-- =========================================================================

-- 1. WIPE THE DATABASE CLEAN (Order matters due to Foreign Keys!)
DELETE FROM OrderItems;
DELETE FROM Invoices_Payments;
DELETE FROM Payments;
DELETE FROM Orders;
DELETE FROM PU_Applications;
DELETE FROM Catalogue;
DELETE FROM Users;

-- 2. INSERT STAFF ACCOUNTS
-- Note: Using the exact ENUM strings from your pasted schema ('Admin', 'Director of Operations', etc.)
INSERT INTO Users (Username, Password, Role) VALUES
('Sysdba', 'London_weighting', 'Admin'),
('manager', 'Get_it_done', 'Director of Operations'),
('accountant', 'Count_money', 'Accountant'),
('clerk', 'Paperwork', 'Accountant'),
('warehouse1', 'Get_a_beer', 'Warehouse employee'),
('warehouse2', 'Lot_smell', 'Warehouse employee'),
('delivery', 'Too_dark', 'Delivery department employee');

-- 3. INSERT MERCHANT ACCOUNTS (Handles IPOS-SA-ACC)
INSERT INTO Users (Username, Password, Role, AccountNo, AccountHolderName, ContactName, Address, Phone, CreditLimit, DiscountPlan, DiscountRate, AccountStatus, OutstandingBalance) VALUES
('northampton', 'city', 'Merchant', 'ACC-1001', 'CityPharmacy', 'Prof David Rhind', 'Northampton Square, London EC1V 0HB', '0207 040 8000', 10000.00, 'Fixed', '5%', 'Normal', 508.60),
('cosymed', 'bondstreet', 'Merchant', 'ACC-1002', 'Cosymed Ltd', 'Mr Alex Wright', '25, Bond Street, London WC1V 8LS', '0207 321 8001', 5000.00, 'Flexible', 'Volume Based', 'Normal', 376.00),
('hello', 'there', 'Merchant', 'ACC-1003', 'HelloPharmacy', 'Mr Bruno Wright', '12, Bond Street, London WC1V 9NS', '0207 321 8002', 5000.00, 'Flexible', 'Volume Based', 'In_Default', 450.00);

-- 4. INSERT CATALOGUE INVENTORY (Handles IPOS-SA-CAT)
-- Updated with 'Category' and 'IsActive' columns
INSERT INTO Catalogue (ProductID, Name, Description, Category, PackageType, Unit, UnitsInPack, PackageCost, Availability, StockLimit, IsActive) VALUES
('100 00001', 'Paracetamol', 'Pain relief medication', 'Analgesics', 'box', 'Caps', 20, 0.10, 10345, 300, TRUE),
('100 00002', 'Aspirin', 'Pain relief and anti-inflammatory', 'Analgesics', 'box', 'Caps', 20, 0.50, 12453, 500, TRUE),
('100 00004', 'Celebrex', 'caps 100 mg', 'Anti-inflammatory', 'box', 'Caps', 10, 10.00, 3420, 200, TRUE),
('200 00004', 'Iodine tincture', 'Antiseptic solution', 'First Aid', 'bottle', 'ml', 100, 0.30, 22134, 200, TRUE),
('400 00001', 'Vitamin C', 'Immune support', 'Vitamins', 'box', 'caps', 30, 1.20, 3258, 300, TRUE);

-- 5. INSERT ORDERS (Handles IPOS-SA-ORD)
-- Note: You will need to replace MerchantID with the actual generated UserID numbers if your Database tool resets auto-increment differently. 
-- Assuming northampton=8, cosymed=9, hello=10 based on the inserts above.
INSERT INTO Orders (OrderID, MerchantID, TotalAmount, OrderStatus, EstimatedDelivery, DispatchDetails) VALUES
('ORD-2026-00001', 8, 508.60, 'ACCEPTED', '2026-04-10 12:00:00', NULL),
('ORD-2026-00002', 9, 376.00, 'READY_TO_DISPATCH', '2026-04-08 15:00:00', 'Packed in Warehouse A'),
('ORD-2026-00003', 10, 450.00, 'DELIVERED', '2026-03-20 09:00:00', 'Signed by Bruno');

-- 6. INSERT ORDER ITEMS (Line items for the orders above)
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
('ORD-2026-00001', '100 00001', 50, 0.10),
('ORD-2026-00001', '100 00004', 50, 10.00),
('ORD-2026-00002', '200 00004', 100, 0.30);

-- 7. INSERT INVOICES & PAYMENTS (The new ledger tables!)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus) VALUES
('ORD-2026-00001', '2026-04-05', '2026-05-05', 'Pending'),
('ORD-2026-00003', '2026-03-15', '2026-04-15', 'Overdue');

-- Adding a sample recorded payment for Cosymed
INSERT INTO Payments (MerchantID, AmountPaid, PaymentMethod, ReferenceNumber) VALUES
(9, 100.00, 'Bank Transfer', 'BT-9938475');

-- 8. INSERT PU APPLICATIONS (The new integration holding pen!)
INSERT INTO PU_Applications (ApplicationID, ApplicationType, CompanyName, ApplicantName, CompanyHouseReg, Address, EmailAddress, ApplicationStatus) VALUES
('PU0001', 'Non-Commercial', NULL, 'Jane Doe', NULL, '10 Downing St', 'jane.doe@example.com', 'Pending'),
('PU0002', 'Commercial', 'Pond Pharmacy', 'Amy Pond', 'CRN-55421', 'Leadworth, UK', 'admin@pondpharma.co.uk', 'Pending');