package com.example.restaurant.repository;

import com.example.restaurant.model.Table;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepo extends IRepository<Table> {

    @Override
    protected String getId(Table table) {
        return table.getId();
    }

}
