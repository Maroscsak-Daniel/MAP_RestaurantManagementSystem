# MAP_RestaurantManagementSystem — Full Project Documentation (Ingrid)

Date: 2025-12-16

This README documents how the Restaurant Management System (Spring Boot) satisfies the technical requirements provided for Iteration 5 (sorting, filtering, validations, Thymeleaf UI, MySQL persistence) and explains architecture, business logic, data flow, templates, tests and how to run and debug the application.

Contents
- Purpose and scope
- How the technical requirements are mapped to the codebase
- Architecture and important packages
- Entity-by-entity summary (model, validations, business logic)
- Sorting, filtering and pagination (how it works)
- Database integration and migrations
- Thymeleaf UI details (forms, validation messages, layout)
- Controllers / Endpoints overview
- Running, testing and troubleshooting
- Expected outputs / examples

---

Purpose and scope
-----------------
This application is a Spring Boot web application (Thymeleaf front-end) that manages restaurant data (orders, bills, menu items, staff, tables, order lines, assignments, customers). It uses Spring Data JPA backed by MySQL and aims to meet the Iteration 5 requirements:
- server-side sorting and filtering for every main entity,
- backend validation and business rules,
- full CRUD via Thymeleaf pages,
- consistent UI (Bootstrap) and visible validation/errors,
- MySQL persistence (no in-memory final storage).

How the requirements were implemented
-------------------------------------
1) Sorting for all entities
- Each index page (e.g., `/bills`, `/orders`, `/menu`, `/customers`, `/chefs`, `/servers`, `/tables`, `/orderlines`, `/assignments`) exposes at least two sortable attributes.
- Sorting is implemented server-side using Spring Data `Sort` together with `Pageable` (see services `*Service.getAllPaged(...)` or `getAll(...)` that override `pageable` with a `PageRequest` containing requested sort).
- Users can sort by clicking table headers (links that include `sort` and `dir` query params) or via the sort control in the filter card (same page).

2) Filtering for all entities
- Each index page has a filter card with at least two criteria combined (text inputs, select boxes, or numeric ranges). Examples:
  - `bills`: filter by `status` (enum) and `totalPrice` range (`min`/`max`).
  - `orders`: filter by `status` and `customer` name.
  - `menu`: filter by `name`, `category` and price range.
  - `customers`: filter by `name` and `minOrders`.
- Filtering is enforced server-side in service layer (repository methods or `Specification` where appropriate). Filter and sort parameters are preserved in the model and the filter form so user input persists after submit.

3) Database integration & validations
- The application uses Spring Data JPA + MySQL. Entity classes map to DB tables and use standard JPA annotations.
- Validation annotations (@NotNull, @Size, @Min, etc.) are applied on DTO/entity fields where appropriate. Controllers that accept form input use `@Valid` with `BindingResult` to detect and display validation messages in the UI.
- Business validations (beyond field-level) are enforced in Services (e.g., BillService, OrderLineService, OrderAssignmentService) and throw meaningful exceptions which controllers translate to user-friendly messages.
- There is no final JSON/in-memory-only option in Iteration 5 — the app persists to MySQL.

4) Thymeleaf UI
- All functions are accessible through Thymeleaf pages.
- Consistent layout: a fixed-top navbar and content cards (matching `bills` layout as the reference) across entities.
- Filter forms are on the list pages, errors shown as alert banners and per-field messages next to inputs.
- Simple CSS via `src/main/resources/static/css/app.css` and Bootstrap CDN are used.

5) Iterations 1–4 requirements
- All entities required by project UML exist and are CRUD-manageable through the UI with sort+filter+validation.

Architecture & package layout
-----------------------------
- `com.example.restaurant.model` — JPA entity classes, enums (OrderStatus, PaymentStatus, PaymentMethod, etc.).
- `com.example.restaurant.repository` — Spring Data JPA repositories (extend `JpaRepository`).
- `com.example.restaurant.service` — Business logic and transactional operations. Services implement filtering/pagination/sorting and business rules.
- `com.example.restaurant.controller` — Spring MVC controllers (Thymeleaf views). They handle `@Valid` form submissions and return model attributes to templates.
- `src/main/resources/templates` — Thymeleaf templates organized per entity: `bills/`, `orders/`, `menu/`, `orderlines/`, `assignments/`, `customers/`, `chefs/`, `servers/`, `tables/`.

Entity-by-entity summary (model, validations, business logic)
-----------------------------------------------------------
Notes below describe the important business rules that were implemented in services.

1) Bill
- Model highlights: `id`, `order` (many-to-one), `totalPrice`, `paymentStatus` (enum: UNPAID/PAID).
- Validations: `order` must be provided; `totalPrice >= 0`.
- Business rules (BillService):
  - A Bill must be linked to an existing Order.
  - An Order can have at most one Bill (duplicate prevented).
  - Payment status cannot be set to PAID unless the linked Order has status COMPLETED.
  - Delete: cannot delete a PAID Bill. Also, Bill deletion can be blocked when the associated order still has related data (lines/assignments) unless the order is cancelled (business rule enforced in `delete`).
