package com.example.restaurant.repository;

import com.example.restaurant.model.Bill;

@org.springframework.stereotype.Repository
public class BillRepository extends Repository<Bill> {

    @Override
    protected String getId(Bill bill) {
        return bill.getId();
    }

}
