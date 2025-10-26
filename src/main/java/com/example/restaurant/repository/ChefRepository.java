package com.example.restaurant.repository;

import com.example.restaurant.model.Chef;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChefRepository {

    private final List<Chef> chefs = new ArrayList<>();

    public void save(Chef chef) {
        delete(chef.getId());
        chefs.add(chef);
    }

    public List<Chef> findAll() {
        return new ArrayList<>(chefs);
    }

    public Chef findById(String id) {
        for (Chef c : chefs) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void delete(String id) {
        chefs.removeIf(c -> c.getId().equals(id));
    }

    public void clear() {
        chefs.clear();
    }
}
