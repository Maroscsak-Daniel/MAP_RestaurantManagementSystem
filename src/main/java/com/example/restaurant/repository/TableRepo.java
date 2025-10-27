package com.example.restaurant.repository;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableRepo implements AbstractRepository<Table> {
    private List<Table> tableRepo = new ArrayList<>();

    @Override
    public Table save(Table table) {
        delete(table.getId());
        tableRepo.add(table);
        return table;
    }

    @Override
    public List<Table> findAll() {
        return new ArrayList<>(tableRepo);
    }

    @Override
    public Table findById(String id) {
        for (Table t : tableRepo) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public Table delete(String id) {
        tableRepo.removeIf(c -> c.getId().equals(id));
        return findById(id);
    }


    public void clear() {
        tableRepo.clear();
    }
}
