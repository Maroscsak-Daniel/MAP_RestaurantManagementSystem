package com.example.restaurant.controller;

import com.example.restaurant.model.Table;
import com.example.restaurant.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {

    private final TableService service;

    public TableController(TableService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Table> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Table byId(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/free")
    public List<Table> free() {
        return service.getFreeTables();
    }

    @PostMapping("/add")
    public String add(@RequestBody Table t) {
        service.add(t);
        return "Table added.";
    }

    @PatchMapping("/{id}/status/{status}")
    public String setStatus(@PathVariable String id, @PathVariable String status) {
        service.setStatus(id, status);
        return "Table status updated.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Table deleted.";
    }

    @DeleteMapping("/clear")
    public String clear() {
        service.clear();
        return "All tables cleared.";
    }
}