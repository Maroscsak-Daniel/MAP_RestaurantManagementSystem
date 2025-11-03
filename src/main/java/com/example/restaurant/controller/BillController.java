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

    @GetMapping
    public String listBills(Model model) {
        model.addAttribute("bills", billService.getAll());
        return "bill/index"; // → templates/bill/index.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("bill", new Bill("", "", 0.0));
        return "bill/form"; // → templates/bill/form.html
    }

    @PostMapping
    public String addBill(@ModelAttribute Bill bill) {
        billService.add(bill);
        return "redirect:/bills";
    }

    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable String id) {
        billService.delete(id);
        return "redirect:/bills";
    }
}
