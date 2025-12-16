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

    public org.springframework.data.domain.Page<MenuItem> getAllPaged(String name, String category, Double minPrice, Double maxPrice, String sortBy, String dir, org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        org.springframework.data.domain.Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if ((name != null && !name.isEmpty()) && (category != null && !category.isEmpty())) {
            return menuItemRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category, p);
        } else if (name != null && !name.isEmpty()) {
            return menuItemRepository.findByNameContainingIgnoreCase(name, p);
        } else if (category != null && !category.isEmpty()) {
            return menuItemRepository.findByCategoryContainingIgnoreCase(category, p);
        } else if (minPrice != null && maxPrice != null) {
            return menuItemRepository.findByPriceBetween(minPrice, maxPrice, p);
        } else {
            return menuItemRepository.findAll(p);
        }
    }
}
