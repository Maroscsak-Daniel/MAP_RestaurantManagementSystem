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

    public void addServer(Server server) {
        repo.add(server);
    }

    public void updateServer(Server server) {
        repo.update(server);
    }

    public List<Server> getAllServers() {
        return repo.getAll();
    }

    public Server getServerById(String id) {
        return repo.getById(id);
    }

    public void deleteServer(String id) {
        repo.delete(id);
    }
}
