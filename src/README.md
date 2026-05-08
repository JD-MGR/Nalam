# Nalam - Pharmacy Management System

Nalam is a comprehensive console-based Pharmacy Management System designed to streamline the operations of a pharmacy. It provides dedicated interfaces for both **Pharmacists** and **Customers**, ensuring efficient medicine inventory management and a smooth ordering process.

## 🚀 Features

### 🔐 User Authentication & Security
- **Secure Access:** Robust login and registration system.
- **Role-Based Access Control (RBAC):** Distinct dashboards for Pharmacists and Customers to ensure data integrity and security.

### 👨‍⚕️ Pharmacist Dashboard
- **Inventory Management:**
  - **Add Medicine:** Register new medicines with details like name, description, price, expiry date, and quantity.
  - **Remove Medicine:** Delete medicines from the inventory by name.
  - **Update Stock:** Easily increase the quantity of existing medicines.
  - **View Stock:** Comprehensive list of all medicines managed by the pharmacist.
- **User Management:** View a list of all registered customers.
- **Sales Tracking:** Monitor all orders placed through the system.

### 🛒 Customer Dashboard
- **Medicine Search:** Search for available medicines by name with partial matching.
- **Order Placement:** Seamlessly place orders for medicines with real-time stock validation and transaction safety.
- **Order History:** View personal order history, including medicine details, quantities, and total amounts.

## 🛠️ Tech Stack

- **Language:** Java (JDK 8+)
- **Database:** MySQL
- **Connectivity:** JDBC (Java Database Connectivity)
- **Architecture:** Layered Architecture (MVC-inspired)

## 🏗️ Architecture Overview

The project follows a clean, layered architecture to ensure maintainability and scalability:

- **View:** Handles user interaction and console input/output.
- **Controller:** Orchestrates the flow between the View and Service layers.
- **Service:** Contains business logic and validation rules.
- **Repository:** Manages data persistence and database operations.
- **Model:** Defines the data structures (POJOs) like `User`, `Medicine`, and `Order`.
- **DB:** Manages database connectivity.

## 📊 Database Schema

The system uses a MySQL database named `nalamDB` with the following key tables:

- **`users`:** Stores user profiles (ID, Name, Email, Password, Role).
- **`medicines`:** Stores medicine inventory (ID, Name, Description, Price, Expiry, Quantity, Pharmacist ID).
- **`orders`:** Stores transaction records (ID, User ID, Medicine ID, Quantity, Total Amount, Date).

## ⚙️ Setup & Installation

### Prerequisites
- Java Development Kit (JDK) installed.
- MySQL Server installed and running.
- MySQL Connector/J library added to the project.

### Steps
1. **Clone the Repository:**
   ```bash
   git clone <repository-url>
   ```

2. **Database Configuration:**
   - Create a database named `nalamDB` in MySQL.
   - Use the following schema to create tables (or allow the application logic to interact with your pre-defined tables):
     ```sql
     CREATE DATABASE nalamDB;
     USE nalamDB;

     CREATE TABLE users (
         id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(100),
         email VARCHAR(100) UNIQUE,
         password VARCHAR(100),
         role ENUM('CUSTOMER', 'PHARMACIST')
     );

     CREATE TABLE medicines (
         medicineId INT AUTO_INCREMENT PRIMARY KEY,
         medicineName VARCHAR(100),
         description TEXT,
         price DOUBLE,
         expiryDate LONG,
         quantity INT,
         pharmacistId INT,
         FOREIGN KEY (pharmacistId) REFERENCES users(id)
     );

     CREATE TABLE orders (
         orderId INT AUTO_INCREMENT PRIMARY KEY,
         userId INT,
         medicineId INT,
         medicineName VARCHAR(100),
         price DOUBLE,
         quantity INT,
         expiryDate LONG,
         totalAmount DOUBLE,
         orderDate LONG,
         FOREIGN KEY (userId) REFERENCES users(id),
         FOREIGN KEY (medicineId) REFERENCES medicines(medicineId)
     );
     ```

3. **Configure Connection:**
   - Update the database credentials in `src/com/zsgs/com.zsgs.nalam/db/NalamDB.java`:
     ```java
     private static String USERNAME = "your_username";
     private static String PASSWORD = "your_password";
     private static String URL = "jdbc:mysql://localhost:3306/nalamDB";
     ```

4. **Run the Application:**
   - Compile and run `com.ganesh.nalam.NalamApplication.java`.

## 📜 Coding Conventions
- **Transaction Safety:** Order placement uses database transactions (`setAutoCommit(false)`) to ensure atomic operations between stock updates and order logging.
- **Custom Exceptions:** Domain-specific errors are handled via `UserException`, `MedicineException`, and `OrderException`.
- **Validation:** Strict input and business rule validation in the Service layer.

---
Developed as a project for learning layered architecture and database integration in Java.
