package com.example.restaurant.repository;

import com.example.restaurant.model.Bill;
import com.example.restaurant.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class BillRepo  implements AbstractRepository < Bill>{
    private List<Bill> billRepo =  new ArrayList<>();


    @Override
    public Bill save(Bill bill) {
        delete(bill.getId());
        billRepo.add(bill);
        return bill;
    }

    @Override
    public List<Bill> findAll() {
        return new ArrayList<>(billRepo);
    }

    @Override
    public Bill findById(String id) {
        for (Bill b : billRepo) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public Bill delete(String id) {
        billRepo.removeIf(c -> c.getId().equals(id));
        return findById(id);
    }

    public void clear() {
        billRepo.clear();
    }

}
