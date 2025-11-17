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
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllOrderAssignments(Model model) {
        model.addAttribute("assignments", orderAssignmentService.getAll());
        return "assignment/index";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getAssignmentDetails(@PathVariable String id, Model model) {
        OrderAssignment assignment = orderAssignmentService.getById(id);
        if (assignment == null)
            return "redirect:/assignments";

        model.addAttribute("assignment", assignment);
        return "assignment/details";
    }

    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment());
        return "assignment/form";
    }

    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createOrderAssignment(@ModelAttribute OrderAssignment assignment) {
        orderAssignmentService.add(assignment);
        return "redirect:/assignments";
    }

    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        OrderAssignment assignment = orderAssignmentService.getById(id);
        if (assignment == null)
            return "redirect:/assignments";

        model.addAttribute("assignment", assignment);
        return "assignment/form";
    }

    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateOrderAssignment(@PathVariable String id,
                                        @ModelAttribute OrderAssignment assignment) {

        assignment.setId(id);
        orderAssignmentService.update(assignment);
        return "redirect:/assignments";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteOrderAssignment(@PathVariable String id) {
        orderAssignmentService.delete(id);
        return "redirect:/assignments";
    }
}
