package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
import com.example.restaurant.service.CustomerService;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.RestaurantTableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final RestaurantTableService tableService;

    public OrderController(OrderService orderService,
                           CustomerService customerService,
                           RestaurantTableService tableService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.tableService = tableService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "orders/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("tables", tableService.getAll());
        return "orders/form";
    }

    @PostMapping
    public String create(@ModelAttribute Order order, Model model) {
        try {
            orderService.create(order);
            return "redirect:/orders";
        } catch (Exception e) {
            model.addAttribute("order", order);
            model.addAttribute("customers", customerService.getAll());
            model.addAttribute("tables", tableService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orders/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getById(id));
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("tables", tableService.getAll());
        return "orders/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Order order,
                         Model model) {
        try {
            orderService.update(id, order);
            return "redirect:/orders";
        } catch (Exception e) {
            model.addAttribute("order", order);
            model.addAttribute("customers", customerService.getAll());
            model.addAttribute("tables", tableService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orders/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        try {
            orderService.delete(id);
            return "redirect:/orders";

        } catch (Exception e) {

            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("error",
                    "Cannot delete this order because it has a bill or other linked data.");

            // IMPORTANT: forward, NOT redirect
            return "orders/index";
        }
    }


}
