package com.example.restaurant.controller;

import com.example.restaurant.model.RestaurantTable;
import com.example.restaurant.service.RestaurantTableService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tables")
public class RestaurantTableController {

    private final RestaurantTableService service;

    public RestaurantTableController(RestaurantTableService service) {
        this.service = service;
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String list(@RequestParam(required = false) Integer number,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {

        var page = service.getAllPaged(number, status, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("tables", page.getContent());

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("number", number == null ? "" : number);
        model.addAttribute("status", status == null ? "" : status);

        return "tables/index";
    }

    // -------------------- CREATE: FORM --------------------
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("table", new RestaurantTable());
        return "tables/form";
    }

    // -------------------- CREATE: ACTION --------------------
    @PostMapping
    public String create(@ModelAttribute RestaurantTable table, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.create(table);
            redirectAttributes.addFlashAttribute("success", "Table created.");
            return "redirect:/tables";
        } catch (Exception e) {
            model.addAttribute("table", table);
            model.addAttribute("error", e.getMessage());
            return "tables/form";
        }
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("table", service.getById(id));
        return "tables/details";
    }

    // -------------------- EDIT: FORM --------------------
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("table", service.getById(id));
        return "tables/form";
    }

    // -------------------- EDIT: ACTION --------------------
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute RestaurantTable table, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.update(id, table);
            redirectAttributes.addFlashAttribute("success", "Table updated.");
            return "redirect:/tables";
        } catch (Exception e) {
            model.addAttribute("table", table);
            model.addAttribute("error", e.getMessage());
            return "tables/form";
        }
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", "Table deleted.");
        return "redirect:/tables";
    }
}
