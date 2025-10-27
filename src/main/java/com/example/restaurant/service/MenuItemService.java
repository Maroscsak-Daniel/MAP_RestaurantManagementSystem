package com.example.restaurant.service;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void addMenuItem(MenuItem item) {
        menuItemRepository.save(item);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(String id) {
        return menuItemRepository.findById(id);
    }

    public void deleteMenuItem(String id) {
        menuItemRepository.delete(id);
    }

    public void clearAll() {
        menuItemRepository.clear();
    }
}
