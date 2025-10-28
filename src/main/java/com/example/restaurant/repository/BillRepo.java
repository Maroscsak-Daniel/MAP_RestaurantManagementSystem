package com.example.restaurant.repository;

import com.example.restaurant.model.Bill;
import org.springframework.stereotype.Repository;

@Repository
public class BillRepo extends IRepository<Bill>{

    @Override
    protected String getId(Bill bill) {
        return bill.getId();
    }

}
