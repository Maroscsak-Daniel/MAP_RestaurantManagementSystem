package com.example.restaurant.service;

import com.example.restaurant.model.Chef;
import com.example.restaurant.repository.ChefRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    private final ChefRepository chefRepository;

    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    public void addChef(Chef chef) {
        chefRepository.save(chef);
    }

    public List<Chef> getAllChefs() {
        return chefRepository.findAll();
    }

    public Chef getChefById(String id) {
        return chefRepository.findById(id);
    }

    public void deleteChef(String id) {
        chefRepository.delete(id);
    }

    public void clearAll() {
        chefRepository.clear();
    }
}
