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

    // GET /assignments - list all
    @GetMapping
    public String getAllOrderAssignments(Model model) {
        model.addAttribute("assignments", orderAssignmentService.getAll());
        return "assignment/index";
    }

    // GET /assignments/new - create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment());
        return "assignment/form";
    }

    // POST /assignments - create
    @PostMapping
    public String createOrderAssignment(@ModelAttribute OrderAssignment assignment) {
        orderAssignmentService.add(assignment);
        return "redirect:/assignments";
    }

    // GET /assignments/{id}/edit - edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        OrderAssignment assignment = orderAssignmentService.getById(id);
        if (assignment == null) {
            return "redirect:/assignments";
        }
        model.addAttribute("assignment", assignment);
        return "assignment/form";
    }

    // POST /assignments/{id} - update
    @PostMapping("/{id}")
    public String updateOrderAssignment(@PathVariable String id,
                                        @ModelAttribute OrderAssignment assignment) {
        assignment.setId(id);
        orderAssignmentService.update(assignment);
        return "redirect:/assignments";
    }

    // POST /assignments/{id}/delete - delete
    @PostMapping("/{id}/delete")
    public String deleteOrderAssignment(@PathVariable String id) {
        orderAssignmentService.delete(id);
        return "redirect:/assignments";
    }
}
