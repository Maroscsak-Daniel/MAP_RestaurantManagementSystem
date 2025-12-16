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
        // Business validation: number must be positive
        if (table.getNumber() == null || table.getNumber() < 1) {
            throw new IllegalArgumentException("Table number must be >= 1");
        }
        // Unique number validation
        if (repo.existsByNumber(table.getNumber())) {
            throw new IllegalArgumentException("Table number already exists: " + table.getNumber());
        }
        return repo.save(table);
    }

    public RestaurantTable update(Long id, RestaurantTable data) {
        RestaurantTable existing = getById(id);
        // Only allow editing occupiedStatus on update (do not change number)
        String newStatus = data.getOccupiedStatus();
        // If trying to set table to "free", ensure all associated orders are COMPLETED or CANCELLED
        if ("free".equalsIgnoreCase(newStatus)) {
            if (existing.getOrders() != null && !existing.getOrders().isEmpty()) {
                boolean allFinished = existing.getOrders().stream()
                        .allMatch(o -> o.getStatus() != null && (o.getStatus().name().equals("COMPLETED") || o.getStatus().name().equals("CANCELLED")));
                if (!allFinished) {
                    throw new IllegalStateException("Cannot set table to free while it has active orders.");
                }
            }
        }
        existing.setOccupiedStatus(newStatus);
        return repo.save(existing);
    }

    public void delete(Long id) {
        RestaurantTable t = getById(id);
        // Allow delete only if there are no orders OR all orders are COMPLETED or CANCELLED
        if (t.getOrders() != null && !t.getOrders().isEmpty()) {
            boolean allFinished = t.getOrders().stream()
                    .allMatch(o -> o.getStatus() != null && (o.getStatus().name().equals("COMPLETED") || o.getStatus().name().equals("CANCELLED")));
            if (!allFinished) {
                throw new IllegalStateException("Cannot delete table while it has active orders.");
            }
        }
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
