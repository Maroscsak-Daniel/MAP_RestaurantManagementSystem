package com.example.restaurant.repository;

import com.example.restaurant.model.MenuItem;

@org.springframework.stereotype.Repository
public class MenuItemRepository extends Repository<MenuItem> {

    @Override
    protected String getId(MenuItem item) {
        return item.getId();
    }

}
