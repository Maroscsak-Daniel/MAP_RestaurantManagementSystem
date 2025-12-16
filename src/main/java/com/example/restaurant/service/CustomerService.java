package com.example.restaurant.service;

import com.example.restaurant.model.Customer;
import com.example.restaurant.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Customer> getAllPaged(String name, Integer minOrders, String sortBy, String dir, Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if ((name != null && !name.isEmpty()) && (minOrders != null)) {
            return repo.findByNameAndMinOrders(name, minOrders, p);
        } else if (name != null && !name.isEmpty()) {
            return repo.findByNameContainingIgnoreCase(name, p);
        } else if (minOrders != null) {
            return repo.findByMinOrders(minOrders, p);
        } else {
            return repo.findAll(p);
        }
    }
}
