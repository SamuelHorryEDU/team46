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

-- Staff Accounts
INSERT INTO Users (Username, Password, Role, AccountHolderName)
VALUES ('Sysdba',     'London_weighting', 'Admin',                           'System Administrator'),
       ('manager',    'Get_it_done',      'Director of Operations',          'Manager'),
       ('accountant', 'Count_money',      'Senior accountant',               'Senior Accountant'),
       ('clerk',      'Paperwork',        'Accountant',                      'Clerk'),
       ('warehouse1', 'Get_a_beer',       'Warehouse employee',              'Warehouse One'),
       ('warehouse2', 'Lot_smell',        'Warehouse employee',              'Warehouse Two'),
       ('delivery',   'Too_dark',         'Delivery department employee',    'Delivery');


-- Merchant Accounts
INSERT INTO Users (Username, Password, Role, AccountNo, AccountHolderName, ContactName,
                   Address, Phone, CreditLimit, DiscountPlan, DiscountRate, AccountStatus, OutstandingBalance)
VALUES
    ('city',    'northampton', 'Merchant', 'ACC0001', 'CityPharmacy',   'Prof David Rhind',
     'Northampton Square, London EC1V 0HB', '0207 040 8000',
     10000.00, 'Fixed',    '3',          'Normal', 0.00),

    ('cosymed', 'bondstreet',  'Merchant', 'ACC0002', 'Cosymed Ltd',    'Mr Alex Wright',
     '25, Bond Street, London WC1V 8LS',   '0207 321 8001',
     5000.00,  'Flexible', '<1000:0,1000-2000:1,2000+:2', 'Normal', 0.00),

    ('hello',   'there',       'Merchant', 'ACC0003', 'HelloPharmacy',  'Mr Bruno Wright',
     '12, Bond Street, London WC1V 9NS',   '0207 321 8002',
     5000.00,  'Flexible', '<1000:0,1000-2000:1,2000+:3', 'Normal', 0.00);

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
-- ORDERS
-- Uses subqueries for MerchantID so insertion order doesn't matter.
-- Scenario source: IPOS_SampleData_2026.pdf
-- ------------------------------------------------

-- Scenario 1: CityPharmacy, 20 Feb 2026, £508.60, DELIVERED 23 Feb
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0001',
        (SELECT UserID FROM Users WHERE Username = 'city'),
        '2026-02-20 09:00:00', 508.60, 'DELIVERED', '2026-02-23 15:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
    ('ORD-2026-0001', '100-00001', 10,  0.10),
    ('ORD-2026-0001', '100-00003', 20,  1.20),
    ('ORD-2026-0001', '200-00004', 12,  0.30),
    ('ORD-2026-0001', '200-00005', 10,  2.50),
    ('ORD-2026-0001', '300-00001', 10, 10.50),
    ('ORD-2026-0001', '300-00002', 20, 15.00),
    ('ORD-2026-0001', '400-00001', 20,  1.20),
    ('ORD-2026-0001', '400-00002', 20,  1.30);

-- Scenario 2: Cosymed Ltd, 25 Feb 2026, £376.00, DELIVERED 26 Feb
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0002',
        (SELECT UserID FROM Users WHERE Username = 'cosymed'),
        '2026-02-25 10:00:00', 376.00, 'DELIVERED', '2026-02-26 17:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
    ('ORD-2026-0002', '100-00001', 10,  0.10),
    ('ORD-2026-0002', '100-00003', 20,  1.20),
    ('ORD-2026-0002', '200-00005', 10,  2.50),
    ('ORD-2026-0002', '300-00002', 20, 15.00),
    ('ORD-2026-0002', '400-00002', 20,  1.30);

-- Scenario 3: HelloPharmacy, 25 Feb 2026, £259.10, DELIVERED 27 Feb
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0003',
        (SELECT UserID FROM Users WHERE Username = 'hello'),
        '2026-02-25 11:00:00', 259.10, 'DELIVERED', '2026-02-27 10:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
    ('ORD-2026-0003', '100-00003', 20,  1.20),
    ('ORD-2026-0003', '200-00004', 12,  0.30),
    ('ORD-2026-0003', '300-00001',  3, 10.50),
    ('ORD-2026-0003', '300-00002', 10, 15.00),
    ('ORD-2026-0003', '400-00001', 20,  1.20),
    ('ORD-2026-0003', '400-00002', 20,  1.30);

-- Scenario 4: Cosymed Ltd, 10 Mar 2026, £430.00, DELIVERED 12 Mar
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0004',
        (SELECT UserID FROM Users WHERE Username = 'cosymed'),
        '2026-03-10 09:00:00', 430.00, 'DELIVERED', '2026-03-12 11:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
    ('ORD-2026-0004', '200-00005', 10,  2.50),
    ('ORD-2026-0004', '300-00001', 10, 10.50),
    ('ORD-2026-0004', '300-00002', 20, 15.00);

