package com.example.restaurant.repository;

import com.example.restaurant.model.Server;
import org.springframework.stereotype.Repository;

@Repository
public class ServerRepository extends InFileRepository<Server> {

    public ServerRepository() {
        super("servers.json", Server.class);
    }
}
