package com.example.restaurant.service;

import com.example.restaurant.model.Bill;
import com.example.restaurant.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository repo;

    public BillService(BillRepository repo) {
        this.repo = repo;
    }

    public List<Bill> getAll() {
        return repo.findAll();
    }

    public Bill getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found: " + id));
    }

    public Bill create(Bill bill) {
        return repo.save(bill);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ---- TOGGLE PAYMENT STATUS ----
    public void togglePaymentStatus(Long id) {
        Bill bill = getById(id);

        String current = bill.getPaymentStatus();
        if ("Paid".equalsIgnoreCase(current)) {
            bill.setPaymentStatus("Unpaid");
        } else {
            bill.setPaymentStatus("Paid");
        }

        repo.save(bill);
    }
}
