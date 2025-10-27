package com.example.restaurant.repository;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderAssignment;

import java.util.ArrayList;
import java.util.List;

public class OrderAssignmentRepo implements AbstractRepository<OrderAssignment> {
    private List<OrderAssignment> orderAssignmentRepo = new ArrayList<>();

    @Override
    public OrderAssignment save(OrderAssignment order) {
        delete(order.getId());
        orderAssignmentRepo.add(order);
        return order;
    }

    @Override
    public List<OrderAssignment> findAll() {
        return new ArrayList<>(orderAssignmentRepo);
    }

    @Override
    public OrderAssignment findById(String id) {
        for (OrderAssignment o : orderAssignmentRepo) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public OrderAssignment delete(String id) {
        orderAssignmentRepo.removeIf(c -> c.getId().equals(id));
        return findById(id);
    }


    public void clear() {
        orderAssignmentRepo.clear();
    }
}
