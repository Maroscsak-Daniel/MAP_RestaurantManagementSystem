package com.example.restaurant.repository;

import java.util.List;

public interface AbstractRepository<T> {
    T save(T t);
    List<T> findAll();
    T findById(String id);
    T delete(String id);
}