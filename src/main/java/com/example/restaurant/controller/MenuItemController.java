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

    // GET /menuitems - list all
    @GetMapping
    public String getAllMenuItems(Model model) {
        model.addAttribute("menuitems", menuItemService.getAllMenuItems());
        return "menuitem/index";
    }

    // GET /menuitems/new - create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("menuitem", new MenuItem());
        return "menuitem/form";
    }

    // POST /menuitems - create
    @PostMapping
    public String createMenuItem(@ModelAttribute MenuItem menuitem) {
        menuItemService.addMenuItem(menuitem);
        return "redirect:/menuitems";
    }

    // GET /menuitems/{id}/edit - edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        MenuItem item = menuItemService.getMenuItemById(id);
        if (item == null) {
            return "redirect:/menuitems";
        }
        model.addAttribute("menuitem", item);
        return "menuitem/form";
    }

    // POST /menuitems/{id} - update
    @PostMapping("/{id}")
    public String updateMenuItem(@PathVariable String id, @ModelAttribute MenuItem menuitem) {
        menuitem.setId(id);
        menuItemService.updateMenuItem(menuitem);
        return "redirect:/menuitems";
    }

    // POST /menuitems/{id}/delete - delete
    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable String id) {
        menuItemService.deleteMenuItem(id);
        return "redirect:/menuitems";
    }
}