- UI: `bills/index` supports filtering by `paymentStatus` and `totalPrice` range, sorting on `id`, `totalPrice`, `paymentStatus`. Create/edit forms show validation errors next to fields.

2) Order
- Model: `id`, `status` (enum), `paymentMethod` (enum), `customer`, `table` (restaurant table), associations to `orderLines`, `assignments`, and possibly a `bill`.
- Validations: required `customer` and `table` in create/edit depending on flow; status must be a valid enum.
- Business rules (OrderService):
  - Server-side filtering and sorting implemented (status, customer name, etc.).
  - On delete, service deletes related OrderLines, Assignments and any associated Bill (cascade behavior implemented explicitly in `delete`). This follows your request: when an Order is deleted, its Bill is removed automatically.
- UI: `orders/index` supports filtering by `status` and `customer`, sorting (id, status, paymentMethod). `orders/form` uses select fields for `customer` and `table`.

3) OrderLine
- Model: `id`, `order` (relation), `menuItem`, `quantity`, `allergens`.
- Validations: `orderId` required, `quantity` must be positive.
- Business rules (OrderLineService):
  - Cannot create/update/delete OrderLines if the Order has a PAID Bill (protects finalized transactions).
  - The service resolves `orderId` and `menuItemId` to managed entities; if references are missing an informative error is raised.
- UI: `orderlines/index` supports filtering by `orderId` and menu item name, sorting by id/quantity. Create/edit forms provide selects for existing Orders and MenuItems. Details page uses standard card layout; Edit button can be hidden if requested.

4) OrderAssignment
- Model: `id`, `order` (relation), `staffId` (string or id of staff assigned).
- Validations: `order` required, `staffId` required and validated on the service side (must correspond to existing staff when required).
- Business rules (OrderAssignmentService):
  - Prevent creating/modifying assignments when the linked Order has a PAID Bill.
  - Prevent deleting an assignment if the related Order is COMPLETED (user requested this behavior). A clear message is thrown from service and presented to user.
- UI: `assignments/index` supports filtering by order and staff; create/edit forms expose order select and staff input.

5) MenuItem
- Model: `id`, `name`, `description`, `price`, `category`, `allergens`.
- Validations: `name` required, `price >= 0` required, `category` limited.
- Business rules (MenuItemService):
  - Cannot delete MenuItem when it is referenced by existing OrderLines — service throws an exception explaining how many order lines reference it.
- UI: `menu/index` supports filtering (name, category, price range), sorting (id, price). Forms display field validation messages.

6) Customer
- Model: `id`, `name`, `orders` relation.
- Business rules: cannot delete Customer with existing Orders — service enforces this and returns informative error.
- UI: `customers/index` supports filtering by name and minOrders; sorting by id and name.

7) Chef, Server
- Simple staff entities: CRUD allowed. Index pages support filtering (name/rank for Chef, shift/experience for Server) and sorting on at least two attributes.
- Deletion is straightforward (service delete) unless business rules are later introduced.

8) RestaurantTable
- Model: `id`, `number`, `occupiedStatus`, `orders`.
- Filtering and sorting available by number/status and id.

Sorting / Filtering / Pagination design
--------------------------------------
- UI: each index page has a filter card at the top. The card contains the form fields for the filters plus sort selector and direction (`dir`). Values are preserved after submission using model attributes.
- Controllers accept query parameters (`sort`, `dir`, and the filter params) and pass them to the relevant service.
- Services build either `Specification<T>` (for complex or enum filters) or call repository methods with a `PageRequest` that includes sort.
- Pagination is provided via Spring Data `Page<T>` objects. The index template renders pagination controls using `page.number`, `page.totalPages`, and uses links that preserve current filters and sort params.

Database integration
--------------------
- Spring Data JPA is used with MySQL datasource (configured in `src/main/resources/application.properties`).
- Entities use JPA annotations (`@Entity`, `@ManyToOne`, `@OneToMany`, etc.). Repositories extend `JpaRepository` and also contain custom query methods used by services (e.g., `findByNameContainingIgnoreCase`, `findByOrder_IdAndMenuItem_NameContainingIgnoreCase`, etc.).
- Business rules that require checking related rows (e.g., preventing deletion of MenuItem when order lines exist) use repository queries to count references.

Thymeleaf usage and templates
----------------------------
- Every entity has three typical pages:
  - `index.html` — list with filter card, table with sortable headers and actions (Details, Edit, Delete).
  - `form.html` — create & edit forms (we use separate create/edit forms in the same template to avoid Thymeleaf parser ternary issues). Forms use `th:object` and `th:field` so validation messages map correctly to fields.
  - `details.html` — standardized card layout showing key fields; action buttons (Back, optional Edit/Delete) implemented per-entity as requested.
