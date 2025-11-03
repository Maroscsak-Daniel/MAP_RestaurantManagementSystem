package com.example.restaurant.repository;

import com.example.restaurant.model.OrderAssignment;

@org.springframework.stereotype.Repository
public class OrderAssignmentRepo extends Repository<OrderAssignment> {

    @Override
    protected String getId(OrderAssignment entity) {
        return entity.getId();
    }

}
