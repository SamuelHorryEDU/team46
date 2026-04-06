-- Create the database
-- Version 3
CREATE DATABASE IF NOT EXISTS ipos_sa_db;
USE ipos_sa_db;

-- 1. Users Table (Handles IPOS-SA-ACC)
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM(
      'Admin',
      'Director of Operations',
      'Merchant',
      'Senior accountant',
      'Accountant',
      'Warehouse employee',
      'Delivery department employee'
    ) NOT NULL,
    AccountNo VARCHAR(20) UNIQUE,
    AccountHolderName VARCHAR(100),
    ContactName VARCHAR(100),
    Address VARCHAR(255),
    Phone VARCHAR(20),
    CreditLimit DECIMAL(10,2),
    DiscountPlan ENUM('Fixed', 'Flexible', 'Variable'),
    DiscountRate VARCHAR(255),
    AccountStatus ENUM('Normal', 'Suspended', 'In_Default') DEFAULT 'Normal',
    OutstandingBalance DECIMAL(10,2) DEFAULT 0.00
);

-- 2. Catalogue Table (Handles IPOS-SA-CAT)
-- Updated ItemID to ProductID and added Category/IsActive to match contract
CREATE TABLE Catalogue (
    ProductID VARCHAR(50) PRIMARY KEY, 
    Name VARCHAR(255) NOT NULL,
    Description VARCHAR(255),
    Category VARCHAR(100), -- New: required by contract
    PackageType VARCHAR(50),
    Unit VARCHAR(50),
    UnitsInPack INT,
    PackageCost DECIMAL(10,2), -- Maps to unitPrice
    Availability INT DEFAULT 0,
    StockLimit INT DEFAULT 0,
    IsActive BOOLEAN DEFAULT TRUE -- New: maps to 'active' boolean
);

-- 3. Orders Table (Handles IPOS-SA-ORD)
-- Updated OrderID to VARCHAR to support "ORD-2026-00042" formats
CREATE TABLE Orders (
    OrderID VARCHAR(50) PRIMARY KEY,
    MerchantID INT,
    OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    TotalAmount DECIMAL(10,2),
    OrderStatus ENUM('ACCEPTED', 'PROCESSING', 'PACKED', 'DISPATCHED', 'DELIVERED', 'CANCELLED') DEFAULT 'ACCEPTED', -- Added CANCELLED
    EstimatedDelivery DATETIME, -- New: Separated for OrderConfirmation DTO
    DispatchDetails VARCHAR(255),
    FOREIGN KEY (MerchantID) REFERENCES Users(UserID)
);

-- 4. OrderItems Table (Line items for each order)
CREATE TABLE OrderItems (
    OrderItemID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID VARCHAR(50), -- Updated to match Orders PK
    ProductID VARCHAR(50), -- Updated to match Catalogue PK
    Quantity INT,
    UnitCost DECIMAL(10,2),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Catalogue(ProductID)
);

-- 5. Invoices_Payments Table (Handles billing)
CREATE TABLE Invoices_Payments (
    InvoiceID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID VARCHAR(50), -- Updated to match Orders PK
    IssueDate DATE,
    DueDate DATE,
    PaymentStatus ENUM('Pending', 'Paid', 'Overdue') DEFAULT 'Pending',
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);

-- 6. Payments Ledger (Handles IPOS-SA-ORD payment recording)
CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    MerchantID INT NOT NULL,
    PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    AmountPaid DECIMAL(10,2) NOT NULL,
    PaymentMethod ENUM('Bank Transfer', 'Credit Card', 'Cash', 'Cheque') NOT NULL,
    ReferenceNumber VARCHAR(100), -- E.g., the bank transaction ID
    FOREIGN KEY (MerchantID) REFERENCES Users(UserID)
);

-- 7. PU Applications Inbox (Handles IPOS-PU integration)
CREATE TABLE PU_Applications (
    ApplicationID VARCHAR(20) PRIMARY KEY, -- e.g., 'PU0001', 'PU0003'
    ApplicationType ENUM('Commercial', 'Non-Commercial') NOT NULL,
    ApplicationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    CompanyName VARCHAR(100), -- NULL if Non-Commercial
    ApplicantName VARCHAR(100),
    CompanyHouseReg VARCHAR(50), -- NULL if Non-Commercial
    Address VARCHAR(255),
    EmailAddress VARCHAR(100) NOT NULL,
    ApplicationStatus ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending'
);
