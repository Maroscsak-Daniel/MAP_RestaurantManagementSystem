package com.example.restaurant.repository;

import com.example.restaurant.model.Chef;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChefRepository implements AbstractRepository<Chef> {

    private final List<Chef> chefs = new ArrayList<>();

    @Override
    public Chef save(Chef chef) {
        delete(chef.getId());
        chefs.add(chef);
        return chef;
    }

    @Override
    public List<Chef> findAll() {
        return new ArrayList<>(chefs);
    }

    @Override
    public Chef findById(String id) {
        for (Chef c : chefs) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Chef delete(String id) {
        chefs.removeIf(c -> c.getId().equals(id));
        return findById(id);
    }

    public void clear() {
        chefs.clear();
    }
}
