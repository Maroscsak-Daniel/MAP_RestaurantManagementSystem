package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menuitems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String listMenuItems(Model model) {
        model.addAttribute("menuitems", menuItemService.getAllMenuItems());
        return "menuitem/index";   // → templates/menuitem/index.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("menuitem", new MenuItem());
        return "menuitem/form";    // → templates/menuitem/form.html
    }

    @PostMapping
    public String addMenuItem(@ModelAttribute MenuItem menuItem) {
        menuItemService.addMenuItem(menuItem);
        return "redirect:/menuitems";
    }

    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable String id) {
        menuItemService.deleteMenuItem(id);
        return "redirect:/menuitems";
    }
}
