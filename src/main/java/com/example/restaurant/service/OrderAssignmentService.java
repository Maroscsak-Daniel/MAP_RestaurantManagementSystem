package com.example.restaurant.service;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.repository.OrderAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAssignmentService {

    private final OrderAssignmentRepository repo;

    public OrderAssignmentService(OrderAssignmentRepository repo) {
        this.repo = repo;
    }

    public void add(OrderAssignment assignment) {
        repo.add(assignment);
    }

    public void update(OrderAssignment assignment) {
        repo.update(assignment);
    }

    public List<OrderAssignment> getAll() {
        return repo.getAll();
    }

    public OrderAssignment getById(String id) {
        return repo.getById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public List<OrderAssignment> getByOrder(String orderId) {
        return repo.getAll().stream()
                .filter(a -> orderId.equals(a.getOrderId()))
                .collect(Collectors.toList());
    }

    public List<OrderAssignment> getByStaff(String staffId) {
        return repo.getAll().stream()
                .filter(a -> staffId.equals(a.getStaffId()))
                .collect(Collectors.toList());
    }
}
