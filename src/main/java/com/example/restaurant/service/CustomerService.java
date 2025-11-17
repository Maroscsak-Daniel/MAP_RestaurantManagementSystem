package com.example.restaurant.service;

import com.example.restaurant.model.Customer;
import com.example.restaurant.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public void add(Customer customer) {
        repo.add(customer);   // JSON ADD
    }

    public void update(Customer customer) {
        repo.update(customer); // JSON UPDATE (if editing customers)
    }

    public List<Customer> getAll() {
        return repo.getAll();  // JSON GET ALL
    }

    public Customer getById(String id) {
        return repo.getById(id);  // JSON GET BY ID
    }

    public void delete(String id) {
        repo.delete(id);      // JSON DELETE
    }
}
