package com.example.restaurant.repository;

import com.example.restaurant.model.Chef;

@org.springframework.stereotype.Repository
public class ChefRepository extends Repository<Chef> {

    @Override
    protected String getId(Chef chef) {
        return chef.getId();
    }
}
