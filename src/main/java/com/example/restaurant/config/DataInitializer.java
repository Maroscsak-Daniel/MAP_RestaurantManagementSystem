package com.example.restaurant.config;

import com.example.restaurant.model.*;
import com.example.restaurant.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class    DataInitializer implements CommandLineRunner {

    private final CustomerService customerService;
    private final RestaurantTableService tableService;
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final OrderAssignmentService orderAssignmentService;
    private final MenuItemService menuItemService;
    private final ChefService chefService;
    private final ServerService serverService;
    private final BillService billService;

    public DataInitializer(
            CustomerService customerService,
            RestaurantTableService tableService,
            OrderService orderService,
            OrderLineService orderLineService,
            OrderAssignmentService orderAssignmentService,
            MenuItemService menuItemService,
            ChefService chefService,
            ServerService serverService,
            BillService billService
    ) {
        this.customerService = customerService;
        this.tableService = tableService;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.orderAssignmentService = orderAssignmentService;
        this.menuItemService = menuItemService;
        this.chefService = chefService;
        this.serverService = serverService;
        this.billService = billService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Prevent duplicate initialization
        if (!customerService.getAll().isEmpty()) {
            System.out.println("=== DB already initialized. Skipping ===");
            return;
        }

        System.out.println("=== INITIALIZING DATABASE ===");

        Random random = new Random();

        // -------------------- CUSTOMERS --------------------
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            customers.add(customerService.create(new Customer("Customer " + i)));
        }

        // -------------------- TABLES --------------------
        List<RestaurantTable> tables = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tables.add(tableService.create(new RestaurantTable(i, "free")));
        }

        // -------------------- CHEFS --------------------
        List<Chef> chefs = new ArrayList<>();
        String[] ranks = {"Junior", "Senior", "Head Chef"};
        for (int i = 1; i <= 10; i++) {
            chefs.add(chefService.create(new Chef("Chef " + i, ranks[i % 3])));
        }

        // -------------------- SERVERS --------------------
        List<Server> servers = new ArrayList<>();
        String[] shifts = {"Morning", "Evening", "Night"};
        for (int i = 1; i <= 10; i++) {
            servers.add(serverService.create(new Server("Server " + i, shifts[i % 3], random.nextInt(10))));
        }

        // -------------------- MENU ITEMS --------------------
        List<MenuItem> menuItems = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            menuItems.add(menuItemService.create(
                    new MenuItem(
                            "Item " + i,
                            "Description for item " + i,
                            5 + random.nextInt(20),
                            (i % 2 == 0 ? "Main" : "Starter"),
                            (i % 3 == 0 ? "Gluten" : "None")
                    )
            ));
        }

        // -------------------- ORDERS --------------------
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {

            Customer c = customers.get(random.nextInt(10));
            RestaurantTable t = tables.get(random.nextInt(10));

            Order order = new Order();
            order.setCustomer(c);
            order.setTable(t);
            order.setStatus(i % 2 == 0 ? "Pending" : "Completed");
            order.setPaymentMethod(i % 2 == 0 ? "Cash" : "Card");

            orders.add(orderService.create(order));
        }

        // -------------------- ORDER LINES --------------------
        for (Order o : orders) {
            for (int i = 0; i < 3; i++) {
                MenuItem item = menuItems.get(random.nextInt(10));

                OrderLine line = new OrderLine(
                        o,
                        item,
                        1 + random.nextInt(3),
                        i % 2 == 0 ? "None" : "Gluten"
                );

                orderLineService.create(line);
            }
        }

        // -------------------- ORDER ASSIGNMENTS --------------------
        for (Order o : orders) {

            Server s = servers.get(random.nextInt(10));

            OrderAssignment assignment = new OrderAssignment();
            assignment.setOrder(o);
            assignment.setStaffId("S" + s.getId()); // simple notation

            orderAssignmentService.create(assignment);
        }

        // -------------------- BILLS --------------------
        for (Order o : orders) {

            double total = 0;

            for (OrderLine line : orderLineService.getByOrder(o.getId())) {
                total += line.getMenuItem().getPrice() * line.getQuantity();
            }

            Bill bill = new Bill(o, total, o.getStatus().equals("Completed") ? "Paid" : "Unpaid");

            billService.create(bill);
        }

        System.out.println("=== DATABASE INITIALIZED WITH ALL ENTITIES ===");
    }
}
