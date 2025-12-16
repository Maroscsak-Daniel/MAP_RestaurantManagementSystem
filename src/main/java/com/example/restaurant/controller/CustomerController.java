package com.example.restaurant.controller;

import com.example.restaurant.model.Customer;
import com.example.restaurant.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String listCustomers(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer minOrders,
                                @RequestParam(required = false, name = "sort") String sortBy,
                                @RequestParam(required = false, name = "dir") String dir,
                                Pageable pageable,
                                Model model) {
        var page = customerService.getAllPaged(name, minOrders, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("customers", page.getContent());

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("name", name == null ? "" : name);
        model.addAttribute("minOrders", minOrders == null ? "" : minOrders);

        return "customers/index";
    }

    // -------------------- CREATE: FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    // -------------------- CREATE: ACTION --------------------
    @PostMapping
    public String createCustomer(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "customers/form";
        }

        customerService.create(customer);
        return "redirect:/customers";
    }

    // -------------------- EDIT: FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "customers/form";
    }

    // -------------------- EDIT: ACTION --------------------
    @PostMapping("/{id}")
    public String updateCustomer(
            @PathVariable Long id,
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "customers/form";
        }

        customerService.update(id, customer);
        return "redirect:/customers";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "customers/details";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        try {
            customerService.delete(id);
            return "redirect:/customers";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            var page = customerService.getAllPaged(null, null, "id", "asc", org.springframework.data.domain.PageRequest.of(0, 20));
            model.addAttribute("page", page);
            model.addAttribute("customers", page.getContent());
            model.addAttribute("currentSort", "id");
            model.addAttribute("currentDir", "asc");
            model.addAttribute("name", "");
            model.addAttribute("minOrders", "");
            return "customers/index";
        }
    }
}
