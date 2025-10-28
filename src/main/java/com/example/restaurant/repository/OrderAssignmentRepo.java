package com.example.restaurant.repository;

import com.example.restaurant.model.OrderAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class OrderAssignmentRepo extends IRepository<OrderAssignment> {

    @Override
    protected String getId(OrderAssignment entity) {
        return entity.getId();
    }

}
