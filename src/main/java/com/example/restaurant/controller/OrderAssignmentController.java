package com.example.restaurant.controller;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.service.OrderAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class OrderAssignmentController {

    private final OrderAssignmentService orderAssignmentService;

    public OrderAssignmentController(OrderAssignmentService orderAssignmentService) {
        this.orderAssignmentService = orderAssignmentService;
        // Date inițiale pentru testare
        if (orderAssignmentService.getAll().isEmpty()) {
            // Presupunând că avem Order O100 și Staff S001, CH01
            orderAssignmentService.add(new OrderAssignment("A01", "O100", "S001"));
            orderAssignmentService.add(new OrderAssignment("A02", "O100", "CH01"));
        }
    }

    // GET /assignments - Afișează lista completă (GET all)
    @GetMapping
    public String getAllOrderAssignments(Model model) {
        model.addAttribute("assignments", orderAssignmentService.getAll());
        // Returnează templates/assignment/index.html (am schimbat directorul în 'assignment' pentru simplitate)
        return "assignment/index";
    }

    // GET /assignments/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment(null, null, null));
        // Returnează templates/assignment/form.html
        return "assignment/form";
    }

    // POST /assignments - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createOrderAssignment(@ModelAttribute OrderAssignment assignment) {
        orderAssignmentService.add(assignment);
        return "redirect:/assignments";
    }

    // POST /assignments/{id}/delete - Șterge obiectul
    @PostMapping("/{id}/delete")
    public String deleteOrderAssignment(@PathVariable String id) {
        orderAssignmentService.delete(id);
        return "redirect:/assignments";
    }
}