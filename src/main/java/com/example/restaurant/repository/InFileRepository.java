package com.example.restaurant.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.*;

public abstract class InFileRepository<T> {

    private final Path filePath;
    private final Class<T> type;
    private final ObjectMapper mapper = new ObjectMapper();
    private List<T> data = new ArrayList<>();

    public InFileRepository(String fileName, Class<T> type) {
        this.filePath = Paths.get("src/main/resources/data/" + fileName);
        this.type = type;
        load();
    }

    private void load() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                save();
                return;
            }

            byte[] bytes = Files.readAllBytes(filePath);
            if (bytes.length == 0) {
                data = new ArrayList<>();
                return;
            }

            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, type);

            data = mapper.readValue(bytes, listType);

        } catch (Exception e) {
            throw new RuntimeException("Could not load: " + filePath, e);
        }
    }

    private void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException("Could not save: " + filePath, e);
        }
    }

    private String getId(T entity) {
        try {
            Method m = entity.getClass().getMethod("getId");
            return (String) m.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Entity has no getId() method", e);
        }
    }

    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    public T getById(String id) {
        return data.stream()
                .filter(x -> getId(x).equals(id))
                .findFirst()
                .orElse(null);
    }

    public void add(T entity) {
        String id = getId(entity);

        // auto-generate ID if missing
        if (id == null || id.isBlank()) {
            try {
                Method setter = entity.getClass().getMethod("setId", String.class);
                id = UUID.randomUUID().toString();
                setter.invoke(entity, id);
            } catch (Exception e) {
                throw new RuntimeException("Failed to auto-assign ID", e);
            }
        }

        // ensure uniqueness
        if (getById(id) != null) {
            throw new IllegalArgumentException("ID already exists: " + id);
        }

        data.add(entity);
        save();
    }

    public void update(T entity) {
        String id = getId(entity);

        data.removeIf(x -> getId(x).equals(id));
        data.add(entity);

        save();
    }

    public void delete(String id) {
        data.removeIf(x -> getId(x).equals(id));
        save();
    }
}
