package com.example.restaurant.repository;

import com.example.restaurant.model.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuItemRepository {

    private final List<MenuItem> menuItems = new ArrayList<>();

    public void save(MenuItem item) {
        // if exists, replace it
        delete(item.getId());
        menuItems.add(item);
    }

    public List<MenuItem> findAll() {
        return new ArrayList<>(menuItems);
    }

    public MenuItem findById(String id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void delete(String id) {
        menuItems.removeIf(item -> item.getId().equals(id));
    }

    public void clear() {
        menuItems.clear();
    }
}
