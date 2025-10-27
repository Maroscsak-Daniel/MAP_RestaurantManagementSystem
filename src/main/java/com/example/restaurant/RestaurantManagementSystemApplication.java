package com.example.restaurant;

import com.example.restaurant.model.*;
import com.example.restaurant.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class RestaurantManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner runTests(ApplicationContext context) {
        return args -> {

            System.out.println("\n--- TESTING RESTAURANT MANAGEMENT SYSTEM ---\n");

            // ===== PART 1: Daniel Maroscsak =====
            MenuItemService menuItemService = context.getBean(MenuItemService.class);
            ChefService chefService = context.getBean(ChefService.class);
            ServerService serverService = context.getBean(ServerService.class);
            OrderLineService orderLineService = context.getBean(OrderLineService.class);

            MenuItem pizza = new MenuItem("M1", "Pizza Margherita", 12.5);
            MenuItem pasta = new MenuItem("M2", "Pasta Carbonara", 14.0);
            menuItemService.addMenuItem(pizza);
            menuItemService.addMenuItem(pasta);

            System.out.println("All Menu Items:");
            menuItemService.getAllMenuItems().forEach(System.out::println);

            Chef chef = new Chef("C1", "Gordon Ramsay", "Italian Cuisine");
            Server server = new Server("S1", "Alice Johnson", "Waitress");
            chefService.addChef(chef);
            serverService.addServer(server);

            System.out.println("\nAll Chefs:");
            chefService.getAllChefs().forEach(System.out::println);

            System.out.println("\nAll Servers:");
            serverService.getAllServers().forEach(System.out::println);

            OrderLine ol1 = new OrderLine("OL1", "M1", 2);
            OrderLine ol2 = new OrderLine("OL2", "M2", 1);
            orderLineService.addOrderLine(ol1);
            orderLineService.addOrderLine(ol2);
            ArrayList<OrderLine> orderLines = new ArrayList<>();
            orderLines.add(ol1);
            orderLines.add(ol2);

            System.out.println("\nAll OrderLines:");
            orderLineService.getAllOrderLines().forEach(System.out::println);

            menuItemService.deleteMenuItem("M2");
            System.out.println("\nAfter deleting MenuItem M2:");
            menuItemService.getAllMenuItems().forEach(System.out::println);


            // ===== PART 2: Colleague’s Section =====
            CustomerService customerService = context.getBean(CustomerService.class);
            TableService tableService = context.getBean(TableService.class);
            OrderService orderService = context.getBean(OrderService.class);
            OrderAssignmentService assignmentService = context.getBean(OrderAssignmentService.class);
            BillService billService = context.getBean(BillService.class);

            OrderAssignment a1 = new OrderAssignment("A1", "O1", "S1");
            OrderAssignment a2 = new OrderAssignment("A2", "O2", "S2");
            assignmentService.add(a1);
            assignmentService.add(a2);
            ArrayList<OrderAssignment> orderss = new ArrayList<>();
            orderss.add(a1);
            orderss.add(a2);

            Order o1 = new Order("O1", "C1", "T1", "Open", orderLines, orderss);
            Order o2 = new Order("O2", "C2", "T2", "Closed", orderLines, orderss);
            orderService.add(o1);
            orderService.add(o2);
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(o1);
            orders.add(o2);



            Customer c1 = new Customer("C1", "John Smith", orders);
            Customer c2 = new Customer("C2", "Emily Davis", orders);
            customerService.add(c1);
            customerService.add(c2);

            Table t1 = new Table("T1", 1, "Free", orders);
            Table t2 = new Table("T2", 2, "Occupied", orders);
            tableService.add(t1);
            tableService.add(t2);

            Bill b1 = new Bill("B1", "O1", 25.50);
            Bill b2 = new Bill("B2", "O2", 38.75);
            billService.add(b1);
            billService.add(b2);

            System.out.println("\nAll Customers:");
            customerService.getAll().forEach(System.out::println);

            System.out.println("\nAll Tables:");
            tableService.getAll().forEach(System.out::println);

            System.out.println("\nFree Tables:");
            tableService.getFreeTables().forEach(System.out::println);

            System.out.println("\nAll Orders:");
            orderService.getAll().forEach(System.out::println);

            System.out.println("\nOrders by Customer C1:");
            orderService.getByCustomer("C1").forEach(System.out::println);

            System.out.println("\nAll Order Assignments:");
            assignmentService.getAll().forEach(System.out::println);

            System.out.println("\nAll Bills:");
            billService.getAll().forEach(System.out::println);

            tableService.setStatus("T1", "Occupied");
            orderService.setStatus("O1", "Closed");

            System.out.println("\nAfter Status Updates:");
            System.out.println("Updated Table T1: " + tableService.getById("T1"));
            System.out.println("Updated Order O1: " + orderService.getById("O1"));

            customerService.delete("C2");
            System.out.println("\nAfter deleting Customer C2:");
            customerService.getAll().forEach(System.out::println);

            System.out.println("\n--- END OF TEST ---\n");
        };
    }
}
