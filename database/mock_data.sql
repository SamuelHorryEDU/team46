-- ================================================
-- IPOS-SA Test Data
-- Run this in MySQL Workbench after creating the schema
-- ================================================

USE ipos_sa_db;

-- ------------------------------------------------
-- USERS
-- Passwords are plain text here for testing only.
-- In the real app, Java should hash them with SHA-256
-- before storing. The TestConnection.java file shows how.
-- ------------------------------------------------

-- Admin account
INSERT INTO Users (Username, Password, Role, AccountHolderName)
VALUES ('admin', 'admin123', 'Admin', 'System Administrator');

-- Director of Operations (can restore In_Default accounts)
INSERT INTO Users (Username, Password, Role, AccountHolderName)
VALUES ('director', 'director123', 'Director of Operations', 'Mr Lancaster');

-- Warehouse employee
INSERT INTO Users (Username, Password, Role, AccountHolderName)
VALUES ('warehouse1', 'warehouse123', 'Warehouse employee', 'Bob Warehouse');

-- Accountant (records payments)
INSERT INTO Users (Username, Password, Role, AccountHolderName)
VALUES ('accountant1', 'account123', 'Accountant', 'Jane Accountant');

-- Merchant 1 - Normal account, no overdue
INSERT INTO Users (
    Username, Password, Role,
    AccountNo, AccountHolderName, ContactName,
    Address, Phone,
    CreditLimit, DiscountPlan, DiscountRate,
    AccountStatus, OutstandingBalance
) VALUES (
             'cosymed', 'merchant123', 'Merchant',
             'ACC-0001', 'Cosymed Ltd', 'John Smith',
             '3 High Level Drive, Sydenham, SE26 3ET', '0208 778 0124',
             5000.00, 'Fixed', '5',
             'Normal', 0.00
         );

-- Merchant 2 - Has outstanding balance but within credit limit
INSERT INTO Users (
    Username, Password, Role,
    AccountNo, AccountHolderName, ContactName,
    Address, Phone,
    CreditLimit, DiscountPlan, DiscountRate,
    AccountStatus, OutstandingBalance
) VALUES (
             'pharmaco', 'merchant123', 'Merchant',
             'ACC-0002', 'PharmaCo Ltd', 'Sarah Jones',
             '14 Market Street, London, EC1A 1BB', '0207 123 4567',
             8000.00, 'Flexible', '1000:1,2000:2,9999:3',
             'Normal', 1200.00
         );

-- Merchant 3 - Suspended (overdue 15-30 days) - for testing status logic
INSERT INTO Users (
    Username, Password, Role,
    AccountNo, AccountHolderName, ContactName,
    Address, Phone,
    CreditLimit, DiscountPlan, DiscountRate,
    AccountStatus, OutstandingBalance
) VALUES (
             'latepayer', 'merchant123', 'Merchant',
             'ACC-0003', 'Late Payer Ltd', 'Mike Late',
             '99 Debt Street, London, W1A 1AA', '0207 999 0000',
             3000.00, 'Fixed', '2',
             'Suspended', 850.00
         );

-- Merchant 4 - In Default (overdue >30 days)
INSERT INTO Users (
    Username, Password, Role,
    AccountNo, AccountHolderName, ContactName,
    Address, Phone,
    CreditLimit, DiscountPlan, DiscountRate,
    AccountStatus, OutstandingBalance
) VALUES (
             'defaulted', 'merchant123', 'Merchant',
             'ACC-0004', 'Default Pharmacy', 'Tom Default',
             '1 Bad Street, Manchester, M1 1AA', '0161 000 1111',
             2000.00, 'Fixed', '0',
             'In_Default', 1500.00
         );

-- ------------------------------------------------
-- CATALOGUE (Products)
-- ------------------------------------------------

INSERT INTO Catalogue (ProductID, Name, Description, Category, PackageType, Unit, UnitsInPack, PackageCost, Availability, StockLimit, IsActive)
VALUES
    ('100-00001', 'Paracetamol',          'Standard paracetamol 500mg',     'Analgesics',    'box',    'Caps', 20,  0.10,  10345, 300,  TRUE),
    ('100-00002', 'Aspirin',              'Aspirin 300mg tablets',           'Analgesics',    'box',    'Caps', 20,  0.50,  12453, 500,  TRUE),
    ('100-00003', 'Analgin',              'Analgin pain relief 500mg',       'Analgesics',    'box',    'Caps', 10,  1.20,  4235,  200,  TRUE),
    ('100-00004', 'Celebrex 100mg',       'Celebrex capsules 100mg',        'Anti-inflam',   'box',    'Caps', 10,  10.00, 3420,  200,  TRUE),
    ('100-00005', 'Celebrex 200mg',       'Celebrex capsules 200mg',        'Anti-inflam',   'box',    'Caps', 10,  18.50, 1450,  150,  TRUE),
    ('100-00006', 'Retin-A Tretin 30g',   'Retin-A Tretinoin cream 30g',    'Dermatology',   'box',    'Caps', 20,  25.00, 2013,  200,  TRUE),
    ('100-00007', 'Lipitor 20mg',         'Lipitor tablets 20mg',           'Cholesterol',   'box',    'Caps', 30,  15.50, 1562,  200,  TRUE),
    ('100-00008', 'Claritin CR 60g',      'Claritin extended release 60g',  'Allergy',       'box',    'Caps', 20,  19.50, 2540,  200,  TRUE),
    ('200-00004', 'Iodine Tincture',      'Iodine tincture antiseptic',     'Antiseptics',   'bottle', 'ml',  100,  0.30,  2213,  200,  TRUE),
    ('200-00005', 'Rhynol',               'Rhynol nasal solution 200ml',    'ENT',           'bottle', 'ml',  200,  2.50,  1908,  300,  TRUE),
    ('300-00001', 'Ospen',                'Ospen antibiotic 500mg',         'Antibiotics',   'box',    'Caps', 20,  10.50, 809,   200,  TRUE),
    ('300-00002', 'Amopen',               'Amoxicillin 500mg capsules',     'Antibiotics',   'box',    'Caps', 30,  15.00, 1340,  300,  TRUE),
    ('400-00001', 'Vitamin C',            'Vitamin C 1000mg tablets',       'Vitamins',      'box',    'Caps', 30,  1.20,  3258,  300,  TRUE),
    ('400-00002', 'Vitamin B12',          'Vitamin B12 1000mcg tablets',    'Vitamins',      'box',    'Caps', 30,  1.30,  2673,  300,  TRUE),
