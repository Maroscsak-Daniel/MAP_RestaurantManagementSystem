package com.example.restaurant.repository;

import com.example.restaurant.model.OrderLine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderLineRepository implements AbstractRepository<OrderLine>{

    private final List<OrderLine> orderLines = new ArrayList<>();

    @Override
    public OrderLine save(OrderLine orderLine) {
        delete(orderLine.getId());
        orderLines.add(orderLine);
        return orderLine;
    }

    @Override
    public List<OrderLine> findAll() {
        return new ArrayList<>(orderLines);
    }

    @Override
    public OrderLine findById(String id) {
        for (OrderLine ol : orderLines) {
            if (ol.getId().equals(id)) {
                return ol;
            }
        }
        return null;
    }

    @Override
    public OrderLine delete(String id) {
        orderLines.removeIf(ol -> ol.getId().equals(id));
        return  findById(id);
    }

    public void clear() {
        orderLines.clear();
    }
}
