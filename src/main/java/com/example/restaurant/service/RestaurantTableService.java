package com.example.restaurant.service;

import com.example.restaurant.model.RestaurantTable;
import com.example.restaurant.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository repo;

    public RestaurantTableService(RestaurantTableRepository repo) {
        this.repo = repo;
    }

    public List<RestaurantTable> getAll() {
        return repo.findAll();
    }

    public RestaurantTable getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Table not found: " + id));
    }

    public RestaurantTable create(RestaurantTable table) {
        return repo.save(table);
    }

    public RestaurantTable update(Long id, RestaurantTable data) {
        RestaurantTable existing = getById(id);
        existing.setNumber(data.getNumber());
        existing.setOccupiedStatus(data.getOccupiedStatus());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<RestaurantTable> getFreeTables() {
        return repo.findAll().stream()
                .filter(t -> "free".equalsIgnoreCase(t.getOccupiedStatus()))
                .toList();
    }

    public void setStatus(Long id, String status) {
        RestaurantTable t = getById(id);
        t.setOccupiedStatus(status);
        repo.save(t);
    }
}
