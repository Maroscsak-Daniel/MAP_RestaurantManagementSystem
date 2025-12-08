package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/menu")
public class MenuItemController {

    private final MenuItemService service;

    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", service.getAll());
        return "menu/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("item", new MenuItem());
        return "menu/form";
    }

    @PostMapping
    public String create(@ModelAttribute MenuItem item) {
        service.create(item);
        return "redirect:/menu";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("item", service.getById(id));
        return "menu/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", service.getById(id));
        return "menu/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute MenuItem item) {
        service.update(id, item);
        return "redirect:/menu";
    }

    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("success", "Item deleted successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/menu";
    }


}
