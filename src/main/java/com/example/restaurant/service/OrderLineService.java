package com.example.restaurant.service;

import com.example.restaurant.model.OrderLine;
import com.example.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository repo;

    public OrderLineService(OrderLineRepository repo) {
        this.repo = repo;
    }

    public void addOrderLine(OrderLine orderLine) {
        repo.add(orderLine);
    }

    public void updateOrderLine(OrderLine orderLine) {
        repo.update(orderLine);
    }

    public List<OrderLine> getAllOrderLines() {
        return repo.getAll();
    }

    public OrderLine getOrderLineById(String id) {
        return repo.getById(id);
    }

    public void deleteOrderLine(String id) {
        repo.delete(id);
    }
}
