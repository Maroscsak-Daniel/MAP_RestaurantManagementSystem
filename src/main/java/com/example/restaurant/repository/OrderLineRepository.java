package com.example.restaurant.repository;

import com.example.restaurant.model.OrderLine;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLineRepository extends IRepository<OrderLine>{

    @Override
    protected String getId(OrderLine OL) {
        return OL.getId();
    }

}
