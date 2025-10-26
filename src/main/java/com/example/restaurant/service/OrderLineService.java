package com.example.restaurant.service;

import com.example.restaurant.model.OrderLine;
import com.example.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLineRepository.save(orderLine);
    }

    public List<OrderLine> getAllOrderLines() {
        return orderLineRepository.findAll();
    }

    public OrderLine getOrderLineById(String id) {
        return orderLineRepository.findById(id);
    }

    public void deleteOrderLine(String id) {
        orderLineRepository.delete(id);
    }

    public void clearAll() {
        orderLineRepository.clear();
    }
}
