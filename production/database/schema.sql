-- Create database
CREATE DATABASE IF NOT EXISTS ipos_sa_db;
USE ipos_sa_db;

-- Create Users table
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

-- Create Catalogue table
CREATE TABLE Catalogue (
  ItemID VARCHAR(20) PRIMARY KEY,
  Description VARCHAR(255) NOT NULL,
  PackageType VARCHAR(50),
  Unit VARCHAR(50),
  UnitsInPack INT,
  PackageCost DECIMAL(10,2),
  Availability INT DEFAULT 0, -- current stock level
  StockLimit INT DEFAULT 0 -- triggers low stock warning
);

-- Create Orders table
CREATE TABLE Orders (
  OrderID INT AUTO_INCREMENT PRIMARY KEY,
  MerchantID INT,
  OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
  TotalAmount DECIMAL(10,2),
  OrderStatus ENUM('Accepted', 'Processing', 'Dispatched', 'Delivered') DEFAULT 'Accepted',
  DispatcDetails VARCHAR(255),
  FOREIGN KEY (MerchantID) REFERENCES Users(UserID)
);

-- Create OrderItems table
CREATE TABLE OrderItems (
  OrderItemID INT AUTO_INCREMENT PRIMARY KEY,
  OrderID INT,
  ItemID VARCHAR(20),
  Quantity INT,
  UnitCost DECIMAL(10,2),
  FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
  FOREIGN KEY (ItemID) REFERENCES Catalogue(ItemID)
);

-- Create Invoices_Payments
CREATE TABLE Invoices_Payments (
  InvoiceID INT AUTO_INCREMENT PRIMARY KEY,
  OrderID INT,
  IssueDate DATE,
  DueDate DATE,
  PaymentStatus ENUM('Pending', 'Paid', 'Overdue') DEFAULT 'Pending',
  FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);
