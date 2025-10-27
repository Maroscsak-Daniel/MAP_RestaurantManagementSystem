package com.example.restaurant.repository;

import com.example.restaurant.model.MenuItem;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuItemRepository implements AbstractRepository<MenuItem> {

    private final List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public MenuItem save(MenuItem item) {
        // if exists, replace it
        delete(item.getId());
        menuItems.add(item);
        return item;
    }

    @Override
    public List<MenuItem> findAll() {
        return new ArrayList<>(menuItems);
    }

    @Override
    public MenuItem findById(String id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public MenuItem delete(String id) {
        menuItems.removeIf(item -> item.getId().equals(id));
        return findById(id);
    }

    public void clear() {
        menuItems.clear();
    }
}
