package com.example.restaurant.service;

import com.example.restaurant.model.Chef;
import com.example.restaurant.repository.ChefRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    private final ChefRepository repo;

    public ChefService(ChefRepository repo) {
        this.repo = repo;
    }

    public void addChef(Chef chef) {
        repo.add(chef);
    }

    public void updateChef(Chef chef) {
        repo.update(chef);
    }

    public List<Chef> getAllChefs() {
        return repo.getAll();
    }

    public Chef getChefById(String id) {
        return repo.getById(id);
    }

    public void deleteChef(String id) {
        repo.delete(id);
    }
}
