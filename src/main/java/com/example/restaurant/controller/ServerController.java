package com.example.restaurant.controller;

import com.example.restaurant.model.Server;
import com.example.restaurant.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Adnotat cu @Controller, nu @RestController
@Controller
@RequestMapping("/servers") // Ruta de bază este /servers
public class ServerController {

    private final ServerService serverService;

    // Injectarea Service-ului (respectă principiul MVC) [cite: 134]
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
        // Opțional: Adăugăm date inițiale in-memory pentru testare
        if (serverService.getAllServers().isEmpty()) {
            serverService.addServer(new Server("S001", "Gigel Frumos", "2 ani", "Senior Waiter"));
            serverService.addServer(new Server("S002", "Ana Pop", "6 luni", "Junior Waiter"));
        }
    }

    // 1. GET /servers - Afișează lista completă (GET all) [cite: 64, 65]
    @GetMapping
    public String getAllServers(Model model) {
        // Adăugăm lista de servere la model, sub cheia "servers"
        model.addAttribute("servers", serverService.getAllServers());
        // Returnează numele template-ului Thymeleaf: templates/server/index.html
        return "server/index";
    }

    // 2. GET /servers/new - Afișează formularul de creare [cite: 66]
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Adăugăm un obiect Server gol la model, sub cheia "server"
        model.addAttribute("server", new Server());
        // Returnează numele template-ului Thymeleaf: templates/server/form.html
        return "server/form";
    }

    // 3. POST /servers - Procesează formularul și creează obiectul (CREATE) [cite: 66]
    @PostMapping
    public String createServer(@ModelAttribute Server server) {
        // Folosim ServerService pentru a salva obiectul (in-memory)
        serverService.addServer(server);
        // Redirecționează către lista de servere (GET /servers)
        return "redirect:/servers";
    }

    // 4. POST /servers/{id}/delete - Șterge obiectul [cite: 67]
    // Folosim POST pentru a evita complexitatea metodei DELETE [cite: 68]
    @PostMapping("/{id}/delete")
    public String deleteServer(@PathVariable String id) {
        // Folosim ServerService pentru a șterge obiectul
        serverService.deleteServer(id);
        // Redirecționează către lista actualizată (GET /servers) [cite: 124]
        return "redirect:/servers";
    }
}