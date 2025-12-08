package com.example.restaurant.controller;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.service.OrderAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/assignments")
public class OrderAssignmentController {

    private final OrderAssignmentService service;

    public OrderAssignmentController(OrderAssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assignments", service.getAll());
        return "assignments/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment());
        return "assignments/form";
    }

    @PostMapping
    public String create(@ModelAttribute OrderAssignment assignment) {
        service.create(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.getById(id));
        return "assignments/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.getById(id));
        return "assignments/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("assignment") OrderAssignment assignment,
            RedirectAttributes redirectAttributes
    ) {
        try {
            service.update(id, assignment);
            redirectAttributes.addFlashAttribute("success", "Assignment updated.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/assignments";
    }
}
