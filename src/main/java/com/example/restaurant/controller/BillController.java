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
    public String getAllBills(Model model) {
        model.addAttribute("bills", billService.getAll());
        return "bill/index";
    }

    // DETAILS
    @GetMapping("/{id}")
    public String getBillDetails(@PathVariable String id, Model model) {
        Bill bill = billService.getById(id);
        if (bill == null) {
            return "redirect:/bills";
        }
        model.addAttribute("bill", bill);
        return "bill/details";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bill", new Bill());
        return "bill/form";
    }

    // CREATE ACTION
    @PostMapping
    public String createBill(@ModelAttribute Bill bill) {
        billService.add(bill);
        return "redirect:/bills";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Bill bill = billService.getById(id);
        if (bill == null) {
            return "redirect:/bills";
        }
        model.addAttribute("bill", bill);
        return "bill/form";
    }

    // UPDATE ACTION
    @PostMapping("/{id}")
    public String updateBill(@PathVariable String id, @ModelAttribute Bill bill) {
        bill.setId(id);
        billService.update(bill);
        return "redirect:/bills";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable String id) {
        billService.delete(id);
        return "redirect:/bills";
    }
}
