package com.example.restaurant.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseCrudService<T, ID> {

    protected final JpaRepository<T, ID> repo;

    protected BaseCrudService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    public List<T> getAll() {
        return repo.findAll();
    }

    public T getById(ID id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found: " + id));
    }

    public T save(T entity) {
        return repo.save(entity);
    }

    public void delete(ID id) {
        repo.deleteById(id);
    }
}
