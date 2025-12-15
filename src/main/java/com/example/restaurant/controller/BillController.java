package com.example.restaurant.controller;

import com.example.restaurant.model.Bill;
import com.example.restaurant.service.BillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    // LIST
    @GetMapping
    public String listBills(Model model) {
        model.addAttribute("bills", billService.getAll());
        return "bills/index";
    }

    // DETAILS
    @GetMapping("/{id}")
    public String billDetails(@PathVariable Long id, Model model) {
        Bill bill = billService.getById(id);
        model.addAttribute("bill", bill);
        return "bills/details";
    }

    // TOGGLE PAYMENT STATUS
    @PostMapping("/{id}/toggle")
    public String toggleBill(@PathVariable Long id) {
        billService.togglePaymentStatus(id);
        return "redirect:/bills/" + id;
    }

    // DELETE (ONLY allowed if order is deleted)
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable Long id) {
        billService.delete(id);
        return "redirect:/bills";
    }
}
