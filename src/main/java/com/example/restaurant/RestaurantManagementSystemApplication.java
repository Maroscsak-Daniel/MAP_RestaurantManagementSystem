package com.example.restaurant;

import com.example.restaurant.model.*;
import com.example.restaurant.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementSystemApplication.class, args);
    }

    /**
     * This method runs automatically when the app starts.
     * It's perfect for quick testing of services & repositories.
     */
    @Bean
    CommandLineRunner runTests(ApplicationContext context) {
        return args -> {

            System.out.println("\n--- 🧠 TESTING RESTAURANT MANAGEMENT SYSTEM ---\n");

            // 🔹 Get beans (Spring automatically creates them)
            MenuItemService menuItemService = context.getBean(MenuItemService.class);
            ChefService chefService = context.getBean(ChefService.class);
            ServerService serverService = context.getBean(ServerService.class);
            OrderLineService orderLineService = context.getBean(OrderLineService.class);

            // 🔹 Test MenuItem
            MenuItem pizza = new MenuItem("M1", "Pizza Margherita", 12.5);
            MenuItem pasta = new MenuItem("M2", "Pasta Carbonara", 14.0);
            menuItemService.addMenuItem(pizza);
            menuItemService.addMenuItem(pasta);

            System.out.println("All Menu Items:");
            menuItemService.getAllMenuItems().forEach(System.out::println);

            // 🔹 Test Staff (Chef + Server)
            Chef chef = new Chef("C1", "Gordon Ramsay", "Italian Cuisine");
            Server server = new Server("S1", "Alice Johnson", "Waitress");
            chefService.addChef(chef);
            serverService.addServer(server);

            System.out.println("\nAll Chefs:");
            chefService.getAllChefs().forEach(System.out::println);
            System.out.println("\nAll Servers:");
            serverService.getAllServers().forEach(System.out::println);

            // 🔹 Test OrderLine
            OrderLine ol1 = new OrderLine("OL1", "M1", 2);
            OrderLine ol2 = new OrderLine("OL2", "M2", 1);
            orderLineService.addOrderLine(ol1);
            orderLineService.addOrderLine(ol2);

            System.out.println("\nAll OrderLines:");
            orderLineService.getAllOrderLines().forEach(System.out::println);

            // 🔹 Test Deletion
            menuItemService.deleteMenuItem("M2");
            System.out.println("\nAfter deleting MenuItem M2:");
            menuItemService.getAllMenuItems().forEach(System.out::println);

            System.out.println("\n--- ✅ END OF TEST ---\n");
        };
    }
}
