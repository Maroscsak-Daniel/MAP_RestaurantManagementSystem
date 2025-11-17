package com.example.restaurant.controller;

import com.example.restaurant.model.Table;
import com.example.restaurant.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllTables(Model model) {
        model.addAttribute("tables", tableService.getAll());
        return "table/index";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getTableDetails(@PathVariable String id, Model model) {
        Table table = tableService.getById(id);
        if (table == null) {
            return "redirect:/tables";
        }

        if (table.getOrderIds() == null) {
            table.setOrderIds(new ArrayList<>());
        }

        model.addAttribute("table", table);
        return "table/details";
    }

    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Table table = new Table();
        table.setOrderIds(new ArrayList<>());
        table.setOccupiedStatus("free");
        model.addAttribute("table", table);
        return "table/form";
    }

    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createTable(@ModelAttribute Table table) {
        if (table.getOrderIds() == null) {
            table.setOrderIds(new ArrayList<>());
        }
        tableService.add(table);
        return "redirect:/tables";
    }

    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Table table = tableService.getById(id);
        if (table == null) {
            return "redirect:/tables";
        }

        if (table.getOrderIds() == null)
            table.setOrderIds(new ArrayList<>());

        model.addAttribute("table", table);
        return "table/form";
    }

    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateTable(@PathVariable String id, @ModelAttribute Table table) {
        table.setId(id);

        if (table.getOrderIds() == null)
            table.setOrderIds(new ArrayList<>());

        tableService.update(table);
        return "redirect:/tables";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteTable(@PathVariable String id) {
        tableService.delete(id);
        return "redirect:/tables";
    }
}
