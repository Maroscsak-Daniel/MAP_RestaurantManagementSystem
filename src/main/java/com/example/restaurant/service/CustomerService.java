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

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    public Customer create(Customer customer) {
        return repo.save(customer);
    }

    public Customer update(Long id, Customer data) {
        Customer existing = getById(id);
        existing.setName(data.getName());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Customer c = getById(id);

        if (!c.getOrders().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with existing orders.");
        }

        repo.delete(c);
    }
}
