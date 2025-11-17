package com.example.restaurant.repository;

import com.example.restaurant.model.OrderLine;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLineRepository extends InFileRepository<OrderLine> {

    public OrderLineRepository() {
        super("orderLines.json", OrderLine.class);
    }
}
