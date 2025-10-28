package com.example.restaurant.repository;

import com.example.restaurant.model.Chef;
import org.springframework.stereotype.Repository;

@Repository
public class ChefRepository extends IRepository<Chef> {

    @Override
    protected String getId(Chef chef) {
        return chef.getId();
    }
}
