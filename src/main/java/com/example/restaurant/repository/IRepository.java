package com.example.restaurant.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class IRepository<T> implements AbstractRepository <T> {

    protected final List<T> items = new ArrayList<>();

    protected abstract String getId(T item);

    @Override
    public T save(T t) {
        // if an object with the same ID exists, replace it
        String id = getId(t);
        Optional<T> existing = items.stream()
                .filter(x -> getId(x).equals(id))
                .findFirst();
        existing.ifPresent(items::remove);
        items.add(t);
        return t;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(items);
    }

    @Override
    public T findById(String id) {
        return items.stream()
                .filter(x -> getId(x).equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public T delete(String id) {
        T found = findById(id);
        if (found != null) {
            items.remove(found);
        }
        return found;
    }

    public void clear() {
        items.clear();
    }
}
