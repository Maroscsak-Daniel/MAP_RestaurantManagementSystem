package com.example.restaurant.service;

import com.example.restaurant.model.Chef;
import com.example.restaurant.repository.ChefRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Paged & filtered
    public Page<Chef> getAllPaged(String name, String rank, String sortBy, String dir, Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if ((name != null && !name.isEmpty()) && (rank != null && !rank.isEmpty())) {
            return repo.findByNameContainingIgnoreCaseAndRankContainingIgnoreCase(name, rank, p);
        } else if (name != null && !name.isEmpty()) {
            return repo.findByNameContainingIgnoreCase(name, p);
        } else if (rank != null && !rank.isEmpty()) {
            return repo.findByRankContainingIgnoreCase(rank, p);
        } else {
            return repo.findAll(p);
        }
    }
}
