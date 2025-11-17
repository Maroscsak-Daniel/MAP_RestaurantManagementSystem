package com.example.restaurant.repository;

import com.example.restaurant.model.Table;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepository extends InFileRepository<Table> {

    public TableRepository() {
        super("tables.json", Table.class);
    }
}
