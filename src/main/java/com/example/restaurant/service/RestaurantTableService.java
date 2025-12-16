package com.example.restaurant.service;

import com.example.restaurant.model.RestaurantTable;
import com.example.restaurant.repository.RestaurantTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<RestaurantTable> getAllPaged(Integer number, String status, String sortBy, String dir, Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (number != null && status != null && !status.isEmpty()) {
            return repo.findByNumberAndOccupiedStatusContainingIgnoreCase(number, status, p);
        } else if (number != null) {
            return repo.findByNumber(number, p);
        } else if (status != null && !status.isEmpty()) {
            return repo.findByOccupiedStatusContainingIgnoreCase(status, p);
        } else {
            return repo.findAll(p);
        }
    }
}
