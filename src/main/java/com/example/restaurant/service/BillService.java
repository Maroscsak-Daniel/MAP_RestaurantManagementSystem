package com.example.restaurant.service;

import com.example.restaurant.model.Bill;
import com.example.restaurant.repository.BillRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    private final BillRepo repo;

    public BillService(BillRepo repo) {
        this.repo = repo;
    }

    public void add(Bill bill) {
        repo.save(bill);
    }

    public List<Bill> getAll() {
        return repo.findAll();
    }

    public Bill getById(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public void clear() {
        repo.clear();
    }

    public List<Bill> getByOrderId(String orderId) {
        return repo.findAll().stream()
                .filter(b -> orderId.equals(b.getOrderId()))
                .collect(Collectors.toList());
    }
}