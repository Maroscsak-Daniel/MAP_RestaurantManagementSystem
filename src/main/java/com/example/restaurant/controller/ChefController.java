package com.example.restaurant.controller;

import com.example.restaurant.model.Chef;
import com.example.restaurant.service.ChefService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chefs")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping("/all")
    public List<Chef> getAll() {
        return chefService.getAllChefs();
    }

    @GetMapping("/{id}")
    public Chef getById(@PathVariable String id) {
        return chefService.getChefById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Chef chef) {
        chefService.addChef(chef);
        return "Chef added successfully!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        chefService.deleteChef(id);
        return "Chef deleted successfully!";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        chefService.clearAll();
        return "All chefs cleared.";
    }
}
