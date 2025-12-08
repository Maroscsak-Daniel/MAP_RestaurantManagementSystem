package com.example.restaurant.controller;

import com.example.restaurant.model.Server;
import com.example.restaurant.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servers")
public class ServerController {

    private final ServerService service;

    public ServerController(ServerService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("servers", service.getAll());
        return "servers/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("server", new Server());
        return "servers/form";
    }

    @PostMapping
    public String create(@ModelAttribute Server server) {
        service.create(server);
        return "redirect:/servers";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("server", service.getById(id));
        return "servers/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("server", service.getById(id));
        return "servers/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Server server) {
        service.update(id, server);
        return "redirect:/servers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/servers";
    }
}
