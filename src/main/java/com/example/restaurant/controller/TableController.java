package com.example.restaurant.controller;

import com.example.restaurant.model.Table;
import com.example.restaurant.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList; // Necesara pentru constructorul Table

@Controller
@RequestMapping("/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
        // Date inițiale pentru testare
        if (tableService.getAll().isEmpty()) {
            tableService.add(new Table("T1", 1, "Occupied", new ArrayList<>()));
            tableService.add(new Table("T2", 2, "Free", new ArrayList<>()));
            tableService.add(new Table("T3", 3, "Free", new ArrayList<>()));
        }
    }

    // GET /tables - Afișează lista completă (GET all)
    @GetMapping
    public String getAllTables(Model model) {
        model.addAttribute("tables", tableService.getAll());
        // Returnează templates/table/index.html
        return "table/index";
    }

    // GET /tables/new - Afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Obiect Table gol. Orders este lăsat null sau ArrayList gol.
        model.addAttribute("table", new Table(null, 0, "Free", new ArrayList<>()));
        // Returnează templates/table/form.html
        return "table/form";
    }

    // POST /tables - Procesează formularul și creează obiectul (CREATE)
    @PostMapping
    public String createTable(@ModelAttribute Table table) {
        // Ne asigurăm că lista de orders nu este null înainte de salvare, deși constructorul o setează
        if (table.getOrders() == null) {
            table.setOrders(new ArrayList<>());
        }
        tableService.add(table);
        return "redirect:/tables";
    }

    // POST /tables/{id}/delete - Șterge obiectul
    @PostMapping("/{id}/delete")
    public String deleteTable(@PathVariable String id) {
        tableService.delete(id);
        return "redirect:/tables";
    }
}