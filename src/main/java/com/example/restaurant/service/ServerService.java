package com.example.restaurant.service;

import com.example.restaurant.model.Server;
import com.example.restaurant.repository.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public void addServer(Server server) {
        serverRepository.save(server);
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Server getServerById(String id) {
        return serverRepository.findById(id);
    }

    public void deleteServer(String id) {
        serverRepository.delete(id);
    }

    public void clearAll() {
        serverRepository.clear();
    }
}
