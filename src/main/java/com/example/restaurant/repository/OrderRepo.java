package com.example.restaurant.repository;

import com.example.restaurant.model.Bill;
import com.example.restaurant.model.Customer;
import com.example.restaurant.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepo implements AbstractRepository <Order>{
    private List<Order> orderRepo = new ArrayList<Order>();

    @Override
    public Order save(Order order) {
        delete(order.getId());
        orderRepo.add(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orderRepo);
    }

    @Override
    public Order findById(String id) {
        for (Order o : orderRepo) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public Order delete(String id) {
        orderRepo.removeIf(c -> c.getId().equals(id));
        return findById(id);
    }


    public void clear() {
        orderRepo.clear();
    }

}
