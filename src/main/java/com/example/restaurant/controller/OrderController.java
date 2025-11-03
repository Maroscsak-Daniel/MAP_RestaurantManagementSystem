package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.model.OrderLine;
import com.example.restaurant.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        // Date inițiale pentru testare
        if (orderService.getAll().isEmpty()) {
            // Se creează liste goale pentru OrderLine și OrderAssignment, conform modelului
            orderService.add(new Order("O100", "C001", "T1", "Completed", new ArrayList<OrderLine>(), new ArrayList<OrderAssignment>(), "Card"));
            orderService.add(new Order("O101", "C002", "T3", "Pending", new ArrayList<OrderLine>(), new ArrayList<OrderAssignment>(), "Cash"));
        }
    }

    // GET /orders - Afișează lista completă (GET all)
    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        // Returnează templates/order/index.html
        return "order/index";
    }

    // GET /orders/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Obiect Order gol, cu liste inițializate
        model.addAttribute("order", new Order(null, null, null, "Pending", new ArrayList<OrderLine>(), new ArrayList<OrderAssignment>(), "Cash"));
        // Returnează templates/order/form.html
        return "order/form";
    }

    // POST /orders - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createOrder(@ModelAttribute Order order) {
        // Asigurăm că listele nu sunt null înainte de salvare
        if (order.getOrderLines() == null) {
            order.setOrderLines(new ArrayList<>());
        }
        if (order.getAssignments() == null) {
            order.setAssignments(new ArrayList<>());
        }
        orderService.add(order);
        return "redirect:/orders";
    }

    // POST /orders/{id}/delete - Șterge obiectul
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}