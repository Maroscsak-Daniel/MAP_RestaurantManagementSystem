package com.example.restaurant.controller;

import com.example.restaurant.model.RestaurantTable;
import com.example.restaurant.service.RestaurantTableService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tables")
public class RestaurantTableController {

    private final RestaurantTableService service;

    public RestaurantTableController(RestaurantTableService service) {
        this.service = service;
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String list(@RequestParam(required = false) String number,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {

        Integer num = null;
        String filterError = null;
        if (number != null && !number.isEmpty()) {
            try {
                num = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                // invalid numeric input -> show error to user and do not apply numeric filter
                filterError = "Please enter a valid integer for Number.";
                num = null;
            }
        }

        var page = service.getAllPaged(num, status, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("tables", page.getContent());

        if (filterError != null) model.addAttribute("filterError", filterError);

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
    public String create(@Valid @ModelAttribute RestaurantTable table, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "tables/form";
        }

        try {
            service.create(table);
            redirectAttributes.addFlashAttribute("success", "Table created.");
            return "redirect:/tables";
        } catch (Exception e) {
            bindingResult.reject("globalError", e.getMessage());
            model.addAttribute("table", table);
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
    public String update(@PathVariable Long id, @Valid @ModelAttribute RestaurantTable table, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("table", table);
            return "tables/form";
        }

        try {
            service.update(id, table);
            redirectAttributes.addFlashAttribute("success", "Table updated.");
            return "redirect:/tables";
        } catch (Exception e) {
            bindingResult.reject("globalError", e.getMessage());
            model.addAttribute("table", table);
            return "tables/form";
        }
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("success", "Table deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tables";
    }
}
