package com.example.restaurant.controller;

import com.example.restaurant.model.OrderLine;
import com.example.restaurant.service.OrderLineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderlines")
public class OrderLineController {

    private final OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
        // Date inițiale in-memory pentru testare (opțional, dar recomandat)
        if (orderLineService.getAllOrderLines().isEmpty()) {
            orderLineService.addOrderLine(new OrderLine("L01", "M001", 2.0, "Fără ceapă"));
            orderLineService.addOrderLine(new OrderLine("L02", "M002", 1.0, "Cu extra dressing"));
        }
    }

    // 1. GET /orderlines - Afișează lista completă (GET all)
    @GetMapping
    public String getAllOrderLines(Model model) {
        model.addAttribute("orderlines", orderLineService.getAllOrderLines());
        // Returnează templates/orderline/index.html
        return "orderline/index";
    }

    // 2. GET /orderlines/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("orderLine", new OrderLine());
        // Returnează templates/orderline/form.html
        return "orderline/form";
    }

    // 3. POST /orderlines - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createOrderLine(@ModelAttribute OrderLine orderLine) {
        orderLineService.addOrderLine(orderLine);
        // Redirecționează către lista actualizată
        return "redirect:/orderlines";
    }

    // 4. POST /orderlines/{id}/delete - Șterge obiectul (DELETE)
    @PostMapping("/{id}/delete")
    public String deleteOrderLine(@PathVariable String id) {
        orderLineService.deleteOrderLine(id);
        // Redirecționează către lista actualizată
        return "redirect:/orderlines";
    }
}