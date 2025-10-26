package com.example.restaurant.controller;

import com.example.restaurant.model.OrderLine;
import com.example.restaurant.service.OrderLineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderlines")
public class OrderLineController {

    private final OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GetMapping("/all")
    public List<OrderLine> getAll() {
        return orderLineService.getAllOrderLines();
    }

    @GetMapping("/{id}")
    public OrderLine getById(@PathVariable String id) {
        return orderLineService.getOrderLineById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody OrderLine orderLine) {
        orderLineService.addOrderLine(orderLine);
        return "OrderLine added successfully!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        orderLineService.deleteOrderLine(id);
        return "OrderLine deleted successfully!";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        orderLineService.clearAll();
        return "All order lines cleared.";
    }
}
