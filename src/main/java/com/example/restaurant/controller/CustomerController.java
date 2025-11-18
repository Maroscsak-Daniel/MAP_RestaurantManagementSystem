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

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAll());
        return "customer/index";
    }


    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getCustomerDetails(@PathVariable String id, Model model) {
        Customer customer = customerService.getById(id);
        if (customer == null) {
            return "redirect:/customers";
        }

        // ca să nu fie niciodată null
        if (customer.getOrderIds() == null) {
            customer.setOrderIds(new ArrayList<>());
        }

        model.addAttribute("customer", customer);
        return "customer/details";
    }


    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Customer customer = new Customer();
        customer.setOrderIds(new ArrayList<>()); // important să nu fie null

        model.addAttribute("customer", customer);
        return "customer/form";
    }


    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createCustomer(@ModelAttribute Customer customer) {

        if (customer.getOrderIds() == null) {
            customer.setOrderIds(new ArrayList<>());
        }

        customerService.add(customer);
        return "redirect:/customers";
    }


    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        Customer customer = customerService.getById(id);
        if (customer == null) {
            return "redirect:/customers";
        }

        if (customer.getOrderIds() == null) {
            customer.setOrderIds(new ArrayList<>());
        }

        model.addAttribute("customer", customer);
        return "customer/form";
    }


    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable String id,
                                 @ModelAttribute Customer customer) {

        customer.setId(id);

        if (customer.getOrderIds() == null) {
            customer.setOrderIds(new ArrayList<>());
        }

        customerService.update(customer);
        return "redirect:/customers";
    }


    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
}
