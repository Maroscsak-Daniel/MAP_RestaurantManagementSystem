package com.example.restaurant.repository;

import com.example.restaurant.model.Server;
import org.springframework.stereotype.Repository;

@Repository
public class ServerRepository extends IRepository<Server> {

    @Override
    protected String getId(Server chelner) {
        return chelner.getId();
    }

}
