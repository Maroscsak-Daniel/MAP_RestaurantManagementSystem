package com.example.restaurant.repository;

import com.example.restaurant.model.Server;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServerRepository {

    private final List<Server> servers = new ArrayList<>();

    public void save(Server server) {
        delete(server.getId());
        servers.add(server);
    }

    public List<Server> findAll() {
        return new ArrayList<>(servers);
    }

    public Server findById(String id) {
        for (Server s : servers) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void delete(String id) {
        servers.removeIf(s -> s.getId().equals(id));
    }

    public void clear() {
        servers.clear();
    }
}
