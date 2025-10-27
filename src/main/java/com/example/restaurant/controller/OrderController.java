package com.example.restaurant.controller;

import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Order> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Order byId(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/by-customer/{customerId}")
    public List<Order> byCustomer(@PathVariable String customerId) {
        return service.getByCustomer(customerId);
    }

    @GetMapping("/by-table/{tableId}")
    public List<Order> byTable(@PathVariable String tableId) {
        return service.getByTable(tableId);
    }

    @PostMapping("/add")
    public String add(@RequestBody Order o) {
        service.add(o);
        return "Order added.";
    }

    @PatchMapping("/{id}/status/{status}")
    public String setStatus(@PathVariable String id, @PathVariable String status) {
        service.setStatus(id, status);
        return "Order status updated.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Order deleted.";
    }

    @DeleteMapping("/clear")
    public String clear() {
        service.clear();
        return "All orders cleared.";
    }
}