- Validation messages use `th:if="${#fields.hasErrors('fieldName')}"` and `th:errors` to display per-field errors. Global errors are shown as alert banners at top.
- Sorting arrows in table headers show the current direction using `▲`/`▼` and the header links toggle `dir`.

Controllers and endpoints (high level)
--------------------------------------
- `/bills` — list, create (`/bills/new` -> POST `/bills`), edit (`/bills/{id}/edit` -> POST `/bills/{id}`), details (`/bills/{id}`), delete (POST `/bills/{id}/delete`).
- `/orders` — list, create, edit, details, delete (same patterns as bills).
- `/orderlines` — list, create, edit, details, delete.
- `/assignments` — list, create, edit, details, delete.
- `/menu` — list, create, edit, details, delete.
- `/customers`, `/chefs`, `/servers`, `/tables` — standard CRUD + filtering/sorting.

Running and testing the application
-----------------------------------
Prerequisites
- Java 17+ (project runs under the JDK specified in Gradle wrapper, verify your local JDK compatibility).
- MySQL server running and a database configured for the application.
- Gradle (wrapper provided) or use `./gradlew` in the project root.

Configuration
- Edit `src/main/resources/application.properties` or provide environment variables for the datasource:
```
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=youruser
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
- If you prefer a different port: add `--server.port=8081` on bootRun or set `server.port` in properties.

Build & Run
1) Build and run tests:
```powershell
./gradlew clean test --info
```
2) Start application locally:
```powershell
./gradlew bootRun
# or to use another port
./gradlew bootRun --args='--server.port=8081'
```
3) Open browser and navigate to `http://localhost:8080/bills` (or `/orders`, `/menu` etc.)

Manual testing notes (what to check)
- Create Order -> Use selects to pick Customer and Table. On save you should be redirected to `/orders` and see a success alert.
- Create OrderLine for an Order -> the Order and MenuItem selects should contain existing ids/names.
- Create Bill -> you must select an existing Order; if you try to set Payment to PAID while the Order status is not COMPLETED, the server will reject and the form will show an error next to `paymentStatus`.
- Delete Order -> deletes related OrderLines, Assignments and the Bill (if exists). The UI will redirect to `/orders`.
- Attempt to delete MenuItem which is used in an OrderLine -> service throws and the UI shows an error message.

Testing & troubleshooting
- If `./gradlew bootRun` fails with "Port 8080 already in use", check the process listening on 8080 and stop it or run on another port. Example (PowerShell):
```powershell
Get-NetTCPConnection -LocalPort 8080 | Select-Object LocalAddress,LocalPort,OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess
Stop-Process -Id <PID> -Force
```
- If database errors occur (driver missing / url wrong), verify `application.properties` and that MySQL driver dependency is present in `build.gradle` (this repo uses Spring Boot starters which include the driver when configured).

Developer notes & design decisions
----------------------------------
- Templates use separated create/edit forms to avoid complex Thymeleaf ternary URL expressions. This simplifies validation flow and avoids duplicate `id` attributes in the DOM.
- Sorting is performed in service layer to allow combining `Specification` (filters) with requested `Sort` safely; if `sort` is provided, service overrides the pageable sort with a `PageRequest` using requested `sort` and `dir`.
- Business logic is kept in services (transactional where needed) and controllers only handle request/response and validation binding.

Expected outputs (examples)
- Creating a Bill without selecting an Order:
  - The form re-displays and shows an error near the Order select: "Please select an order." and the creation is not persisted.
- Setting Bill payment to PAID while Order status is not COMPLETED:
  - Server rejects with illegal state message: "Cannot mark bill as PAID when order is not completed." shown next to `paymentStatus`.
- Deleting an Order with related OrderLines/Assignments:
  - OrderService deletes lines and assignments and any associated Bill. After successful deletion, you are redirected to `/orders` and a success alert is shown.

How services interact with the database
--------------------------------------
- Controllers invoke Service methods. Services use Spring Data repositories which translate calls to SQL executed against MySQL.
- Example flow (create Bill):
  1) Controller receives `@Valid Bill` and BindingResult.
  2) Controller calls `billService.create(bill)`.
  3) `BillService.create` validates business constraints (order exists, no duplicate bill, price >= 0, PAID allowed only for COMPLETED orders).
  4) Service uses `BillRepository.save(bill)` to persist. JPA handles the SQL insert.
  5) Controller redirect to `/bills` on success.

Final notes
-----------
- This README documents how each requirement from the supplied list is satisfied and how the code implements sorting, filtering, validation, persistence and UI integration.
