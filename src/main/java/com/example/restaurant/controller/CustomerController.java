package com.example.restaurant.controller;

import com.example.restaurant.model.Customer;
import com.example.restaurant.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Customer> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Customer byId(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Customer c) {
        service.add(c);
        return "Customer added.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Customer deleted.";
    }

    @DeleteMapping("/clear")
    public String clear() {
        service.clear();
        return "All customers cleared.";
    }
}