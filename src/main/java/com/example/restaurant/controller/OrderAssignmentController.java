package com.example.restaurant.controller;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.service.OrderAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class OrderAssignmentController {

    private final OrderAssignmentService assignmentService;

    public OrderAssignmentController(OrderAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.getAll());
        return "assignment/index"; // → templates/assignment/index.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment("", "", ""));
        return "assignment/form"; // → templates/assignment/form.html
    }

    @PostMapping
    public String addAssignment(@ModelAttribute OrderAssignment assignment) {
        assignmentService.add(assignment);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable String id) {
        assignmentService.delete(id);
        return "redirect:/assignments";
    }
}
