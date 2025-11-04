package com.example.restaurant.controller;

import com.example.restaurant.model.Bill;
import com.example.restaurant.service.BillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bills")
public class BillController{

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
        // Date inițiale pentru testare
        if (billService.getAll().isEmpty()) {
            billService.add(new Bill("B001", "O100", 55.50));
            billService.add(new Bill("B002", "O101", 120.00));
        }
    }

    // GET /bills - Afișează lista completă (GET all)
    @GetMapping
    public String getAllBills(Model model) {
        model.addAttribute("bills", billService.getAll());
        // Returnează templates/bill/index.html
        return "bill/index";
    }

    // GET /bills/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bill", new Bill(null, null, 0.0));
        // Returnează templates/bill/form.html
        return "bill/form";
    }

    // POST /bills - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createBill(@ModelAttribute Bill bill) {
        billService.add(bill);
        return "redirect:/bills";
    }

    // POST /bills/{id}/delete - Șterge obiectul
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable String id) {
        billService.delete(id);
        return "redirect:/bills";
    }
}