package com.example.restaurant.controller;

import com.example.restaurant.model.Customer;
import com.example.restaurant.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList; // Necesara pentru constructorul Customer

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        // Date inițiale pentru testare
        if (customerService.getAll().isEmpty()) {
            customerService.add(new Customer("C001", "Alina Munteanu", new ArrayList<>()));
            customerService.add(new Customer("C002", "Marius Popa", new ArrayList<>()));
        }
    }

    // GET /customers - Afișează lista completă (GET all)
    @GetMapping
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAll());
        // Returnează templates/customer/index.html
        return "customer/index";
    }

    // GET /customers/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Obiect Customer gol. Orders este lăsat ArrayList gol.
        model.addAttribute("customer", new Customer(null, null, new ArrayList<>()));
        // Returnează templates/customer/form.html
        return "customer/form";
    }

    // POST /customers - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createCustomer(@ModelAttribute Customer customer) {
        // Asigurăm că lista de orders nu este null înainte de salvare
        if (customer.getOrders() == null) {
            customer.setOrders(new ArrayList<>());
        }
        customerService.add(customer);
        return "redirect:/customers";
    }

    // POST /customers/{id}/delete - Șterge obiectul
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
}