-- Scenario 5: HelloPharmacy, 25 Mar 2026, £877.50 — DISPATCHED (active, visible in orders tab)
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0005',
        (SELECT UserID FROM Users WHERE Username = 'hello'),
        '2026-03-25 09:00:00', 877.50, 'DISPATCHED', '2026-03-27 10:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
    ('ORD-2026-0005', '100-00003', 20,  1.20),
    ('ORD-2026-0005', '100-00004',  5, 10.00),
    ('ORD-2026-0005', '100-00005',  5, 18.50),
    ('ORD-2026-0005', '100-00006',  5, 25.00),
    ('ORD-2026-0005', '100-00007', 10, 15.50),
    ('ORD-2026-0005', '300-00001', 10, 10.50),
    ('ORD-2026-0005', '300-00002', 20, 15.00),
    ('ORD-2026-0005', '400-00002', 20,  1.30);

-- Scenario 6: HelloPharmacy, 1 Apr 2026, £577.50 — PROCESSING (active, visible in orders tab)
INSERT INTO Orders (OrderID, MerchantID, OrderDate, TotalAmount, OrderStatus, EstimatedDelivery)
VALUES ('ORD-2026-0006',
        (SELECT UserID FROM Users WHERE Username = 'hello'),
        '2026-04-01 10:00:00', 577.50, 'PROCESSING', '2026-04-03 10:00:00');

INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitCost) VALUES
    ('ORD-2026-0006', '100-00003', 20,  1.20),
    ('ORD-2026-0006', '100-00004',  5, 10.00),
    ('ORD-2026-0006', '100-00005',  5, 18.50),
    ('ORD-2026-0006', '100-00006',  5, 25.00),
    ('ORD-2026-0006', '100-00007', 10, 15.50),
    ('ORD-2026-0006', '300-00001', 10, 10.50),
    ('ORD-2026-0006', '400-00002', 20,  1.30);

-- ------------------------------------------------
-- INVOICES
-- ------------------------------------------------

-- ORD1 (CityPharmacy) — PAID (payment received 15 March)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0001', '2026-02-20', '2026-03-31', 'Paid');

-- ORD2 (Cosymed) — PAID (payment received 15 March)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0002', '2026-02-25', '2026-03-31', 'Paid');

-- ORD3 (HelloPharmacy) — PAID (HelloPharmacy cleared balance 5 March)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0003', '2026-02-25', '2026-03-31', 'Paid');

-- ORD4 (Cosymed) — PAID (payment received 15 March)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0004', '2026-03-10', '2026-03-31', 'Paid');

-- ORD5 (HelloPharmacy) — OVERDUE (no payment since 5 March; due 31 March, now 15 days overdue)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0005', '2026-03-25', '2026-03-31', 'Overdue');

-- ORD6 (HelloPharmacy) — PENDING (due end of April)
INSERT INTO Invoices_Payments (OrderID, IssueDate, DueDate, PaymentStatus)
VALUES ('ORD-2026-0006', '2026-04-01', '2026-04-30', 'Pending');

-- ------------------------------------------------
-- PAYMENTS
-- ------------------------------------------------

-- Scenario 8: CityPharmacy cleared balance 15 March (bank transfer)
INSERT INTO Payments (MerchantID, PaymentDate, AmountPaid, PaymentMethod, ReferenceNumber)
VALUES ((SELECT UserID FROM Users WHERE Username = 'city'),
        '2026-03-15 10:00:00', 508.60, 'Bank Transfer', 'BACS-20260315-CITY');

-- Scenario 9: Cosymed Ltd cleared balance 15 March (company credit card)
INSERT INTO Payments (MerchantID, PaymentDate, AmountPaid, PaymentMethod, ReferenceNumber)
VALUES ((SELECT UserID FROM Users WHERE Username = 'cosymed'),
        '2026-03-15 11:00:00', 806.00, 'Credit Card', 'CC-20260315-COSY');

-- Scenario 7: HelloPharmacy cleared balance 5 March (last payment received)
INSERT INTO Payments (MerchantID, PaymentDate, AmountPaid, PaymentMethod, ReferenceNumber)
VALUES ((SELECT UserID FROM Users WHERE Username = 'hello'),
        '2026-03-05 09:00:00', 259.10, 'Credit Card', 'CC-20260305-HELLO');

-- ------------------------------------------------
-- UPDATE HELLOPHARMACY: outstanding balance + suspended status
-- (ORD5 invoice overdue 15+ days → Suspended per business rules)
-- ------------------------------------------------
UPDATE Users
SET OutstandingBalance = 1455.00,
    AccountStatus      = 'Suspended'
WHERE Username = 'hello';

-- ------------------------------------------------
-- PU APPLICATIONS (from the online portal)
-- ------------------------------------------------

INSERT INTO PU_Applications (ApplicationID, ApplicationType, ApplicantName, CompanyName, CompanyHouseReg, Address, EmailAddress, ApplicationStatus)
VALUES
    ('PU-0001', 'Commercial',     'Alice Green',  'Green Pharmacy Ltd',  'CH12345678', '10 Main St, London', 'alice@greenpharm.com',  'Pending'),
    ('PU-0002', 'Non-Commercial', 'Bob Normal',    NULL,                  NULL,         '5 Side St, Leeds',   'bob@email.com',         'Approved'),
    ('PU-0003', 'Commercial',     'Carol White',  'White Medical Ltd',   'CH87654321', '20 Park Rd, Bristol', 'carol@whitemed.com',   'Rejected');