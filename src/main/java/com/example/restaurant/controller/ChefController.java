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
        // Date inițiale pentru testare
        if (chefService.getAllChefs().isEmpty()) {
            chefService.addChef(new Chef("CH01", "Ion Popescu", "10 ani", "Patiserie"));
            chefService.addChef(new Chef("CH02", "Maria Dobre", "5 ani", "Bucătărie Caldă"));
        }
    }

    //[cite_start]// GET /chefs - Afișează lista completă (GET all) [cite: 51, 64]
    @GetMapping
    public String getAllChefs(Model model) {
        model.addAttribute("chefs", chefService.getAllChefs());
        // Returnează templates/chef/index.html
        return "chef/index";
    }

    //[cite_start]// GET /chefs/new - Afișează formularul de creare [cite: 52, 66]
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Obiect Chef gol, gata să primească date
        model.addAttribute("chef", new Chef());
        // Returnează templates/chef/form.html
        return "chef/form";
    }

    //[cite_start]// POST /chefs - Procesează formularul și creează obiectul (CREATE) [cite: 66]
    @PostMapping
    public String createChef(@ModelAttribute Chef chef) {
        //[cite_start]// Controllerul apelează Service-ul (respectă SRP și MVC) [cite: 130, 131, 134]
        chefService.addChef(chef);
        return "redirect:/chefs";
    }

    //[cite_start]// POST /chefs/{id}/delete - Șterge obiectul [cite: 53, 67]
    @PostMapping("/{id}/delete")
    public String deleteChef(@PathVariable String id) {
        chefService.deleteChef(id);
        return "redirect:/chefs";
    }
}