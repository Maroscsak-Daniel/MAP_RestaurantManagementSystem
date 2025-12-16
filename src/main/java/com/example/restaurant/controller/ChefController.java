package com.example.restaurant.controller;

import com.example.restaurant.model.Chef;
import com.example.restaurant.service.ChefService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chefs")
public class ChefController {

    private final ChefService service;

    public ChefController(ChefService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String rank,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {

        var page = service.getAllPaged(name, rank, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("chefs", page.getContent());

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("name", name == null ? "" : name);
        model.addAttribute("rank", rank == null ? "" : rank);

        return "chefs/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chefs/form";
    }

    @PostMapping
    public String create(@ModelAttribute Chef chef, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.create(chef);
            redirectAttributes.addFlashAttribute("success", "Chef created.");
            return "redirect:/chefs";
        } catch (Exception e) {
            model.addAttribute("chef", chef);
            model.addAttribute("error", e.getMessage());
            return "chefs/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("chef", service.getById(id));
        return "chefs/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("chef", service.getById(id));
        return "chefs/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Chef chef, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.update(id, chef);
            redirectAttributes.addFlashAttribute("success", "Chef updated.");
            return "redirect:/chefs";
        } catch (Exception e) {
            model.addAttribute("chef", chef);
            model.addAttribute("error", e.getMessage());
            return "chefs/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", "Chef deleted.");
        return "redirect:/chefs";
    }
}