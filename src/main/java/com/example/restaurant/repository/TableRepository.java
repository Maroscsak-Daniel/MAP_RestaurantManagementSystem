package com.example.restaurant.repository;

import com.example.restaurant.model.Table;

@org.springframework.stereotype.Repository
public class TableRepository extends Repository<Table> {

    @Override
    protected String getId(Table table) {
        return table.getId();
    }

}
