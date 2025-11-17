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

    // GET /servers - list all
    @GetMapping
    public String getAllServers(Model model) {
        model.addAttribute("servers", serverService.getAllServers());
        return "server/index";
    }

    // GET /servers/new - create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("server", new Server());
        return "server/form";
    }

    // POST /servers - create
    @PostMapping
    public String createServer(@ModelAttribute Server server) {
        serverService.addServer(server);
        return "redirect:/servers";
    }

    // GET /servers/{id}/edit - edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Server server = serverService.getServerById(id);
        if (server == null) {
            return "redirect:/servers";
        }
        model.addAttribute("server", server);
        return "server/form";
    }

    // POST /servers/{id} - update
    @PostMapping("/{id}")
    public String updateServer(@PathVariable String id, @ModelAttribute Server server) {
        server.setId(id);
        serverService.updateServer(server);
        return "redirect:/servers";
    }

    // POST /servers/{id}/delete - delete
    @PostMapping("/{id}/delete")
    public String deleteServer(@PathVariable String id) {
        serverService.deleteServer(id);
        return "redirect:/servers";
    }
}
