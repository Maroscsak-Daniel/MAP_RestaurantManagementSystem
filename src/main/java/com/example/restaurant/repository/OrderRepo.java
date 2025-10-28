package com.example.restaurant.repository;

import com.example.restaurant.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepo extends IRepository<Order>{

    @Override
    protected String getId(Order order) {
        return order.getId();
    }

}
