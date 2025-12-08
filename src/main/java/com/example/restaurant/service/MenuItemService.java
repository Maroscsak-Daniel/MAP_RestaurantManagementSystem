package com.example.restaurant.service;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final OrderLineRepository orderLineRepository;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           OrderLineRepository orderLineRepository) {
        this.menuItemRepository = menuItemRepository;
        this.orderLineRepository = orderLineRepository;
    }

    public List<MenuItem> getAll() {
        return menuItemRepository.findAll();
    }

    public MenuItem getById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found: " + id));
    }

    public MenuItem create(MenuItem item) {
        return menuItemRepository.save(item);
    }

    public MenuItem update(Long id, MenuItem data) {
        MenuItem existing = getById(id);

        existing.setName(data.getName());
        existing.setDescription(data.getDescription());
        existing.setPrice(data.getPrice());
        existing.setCategory(data.getCategory());
        existing.setAllergens(data.getAllergens());

        return menuItemRepository.save(existing);
    }

    public void delete(Long id) {

        long linkedLines = orderLineRepository.countByMenuItem_Id(id);
        if (linkedLines > 0) {
            throw new IllegalStateException(
                    "Cannot delete menu item — it is used in " + linkedLines + " order lines."
            );
        }

        menuItemRepository.deleteById(id);
    }
}

