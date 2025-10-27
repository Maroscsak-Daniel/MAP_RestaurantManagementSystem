package com.example.restaurant.service;

import com.example.restaurant.model.Table;
import com.example.restaurant.repository.TableRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final TableRepo repo;

    public TableService(TableRepo repo) {
        this.repo = repo;
    }

    public void add(Table table) {
        repo.save(table);
    }

    public List<Table> getAll() {
        return repo.findAll();
    }

    public Table getById(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public void clear() {
        repo.clear();
    }

    // Convenience helpers
    public List<Table> getFreeTables() {
        return repo.findAll().stream()
                .filter(t -> "Free".equalsIgnoreCase(t.getOccupiedStatus()))
                .collect(Collectors.toList());
    }

    public void setStatus(String id, String status) {
        Table t = repo.findById(id);
        if (t != null) {
            t.setOccupiedStatus(status);
            repo.save(t);
        }
    }
}

