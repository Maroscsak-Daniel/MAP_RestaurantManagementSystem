package com.example.restaurant.repository;

import com.example.restaurant.model.Server;

@org.springframework.stereotype.Repository
public class ServerRepository extends Repository<Server> {

    @Override
    protected String getId(Server chelner) {
        return chelner.getId();
    }

}
