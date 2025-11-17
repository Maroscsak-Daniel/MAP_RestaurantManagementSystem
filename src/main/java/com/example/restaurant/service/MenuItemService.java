package com.example.restaurant.service;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository repo;

    public MenuItemService(MenuItemRepository repo) {
        this.repo = repo;
    }

    public void addMenuItem(MenuItem item) {
        repo.add(item);
    }

    public void updateMenuItem(MenuItem item) {
        repo.update(item);
    }

    public List<MenuItem> getAllMenuItems() {
        return repo.getAll();
    }

    public MenuItem getMenuItemById(String id) {
        return repo.getById(id);
    }

    public void deleteMenuItem(String id) {
        repo.delete(id);
    }
}
