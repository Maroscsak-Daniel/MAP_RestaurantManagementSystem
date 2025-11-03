package com.example.restaurant.repository;

import com.example.restaurant.model.Order;

@org.springframework.stereotype.Repository
public class OrderRepository extends Repository<Order> {

    @Override
    protected String getId(Order order) {
        return order.getId();
    }

}
