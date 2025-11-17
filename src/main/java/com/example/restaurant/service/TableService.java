package com.example.restaurant.service;

import com.example.restaurant.model.Table;
import com.example.restaurant.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final TableRepository repo;

    public TableService(TableRepository repo) {
        this.repo = repo;
    }

    public void add(Table table) {
        repo.add(table);
    }

    public void update(Table table) {
        repo.update(table);
    }

    public List<Table> getAll() {
        return repo.getAll();
    }

    public Table getById(String id) {
        return repo.getById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    // Convenience helpers
    public List<Table> getFreeTables() {
        return repo.getAll().stream()
                .filter(t -> "free".equalsIgnoreCase(t.getOccupiedStatus()))
                .collect(Collectors.toList());
    }

    public void setStatus(String id, String status) {
        Table t = repo.getById(id);
        if (t != null) {
            t.setOccupiedStatus(status);
            repo.update(t);
        }
    }
}
