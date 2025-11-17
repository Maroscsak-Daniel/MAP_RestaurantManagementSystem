package com.example.restaurant.repository;

import com.example.restaurant.model.Bill;
import org.springframework.stereotype.Repository;

@Repository
public class BillRepository extends InFileRepository<Bill> {

    public BillRepository() {
        super("bills.json", Bill.class);
    }
}
