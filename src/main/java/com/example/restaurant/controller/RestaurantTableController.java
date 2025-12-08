package com.example.restaurant.controller;

import com.example.restaurant.model.RestaurantTable;
import com.example.restaurant.service.RestaurantTableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tables")
public class RestaurantTableController {

    private final RestaurantTableService service;

    public RestaurantTableController(RestaurantTableService service) {
        this.service = service;
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String list(Model model) {
        model.addAttribute("tables", service.getAll());
        return "tables/index";  // templates/tables/index.html
    }

    // -------------------- CREATE: FORM --------------------
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("table", new RestaurantTable());
        return "tables/form";
    }

    // -------------------- CREATE: ACTION --------------------
    @PostMapping
    public String create(@ModelAttribute("table") RestaurantTable table) {
        service.create(table);
        return "redirect:/tables";
    }

    // -------------------- EDIT: FORM --------------------
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        RestaurantTable table = service.getById(id);
        model.addAttribute("table", table);
        return "tables/form";
    }

    // -------------------- EDIT: ACTION --------------------
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("table") RestaurantTable table
    ) {
        service.update(id, table);
        return "redirect:/tables";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        RestaurantTable table = service.getById(id);
        model.addAttribute("table", table);
        return "tables/details";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        try {
            service.delete(id);
            return "redirect:/tables";

        } catch (Exception e) {
            model.addAttribute("tables", service.getAll());
            model.addAttribute("error",
                    "Cannot delete this table because it is linked to existing orders or data.");

            // Stay on the tables page (NO redirect)
            return "tables/index";
        }
    }

}
