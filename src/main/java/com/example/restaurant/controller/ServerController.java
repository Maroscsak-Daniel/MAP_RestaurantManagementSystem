package com.example.restaurant.controller;

import com.example.restaurant.model.Server;
import com.example.restaurant.service.ServerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/all")
    public List<Server> getAll() {
        return serverService.getAllServers();
    }

    @GetMapping("/{id}")
    public Server getById(@PathVariable String id) {
        return serverService.getServerById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Server server) {
        serverService.addServer(server);
        return "Server added successfully!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        serverService.deleteServer(id);
        return "Server deleted successfully!";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        serverService.clearAll();
        return "All servers cleared.";
    }
}
