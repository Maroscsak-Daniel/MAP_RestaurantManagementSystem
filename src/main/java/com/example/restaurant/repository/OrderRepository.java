package com.example.restaurant.repository;

import com.example.restaurant.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends InFileRepository<Order> {

    public OrderRepository() {
        super("orders.json", Order.class);
    }
}
