package com.example.restaurant.controller;

import com.example.restaurant.model.Customer;
import com.example.restaurant.service.CustomerService;
import jakarta.validation.Valid;
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
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAll());
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
            model.addAttribute("customers", customerService.getAll());
            return "customers/index";
        }
    }
}
