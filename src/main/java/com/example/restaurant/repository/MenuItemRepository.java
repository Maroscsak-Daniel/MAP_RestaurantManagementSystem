package com.example.restaurant.repository;

import com.example.restaurant.model.MenuItem;
import org.springframework.stereotype.Repository;

@Repository
public class MenuItemRepository extends InFileRepository<MenuItem> {

    public MenuItemRepository() {
        super("menuItems.json", MenuItem.class);
    }
}
