package com.example.restaurant.repository;

import com.example.restaurant.model.OrderLine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderLineRepository {

    private final List<OrderLine> orderLines = new ArrayList<>();

    public void save(OrderLine orderLine) {
        delete(orderLine.getId());
        orderLines.add(orderLine);
    }

    public List<OrderLine> findAll() {
        return new ArrayList<>(orderLines);
    }

    public OrderLine findById(String id) {
        for (OrderLine ol : orderLines) {
            if (ol.getId().equals(id)) {
                return ol;
            }
        }
        return null;
    }

    public void delete(String id) {
        orderLines.removeIf(ol -> ol.getId().equals(id));
    }

    public void clear() {
        orderLines.clear();
    }
}
