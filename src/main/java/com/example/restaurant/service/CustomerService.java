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
        repo.save(customer);
    }

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getById(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public void clear() {
        repo.clear();
    }
}
