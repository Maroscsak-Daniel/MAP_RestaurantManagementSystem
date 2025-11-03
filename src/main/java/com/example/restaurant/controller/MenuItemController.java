package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menuitems") // Recomandat plural, dar poți folosi și /menuitem
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
        // Date inițiale pentru testare
        if (menuItemService.getAllMenuItems().isEmpty()) {
            menuItemService.addMenuItem(new MenuItem("M001", "Cheeseburger", 35.00));
            menuItemService.addMenuItem(new MenuItem("M002", "Salată Cezar", 28.50));
        }
    }

    // GET /menuitems - Afișează lista completă (GET all)
    @GetMapping
    public String getAllMenuItems(Model model) {
        model.addAttribute("menuitems", menuItemService.getAllMenuItems());
        // Returnează templates/menuitem/index.html
        return "menuitem/index";
    }

    // GET /menuitems/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("menuitem", new MenuItem());
        // Returnează templates/menuitem/form.html
        return "menuitem/form";
    }

    // POST /menuitems - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createMenuItem(@ModelAttribute MenuItem menuitem) {
        menuItemService.addMenuItem(menuitem);
        return "redirect:/menuitems";
    }

    // POST /menuitems/{id}/delete - Șterge obiectul
    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable String id) {
        menuItemService.deleteMenuItem(id);
        return "redirect:/menuitems";
    }
}