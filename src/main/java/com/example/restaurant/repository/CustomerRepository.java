package com.example.restaurant.repository;

import com.example.restaurant.model.Customer;

@org.springframework.stereotype.Repository
public class CustomerRepository extends Repository<Customer> {

    @Override
    protected String getId(Customer customer) {
        return customer.getId();
    }

}
