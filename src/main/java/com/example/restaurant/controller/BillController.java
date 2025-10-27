package com.example.restaurant.controller;

import com.example.restaurant.model.Bill;
import com.example.restaurant.service.BillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillService service;

    public BillController(BillService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Bill> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Bill byId(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/by-order/{orderId}")
    public List<Bill> byOrder(@PathVariable String orderId) {
        return service.getByOrderId(orderId);
    }

    @PostMapping("/add")
    public String add(@RequestBody Bill b) {
        service.add(b);
        return "Bill added.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Bill deleted.";
    }

    @DeleteMapping("/clear")
    public String clear() {
        service.clear();
        return "All bills cleared.";}
}