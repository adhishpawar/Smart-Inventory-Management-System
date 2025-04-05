# 🛒Smart Inventory Management System

A **console-based Inventory & Order Management System** built with Java, designed for both Admin and User roles. This project includes features like product management, user registration/login, and order processing. Ideal for educational and demo purposes.

---

## 🚀 Features

### 👤 User Authentication
- 🔐 Register (Admin/User)
- 🔑 Login with role-based redirection

### 🛍️ For Users
- 🔎 View All Products
- 🗂️ Search Products by Name or Category
- 🧾 Place Orders
- 📦 View Orders Placed

### 🛠️ For Admins
- ➕ Add Product
- 🔁 Update Product
- ❌ Delete Product
- 🔍 Search Product (by Name/Category)
- 📃 View All Inventory
- 📑 View All Orders (coming soon...)

---

## 🧑‍💻 Technologies Used

- **Java** (Core)
- **Object-Oriented Programming (OOP)**
- **JDBC** (for database operations)
- **MySQL** (backend database)
- **MVC Architecture**

---

## 📸 Sample Console Output

```bash
===== Inventory List =====
ID: 1 | Name: Laptop | Category: Electronics | Price: $65000.0 | Stock: 10
ID: 2 | Name: Keyboard | Category: Electronics | Price: $700.0 | Stock: 20

===== User Order List =====
Order ID: 101 | Product ID: 1 | Product Name: Laptop | Quantity: 2 | Order Date: 2025-04-01 15:30:00
```

## Project Structure
- src/
- ├── org.inventory/
- │   ├── Main.java
- │   ├── dao/
- │   │   └── OrderDAO.java
- |   │   └── InventoryDAO.java
- |   │   └── UserDAO.java
- │   ├── models/
- │   │   ├── Product.java
- │   │   └── User.java
- │   │   └── Order.java
- │   └── services/
- │       ├── AuthService.java
- │       └── InventoryService.java

