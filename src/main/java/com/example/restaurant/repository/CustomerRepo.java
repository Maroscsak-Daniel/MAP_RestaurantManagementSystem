package com.example.restaurant.repository;

import com.example.restaurant.model.Customer;
import com.example.restaurant.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerRepo implements AbstractRepository<Customer> {

    private List<Customer> customerRepo =  new ArrayList<>();

    @Override
    public Customer save(Customer customer) {
        delete(customer.getId());
        customerRepo.add(customer);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customerRepo);
    }

    @Override
    public Customer findById(String id) {
        for (Customer c : customerRepo) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Customer delete(String id) {
        customerRepo.removeIf(c -> c.getId().equals(id));
        return findById(id);
    }


    public void clear() {
        customerRepo.clear();
    }


}
