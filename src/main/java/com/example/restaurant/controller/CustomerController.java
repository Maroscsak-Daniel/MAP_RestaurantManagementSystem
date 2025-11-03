package com.example.restaurant.controller;

import com.example.restaurant.model.Customer;
import com.example.restaurant.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAll());
        return "customer/index"; // → templates/customer/index.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer("", "", new ArrayList<>()));
        return "customer/form"; // → templates/customer/form.html
    }

    @PostMapping
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.add(customer);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
}