-- This one is below stock limit — to test low stock alerts
    ('100-00009', 'Ibuprofen 400mg',      'Ibuprofen anti-inflammatory',    'Analgesics',    'box',    'Caps', 24,  0.80,  50,    300,  TRUE);

-- ------------------------------------------------
-- ORDERS (for Merchant 1 - UserID 5 = cosymed)
-- ------------------------------------------------

-- Order 1 - Delivered and paid
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0001', 5, '2026-01-12 10:30:00', 508.60, 'DELIVERED', '2026-01-14 12:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
                                                                    ('ORD-2026-0001', '100-00001', 10, 0.10),
                                                                    ('ORD-2026-0001', '100-00003', 20, 1.20),
                                                                    ('ORD-2026-0001', '200-00004', 20, 0.30),
                                                                    ('ORD-2026-0001', '200-00005', 10, 2.50),
                                                                    ('ORD-2026-0001', '300-00001', 10, 10.50),
                                                                    ('ORD-2026-0001', '300-00002', 20, 15.00),
                                                                    ('ORD-2026-0001', '400-00001', 20, 1.20),
                                                                    ('ORD-2026-0001', '400-00002', 20, 1.30);

-- Order 2 - Processing
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus)
VALUES ('ORD-2026-0002', 5, '2026-03-15 14:00:00', 320.00, 'PROCESSING');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
                                                                    ('ORD-2026-0002', '100-00004', 10, 10.00),
                                                                    ('ORD-2026-0002', '100-00007', 14, 15.50);

-- Order 3 - Accepted (brand new)
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus)
VALUES ('ORD-2026-0003', 5, NOW(), 75.00, 'ACCEPTED');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
                                                                    ('ORD-2026-0003', '400-00001', 25, 1.20),
                                                                    ('ORD-2026-0003', '400-00002', 30, 1.30);

-- ------------------------------------------------
-- INVOICES
-- ------------------------------------------------

-- Invoice for Order 1 - PAID
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0001', '2026-01-12', '2026-02-12', 'Paid');

-- Invoice for Order 2 - PENDING
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0002', '2026-03-15', '2026-04-15', 'Pending');

-- Invoice for Order 3 - PENDING
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0003', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'Pending');

-- Overdue invoice for the suspended merchant (latepayer, UserID 7)
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus)
VALUES ('ORD-2026-0010', 7, '2026-02-01 09:00:00', 850.00, 'DELIVERED');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost)
VALUES ('ORD-2026-0010', '100-00005', 20, 18.50), ('ORD-2026-0010', '300-00002', 30, 15.00);

-- This invoice is overdue by ~20 days to trigger SUSPENDED status
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0010', '2026-02-01', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 'Overdue');

-- ------------------------------------------------
-- PAYMENTS
-- ------------------------------------------------

-- Payment recorded for Order 1 (cosymed paid in full)
INSERT INTO Payments (MerchantID, PaymentDate, AmountPaid, PaymentMethod, ReferenceNumber)
VALUES (5, '2026-01-20 11:00:00', 508.60, 'Bank Transfer', 'BACS-20260120-001');

-- ------------------------------------------------
-- PU APPLICATIONS (from the online portal)
-- ------------------------------------------------

INSERT INTO PU_Applications (ApplicationID, ApplicationType, ApplicantName, CompanyName, CompanyHouseReg, Address, EmailAddress, ApplicationStatus)
VALUES
    ('PU-0001', 'Commercial',     'Alice Green',  'Green Pharmacy Ltd',  'CH12345678', '10 Main St, London', 'alice@greenpharm.com',  'Pending'),
    ('PU-0002', 'Non-Commercial', 'Bob Normal',    NULL,                  NULL,         '5 Side St, Leeds',   'bob@email.com',         'Approved'),
    ('PU-0003', 'Commercial',     'Carol White',  'White Medical Ltd',   'CH87654321', '20 Park Rd, Bristol', 'carol@whitemed.com',   'Rejected');