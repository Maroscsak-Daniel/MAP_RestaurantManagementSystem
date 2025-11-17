package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
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
    }

    // GET /orders - list all
    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "order/index";
    }

    // GET /orders/new - show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        Order order = new Order();
        order.setStatus("Pending");
        order.setPaymentMethod("Cash");

        // IMPORTANT: ID LISTS MUST BE INITIALIZED
        order.setOrderLineIds(new ArrayList<>());
        order.setAssignmentIds(new ArrayList<>());

        model.addAttribute("order", order);
        return "order/form";
    }

    // POST /orders - create
    @PostMapping
    public String createOrder(@ModelAttribute Order order) {

        // Make sure lists are never null
        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());

        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        orderService.add(order);
        return "redirect:/orders";
    }

    // GET /orders/{id}/edit - form for editing
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Order order = orderService.getById(id);
        if (order == null) return "redirect:/orders";

        // Ensure non-null lists
        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());
        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        model.addAttribute("order", order);
        return "order/form";
    }

    // POST /orders/{id} - update existing
    @PostMapping("/{id}")
    public String updateOrder(@PathVariable String id, @ModelAttribute Order order) {

        order.setId(id); // Ensure ID remains consistent

        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());
        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        orderService.update(order);
        return "redirect:/orders";
    }

    // POST /orders/{id}/delete - delete
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
