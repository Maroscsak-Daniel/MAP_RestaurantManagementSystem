package com.example.restaurant.repository;

import com.example.restaurant.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Page<MenuItem> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<MenuItem> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
    Page<MenuItem> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category, Pageable pageable);
    Page<MenuItem> findByPriceBetween(Double min, Double max, Pageable pageable);
}
