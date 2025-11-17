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
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllOrderLines(Model model) {
        model.addAttribute("orderlines", orderLineService.getAllOrderLines());
        return "orderline/index";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getOrderLineDetails(@PathVariable String id, Model model) {
        OrderLine line = orderLineService.getOrderLineById(id);
        if (line == null)
            return "redirect:/orderlines";

        model.addAttribute("orderline", line);
        return "orderline/details";
    }

    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("orderline", new OrderLine());
        return "orderline/form";
    }

    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createOrderLine(@ModelAttribute OrderLine orderLine) {
        orderLineService.addOrderLine(orderLine);
        return "redirect:/orderlines";
    }

    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        OrderLine orderLine = orderLineService.getOrderLineById(id);
        if (orderLine == null)
            return "redirect:/orderlines";

        model.addAttribute("orderline", orderLine);
        return "orderline/form";
    }

    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateOrderLine(@PathVariable String id, @ModelAttribute OrderLine orderLine) {

        orderLine.setId(id); // keep ID
        orderLineService.updateOrderLine(orderLine);
        return "redirect:/orderlines";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteOrderLine(@PathVariable String id) {
        orderLineService.deleteOrderLine(id);
        return "redirect:/orderlines";
    }
}
