package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "order/index"; // → templates/order/index.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order("", "", "", "Open", null, null));
        return "order/form"; // → templates/order/form.html
    }

    @PostMapping
    public String addOrder(@ModelAttribute Order order) {
        orderService.add(order);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable String id, @RequestParam String status) {
        orderService.setStatus(id, status);
        return "redirect:/orders";
    }
}
