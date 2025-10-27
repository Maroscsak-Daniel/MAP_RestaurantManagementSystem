package com.example.restaurant.service;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.repository.OrderAssignmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAssignmentService {

    private final OrderAssignmentRepo repo;

    public OrderAssignmentService(OrderAssignmentRepo repo) {
        this.repo = repo;
    }

    public void add(OrderAssignment assignment) {
        repo.save(assignment);
    }

    public List<OrderAssignment> getAll() {
        return repo.findAll();
    }

    public OrderAssignment getById(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public void clear() {
        repo.clear();
    }

    public List<OrderAssignment> getByOrder(String orderId) {
        return repo.findAll().stream()
                .filter(a -> orderId.equals(a.getOrderId()))
                .collect(Collectors.toList());
    }

    public List<OrderAssignment> getByStaff(String staffId) {
        return repo.findAll().stream()
                .filter(a -> staffId.equals(a.getStaffId()))
                .collect(Collectors.toList());
    }
}
