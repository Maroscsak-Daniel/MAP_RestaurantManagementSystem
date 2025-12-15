package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.service.CustomerService;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.RestaurantTableService;
import com.example.restaurant.service.OrderLineService;
import com.example.restaurant.service.OrderAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final RestaurantTableService tableService;
    private final OrderLineService orderLineService;
    private final OrderAssignmentService assignmentService;

    public OrderController(OrderService orderService,
                           CustomerService customerService,
                           RestaurantTableService tableService,
                           OrderLineService orderLineService,
                           OrderAssignmentService assignmentService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.tableService = tableService;
        this.orderLineService = orderLineService;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String index(@RequestParam(required = false) String status,
                        @RequestParam(required = false, name = "customer") String customerName,
                        @RequestParam(required = false, name = "sort") String sortBy,
                        @RequestParam(required = false, name = "dir") String dir,
                        Pageable pageable,
                        Model model) {

        Page<Order> page = orderService.getAll(status, customerName, sortBy, dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("orders", page.getContent());

        List<String> statuses = Arrays.stream(OrderStatus.values()).map(Enum::name).collect(Collectors.toList());
        model.addAttribute("statuses", statuses);

        model.addAttribute("currentStatus", status == null ? "" : status);
        model.addAttribute("currentCustomer", customerName == null ? "" : customerName);
        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);

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
    public String create(@ModelAttribute Order order, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            orderService.create(order);
            redirectAttributes.addFlashAttribute("success", "Order created successfully.");
            return "redirect:/orders";
        } catch (Exception e) {
            model.addAttribute("order", order);
            model.addAttribute("customers", customerService.getAll());
            model.addAttribute("tables", tableService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orders/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        var order = orderService.getById(id);
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLineService.getByOrder(id));
        model.addAttribute("assignments", assignmentService.getByOrder(id));
        return "orders/details";
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
                         Model model,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            orderService.update(id, order);
            redirectAttributes.addFlashAttribute("success", "Order updated successfully.");
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
