# Restaurant Management System – Daniel Maroscsak (Part 1/2)

## Overview
This document describes the part of the project implemented by **Daniel Maroscsak** as part of the Restaurant Management System assignment (Spring Boot Project 1).

The purpose of this phase was to build an **in-memory version** of the system, structured according to the **Model – Repository – Service – Controller** architecture.  
All data is stored using Java collections (Lists), without any database.

---

## Implemented Components

### Model Layer
- **MenuItem** – Represents a menu product (name, price).
- **OrderLine** – Represents a single order line (menu item and quantity).
- **Staff** – Abstract base class for all staff members (ID, name).
- **Chef** – Subclass of Staff representing kitchen staff with specialization.
- **Server** – Subclass of Staff representing front-of-house staff with designation.

### Repository Layer
In-memory CRUD repositories using Lists:
- MenuItemRepository
- OrderLineRepository
- StaffRepository
- ChefRepository
- ServerRepository

### Service Layer
Business logic classes that connect the controllers with the repositories:
- MenuItemService
- OrderLineService
- StaffService
- ChefService
- ServerService

### Controller Layer
REST controllers that provide CRUD endpoints for each entity:
- MenuItemController
- OrderLineController
- StaffController
- ChefController
- ServerController

---

## Package Structure
com.example.restaurant/
├── model/
│ ├── MenuItem.java
│ ├── OrderLine.java
│ ├── Staff.java
│ ├── Chef.java
│ └── Server.java
├── repository/
├── service/
├── controller/
└── RestaurantManagementSystemApplication.java

yaml
Copy code

---

## Testing
Testing was done through a `CommandLineRunner` inside  
`RestaurantManagementSystemApplication.java`, which automatically runs at startup.

### Example console output
--- TESTING RESTAURANT MANAGEMENT SYSTEM ---

All Menu Items:
MenuItem{id='M1', name='Pizza Margherita', price=12.5}
MenuItem{id='M2', name='Pasta Carbonara', price=14.0}

All Chefs:
Chef{id='C1', name='Gordon Ramsay', specialization='Italian Cuisine'}

All Servers:
Server{id='S1', name='Alice Johnson', designation='Waitress'}

All OrderLines:
OrderLine{id='OL1', menuItemId='M1', quantity=2.0}
OrderLine{id='OL2', menuItemId='M2', quantity=1.0}

After deleting MenuItem M2:
MenuItem{id='M1', name='Pizza Margherita', price=12.5}

--- END OF TEST ---

yaml
Copy code

---

## Summary
This part of the project covers the implementation of five model classes and their corresponding repository, service, and controller layers.  
It establishes the complete application structure for in-memory data management and prepares the system for later integration with persistent storage and extended business logic.
