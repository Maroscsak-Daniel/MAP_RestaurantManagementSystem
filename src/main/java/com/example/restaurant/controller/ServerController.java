package com.example.restaurant.controller;

import com.example.restaurant.model.Server;
import com.example.restaurant.service.ServerService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servers")
public class ServerController {

    private final ServerService service;

    public ServerController(ServerService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String shift,
                       @RequestParam(required = false) Integer minExp,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {

        var page = service.getAllPaged(shift, minExp, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("servers", page.getContent());

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("shift", shift == null ? "" : shift);
        model.addAttribute("minExp", minExp == null ? "" : minExp);

        return "servers/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("server", new Server());
        return "servers/form";
    }

    @PostMapping
    public String create(@ModelAttribute Server server, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.create(server);
            redirectAttributes.addFlashAttribute("success", "Server created.");
            return "redirect:/servers";
        } catch (Exception e) {
            model.addAttribute("server", server);
            model.addAttribute("error", e.getMessage());
            return "servers/form";
        }
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
    public String update(@PathVariable Long id, @ModelAttribute Server server, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.update(id, server);
            redirectAttributes.addFlashAttribute("success", "Server updated.");
            return "redirect:/servers";
        } catch (Exception e) {
            model.addAttribute("server", server);
            model.addAttribute("error", e.getMessage());
            return "servers/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", "Server deleted.");
        return "redirect:/servers";
    }
}
