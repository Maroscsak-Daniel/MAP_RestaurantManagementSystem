package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.service.MenuItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menuitems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/all")
    public List<MenuItem> getAll() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable String id) {
        return menuItemService.getMenuItemById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody MenuItem item) {
        menuItemService.addMenuItem(item);
        return "MenuItem added successfully!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        menuItemService.deleteMenuItem(id);
        return "MenuItem deleted successfully!";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        menuItemService.clearAll();
        return "All menu items cleared.";
    }
}
