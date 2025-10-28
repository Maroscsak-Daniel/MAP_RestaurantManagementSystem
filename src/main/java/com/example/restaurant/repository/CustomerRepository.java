package com.example.restaurant.repository;

import com.example.restaurant.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository extends IRepository<Customer> {

    @Override
    protected String getId(Customer customer) {
        return customer.getId();
    }

}
