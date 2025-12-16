package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {

        var page = service.getAllPaged(name, category, minPrice, maxPrice, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("items", page.getContent());

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("name", name == null ? "" : name);
        model.addAttribute("category", category == null ? "" : category);
        model.addAttribute("currentMin", minPrice == null ? "" : minPrice);
        model.addAttribute("currentMax", maxPrice == null ? "" : maxPrice);

        return "menu/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("item", new MenuItem());
        return "menu/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("item") MenuItem item,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "menu/form";
        }

        service.create(item);
        redirectAttributes.addFlashAttribute("success", "Item created successfully.");
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
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("item") MenuItem item,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "menu/form";
        }

        service.update(id, item);
        redirectAttributes.addFlashAttribute("success", "Item updated successfully.");
        return "redirect:/menu";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("success", "Item deleted.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/menu";
    }

}
