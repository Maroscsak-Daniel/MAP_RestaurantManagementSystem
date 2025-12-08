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

    public List<Chef> getAll() {
        return repo.findAll();
    }

    public Chef getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chef not found: " + id));
    }

    public Chef create(Chef c) {
        return repo.save(c);
    }

    public Chef update(Long id, Chef data) {
        Chef c = getById(id);
        c.setName(data.getName());
        c.setRank(data.getRank());
        return repo.save(c);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
