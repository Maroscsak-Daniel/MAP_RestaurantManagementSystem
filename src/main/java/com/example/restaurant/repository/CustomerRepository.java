package com.example.restaurant.repository;

import com.example.restaurant.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository extends InFileRepository<Customer> {

    public CustomerRepository() {
        super("customers.json", Customer.class);
    }
}
