package com.example.restaurant.repository;

import com.example.restaurant.model.Chef;
import org.springframework.stereotype.Repository;

@Repository
public class ChefRepository extends InFileRepository<Chef> {

    public ChefRepository() {
        super("chefs.json", Chef.class);
    }
}
