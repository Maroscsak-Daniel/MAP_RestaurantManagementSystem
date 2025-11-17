package com.example.restaurant.service;

import com.example.restaurant.model.Bill;
import com.example.restaurant.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    private final BillRepository repo;

    public BillService(BillRepository repo) {
        this.repo = repo;
    }

    public void add(Bill bill) {
        repo.add(bill);
    }

    public void update(Bill bill) {
        repo.update(bill);
    }

    public List<Bill> getAll() {
        return repo.getAll();
    }

    public Bill getById(String id) {
        return repo.getById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public List<Bill> getByOrderId(String orderId) {
        return repo.getAll().stream()
                .filter(b -> orderId.equals(b.getOrderId()))
                .collect(Collectors.toList());
    }
}
