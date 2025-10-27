package com.example.restaurant.controller;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.service.OrderAssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class OrderAssignmentController {

    private final OrderAssignmentService service;

    public OrderAssignmentController(OrderAssignmentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<OrderAssignment> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public OrderAssignment byId(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/by-order/{orderId}")
    public List<OrderAssignment> byOrder(@PathVariable String orderId) {
        return service.getByOrder(orderId);
    }

    @GetMapping("/by-staff/{staffId}")
    public List<OrderAssignment> byStaff(@PathVariable String staffId) {
        return service.getByStaff(staffId);
    }

    @PostMapping("/add")
    public String add(@RequestBody OrderAssignment a) {
        service.add(a);
        return "OrderAssignment added.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "OrderAssignment deleted.";
    }

    @DeleteMapping("/clear")
    public String clear() {
        service.clear();
        return "All assignments cleared.";
    }
}
