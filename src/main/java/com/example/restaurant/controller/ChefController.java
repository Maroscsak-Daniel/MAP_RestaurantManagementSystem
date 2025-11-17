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

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllChefs(Model model) {
        model.addAttribute("chefs", chefService.getAllChefs());
        return "chef/index";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getChefDetails(@PathVariable String id, Model model) {
        Chef chef = chefService.getChefById(id);
        if (chef == null)
            return "redirect:/chefs";

        model.addAttribute("chef", chef);
        return "chef/details";
    }

    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chef/form";
    }

    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createChef(@ModelAttribute Chef chef) {
        chefService.addChef(chef);
        return "redirect:/chefs";
    }

    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Chef chef = chefService.getChefById(id);
        if (chef == null)
            return "redirect:/chefs";

        model.addAttribute("chef", chef);
        return "chef/form";
    }

    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateChef(@PathVariable String id, @ModelAttribute Chef chef) {
        chef.setId(id);
        chefService.updateChef(chef);
        return "redirect:/chefs";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteChef(@PathVariable String id) {
        chefService.deleteChef(id);
        return "redirect:/chefs";
    }
}
