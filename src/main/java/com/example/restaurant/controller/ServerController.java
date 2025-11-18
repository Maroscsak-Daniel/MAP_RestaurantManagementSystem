package com.example.restaurant.controller;

import com.example.restaurant.model.Server;
import com.example.restaurant.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    // -------------------- LIST --------------------
    @GetMapping
    public String getAllServers(Model model) {
        model.addAttribute("servers", serverService.getAllServers());
        return "server/index";
    }

    // -------------------- DETAILS --------------------
    @GetMapping("/{id}")
    public String getServerDetails(@PathVariable String id, Model model) {
        Server server = serverService.getServerById(id);
        if (server == null)
            return "redirect:/servers";

        model.addAttribute("server", server);
        return "server/details";
    }

    // -------------------- CREATE FORM --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("server", new Server());
        return "server/form";
    }

    // -------------------- CREATE ACTION --------------------
    @PostMapping
    public String createServer(@ModelAttribute Server server) {
        serverService.addServer(server);
        return "redirect:/servers";
    }

    // -------------------- EDIT FORM --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        Server server = serverService.getServerById(id);
        if (server == null)
            return "redirect:/servers";

        model.addAttribute("server", server);
        return "server/form";
    }

    // -------------------- UPDATE ACTION --------------------
    @PostMapping("/{id}")
    public String updateServer(@PathVariable String id, @ModelAttribute Server server) {

        server.setId(id);
        serverService.updateServer(server);
        return "redirect:/servers";
    }

    // -------------------- DELETE --------------------
    @PostMapping("/{id}/delete")
    public String deleteServer(@PathVariable String id) {
        serverService.deleteServer(id);
        return "redirect:/servers";
    }
}
