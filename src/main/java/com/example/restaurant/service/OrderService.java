package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public void add(Order order) {
        repo.save(order);
    }

    public List<Order> getAll() {
        return repo.findAll();
    }

    public Order getById(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public void clear() {
        repo.clear();
    }

    // Convenience filters
    public List<Order> getByCustomer(String customerId) {
        return repo.findAll().stream()
                .filter(o -> customerId.equals(o.getCustomerId()))
                .collect(Collectors.toList());
    }

    public List<Order> getByTable(String tableId) {
        return repo.findAll().stream()
                .filter(o -> tableId.equals(o.getTableId()))
                .collect(Collectors.toList());
    }

    public void setStatus(String orderId, String status) {
        Order o = repo.findById(orderId);
        if (o != null) {
            o.setStatus(status);
            repo.save(o);
        }
    }
}
