package com.example.restaurant.repository;

import com.example.restaurant.model.OrderAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class OrderAssignmentRepository extends InFileRepository<OrderAssignment> {

    public OrderAssignmentRepository() {
        super("orderAssignments.json", OrderAssignment.class);
    }
}
