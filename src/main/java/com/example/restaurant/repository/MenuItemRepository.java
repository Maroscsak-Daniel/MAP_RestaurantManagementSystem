package com.example.restaurant.repository;

import com.example.restaurant.model.MenuItem;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public class MenuItemRepository extends IRepository<MenuItem> {

    @Override
    protected String getId(MenuItem item) {
        return item.getId();
    }

}
