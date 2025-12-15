package com.example.restaurant.controller;

import com.example.restaurant.model.Chef;
import com.example.restaurant.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chefs")
public class ChefController {

    private final ChefService service;

    public ChefController(ChefService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("chefs", service.getAll());
        return "chefs/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chefs/form";
    }

    @PostMapping
    public String create(@ModelAttribute Chef chef) {
        service.create(chef);
        return "redirect:/chefs";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("chef", service.getById(id));
        return "chefs/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("chef", service.getById(id));
        return "chefs/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Chef chef) {
        service.update(id, chef);
        return "redirect:/chefs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/chefs";
    }
}
