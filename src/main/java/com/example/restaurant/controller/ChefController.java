package com.example.restaurant.controller;

import com.example.restaurant.model.Chef;
import com.example.restaurant.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chefs")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    // GET /chefs - list all
    @GetMapping
    public String getAllChefs(Model model) {
        model.addAttribute("chefs", chefService.getAllChefs());
        return "chef/index";
    }

    // GET /chefs/new - create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chef/form";
    }

    // POST /chefs - create
    @PostMapping
    public String createChef(@ModelAttribute Chef chef) {
        chefService.addChef(chef);
        return "redirect:/chefs";
    }

    // GET /chefs/{id}/edit - edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Chef chef = chefService.getChefById(id);
        if (chef == null) {
            return "redirect:/chefs";
        }
        model.addAttribute("chef", chef);
        return "chef/form";
    }

    // POST /chefs/{id} - update
    @PostMapping("/{id}")
    public String updateChef(@PathVariable String id, @ModelAttribute Chef chef) {
        chef.setId(id);
        chefService.updateChef(chef);
        return "redirect:/chefs";
    }

    // POST /chefs/{id}/delete - delete
    @PostMapping("/{id}/delete")
    public String deleteChef(@PathVariable String id) {
        chefService.deleteChef(id);
        return "redirect:/chefs";
    }
}
