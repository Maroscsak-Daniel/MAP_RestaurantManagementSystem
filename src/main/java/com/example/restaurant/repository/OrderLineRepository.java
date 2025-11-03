package com.example.restaurant.repository;

import com.example.restaurant.model.OrderLine;

@org.springframework.stereotype.Repository
public class OrderLineRepository extends Repository<OrderLine> {

    @Override
    protected String getId(OrderLine OL) {
        return OL.getId();
    }

}
