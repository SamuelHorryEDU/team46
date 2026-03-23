-- Create the database
CREATE DATABASE IF NOT EXISTS ipos_sa_db;
USE ipos_sa_db;

-- 1. Users Table (Handles IPOS-SA-ACC)
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'Manager', 'Merchant') NOT NULL,
    ContactDetails VARCHAR(255),
    CreditLimit DECIMAL(10,2),
    DiscountPlan ENUM('Fixed', 'Flexible'),
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
    OrderStatus ENUM('ACCEPTED', 'PROCESSING', 'DISPATCHED', 'DELIVERED', 'CANCELLED') DEFAULT 'ACCEPTED', -- Added CANCELLED
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
