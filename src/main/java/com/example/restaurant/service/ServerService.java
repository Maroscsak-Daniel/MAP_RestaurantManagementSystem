package com.example.restaurant.service;

import com.example.restaurant.model.Server;
import com.example.restaurant.repository.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository repo;

    public ServerService(ServerRepository repo) {
        this.repo = repo;
    }

    public List<Server> getAll() {
        return repo.findAll();
    }

    public Server getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Server not found: " + id));
    }

    public Server create(Server server) {
        return repo.save(server);
    }

    public Server update(Long id, Server data) {
        Server s = getById(id);
        s.setName(data.getName());
        s.setShift(data.getShift());
        s.setExperienceYears(data.getExperienceYears());
        return repo.save(s);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
