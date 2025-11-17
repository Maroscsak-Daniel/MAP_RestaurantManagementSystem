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

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "order/index";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable String id, Model model) {
        Order order = orderService.getById(id);
        if (order == null)
            return "redirect:/orders";

        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());
        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        model.addAttribute("order", order);
        return "order/details";
    }

    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        Order order = new Order();
        order.setStatus("Pending");
        order.setPaymentMethod("Cash");

        order.setOrderLineIds(new ArrayList<>());
        order.setAssignmentIds(new ArrayList<>());

        model.addAttribute("order", order);
        return "order/form";
    }

    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createOrder(@ModelAttribute Order order) {

        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());

        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        orderService.add(order);
        return "redirect:/orders";
    }

    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        Order order = orderService.getById(id);
        if (order == null)
            return "redirect:/orders";

        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());
        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        model.addAttribute("order", order);
        return "order/form";
    }

    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateOrder(@PathVariable String id, @ModelAttribute Order order) {

        order.setId(id);

        if (order.getOrderLineIds() == null)
            order.setOrderLineIds(new ArrayList<>());
        if (order.getAssignmentIds() == null)
            order.setAssignmentIds(new ArrayList<>());

        orderService.update(order);
        return "redirect:/orders";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
