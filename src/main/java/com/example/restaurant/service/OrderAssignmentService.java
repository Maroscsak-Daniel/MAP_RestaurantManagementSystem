package com.example.restaurant.service;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.repository.OrderAssignmentRepository;
import com.example.restaurant.model.Order;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAssignmentService {
    private final OrderAssignmentRepository repo;
    private final OrderRepository orderRepository;

    public OrderAssignmentService(
            OrderAssignmentRepository repo,
            OrderRepository orderRepository
    ) {
        this.repo = repo;
        this.orderRepository = orderRepository;
    }

    public List<OrderAssignment> getAll() {
        return repo.findAll();
    }

    public OrderAssignment getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + id));
    }

    public OrderAssignment create(OrderAssignment a) {
        return repo.save(a);
    }

    public void update(Long id, OrderAssignment data) {

        OrderAssignment existing = getById(id);

        // Validate order exists
        Long newOrderId = data.getOrder().getId();
        if (!orderRepository.existsById(newOrderId)) {
            throw new IllegalArgumentException("Order ID " + newOrderId + " does not exist.");
        }

        // Do the update
        existing.setOrder(orderRepository.findById(newOrderId).orElseThrow());
        existing.setStaffId(data.getStaffId());

        repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
