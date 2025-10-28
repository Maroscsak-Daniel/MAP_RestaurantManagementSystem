# Restaurant Management System – Ingrid Matei (Part 2/2)

## Overview
This document describes the part of the project implemented by Ingrid
as part of the Restaurant Management System assignment (Spring Boot Project 1).

## Implemented Components
- Customer: Tracks customer details and their past Order history
- Table: Manages the physical dining area, primarily tracking the occupiedStatus (Free/Occupied)
- Order: The central transaction record. It aggregates items (OrderLine), staff (OrderAssignment), and links to the Customer, Table, and final Bill
- OrderAssignment: A crucial associative entity linking a specific Order to the respective Staff member (Server or Chef)
- Bill: Represents the financial record for an Order

### Service Layer
Business logic classes that connect the controllers with the repositories:

### Repository Layer
In-memory CRUD repositories using Lists:

### Service Layer
Business logic classes that connect the controllers with the repositories:

- TableService: 
    getFreeTables(): Filters the repository to return only tables currently marked as "Free"
    setStatus(id, status): Facilitates state transition (e.g., from ""Free"" to ""Occupied"") for a specific table
- OrderService
    getByStaff(staffId): Traverses the OrderAssignment relationship to retrieve all orders managed by a specific staff ID
    getByTable(tableId): Retrieves all orders placed at a specific table.
- BillService
    getByOrder(orderId): Provides direct lookup of the final bill document using its associated order ID

### Controller Layer
REST controllers that provide CRUD endpoints for each entity:

## Package Structure
com.example.restaurant/
├── model/
│ ├── Order.java
│ ├── OrderAssignment.java
│ ├── Bill.java
│ ├── Table.java
│ └── Customer.java
├── repository/
│ ├── OrderRepo.java
│ ├── OrderAssignmentRepo.java
│ ├── BillRepo.java
│ ├── TableRepo.java
│ └── CustomerRepo.java
├── service/
│ ├── OrderService.java
│ ├── OrderAssignmentService.java
│ ├── BillService.java
│ ├── TableService.java
│ └── CustomerService.java
├── controller/
│ ├── OrderController.java
│ ├── OrderAssignmentController.java
│ ├── BillController.java
│ ├── TableController.java
│ └── CustomerController.java
└── RestaurantManagementSystemApplication.java