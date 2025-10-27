package com.example.restaurant.repository;

import com.example.restaurant.model.Server;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServerRepository implements AbstractRepository<Server> {

    private final List<Server> servers = new ArrayList<>();

    @Override
    public Server save(Server server) {
        delete(server.getId());
        servers.add(server);
        return server;
    }

    @Override
    public List<Server> findAll() {
        return new ArrayList<>(servers);
    }

    @Override
    public Server findById(String id) {
        for (Server s : servers) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Server delete(String id) {
        servers.removeIf(s -> s.getId().equals(id));
        return  findById(id);
    }

    public void clear() {
        servers.clear();
    }
}
