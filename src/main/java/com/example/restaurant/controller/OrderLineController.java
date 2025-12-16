package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.model.OrderLine;
import com.example.restaurant.service.MenuItemService;
import com.example.restaurant.service.OrderLineService;
import com.example.restaurant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orderlines")
public class OrderLineController {

    private final OrderLineService service;
    private final OrderService orderService;
    private final MenuItemService menuItemService;

    public OrderLineController(OrderLineService service, OrderService orderService, MenuItemService menuItemService) {
        this.service = service;
        this.orderService = orderService;
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Long orderId,
                       @RequestParam(required = false) String menuName,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {
        var page = service.getAllPaged(orderId, menuName, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("orderlines", page.getContent());

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("orderId", orderId == null ? "" : orderId);
        model.addAttribute("menuName", menuName == null ? "" : menuName);

        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("menuItems", menuItemService.getAll());

        return "orderlines/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("orderline", new OrderLine());
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("menuItems", menuItemService.getAll());
        return "orderlines/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute OrderLine orderline,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orderline", orderline);
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("menuItems", menuItemService.getAll());
            return "orderlines/form";
        }

        try {
            service.create(orderline);
            redirectAttributes.addFlashAttribute("success", "OrderLine created.");
            return "redirect:/orderlines";
        } catch (Exception e) {
            model.addAttribute("orderline", orderline);
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("menuItems", menuItemService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orderlines/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("orderline", service.getById(id));
        return "orderlines/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("orderline", service.getById(id));
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("menuItems", menuItemService.getAll());
        return "orderlines/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute OrderLine orderline, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orderline", orderline);
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("menuItems", menuItemService.getAll());
            return "orderlines/form";
        }

        try {
            service.update(id, orderline);
            redirectAttributes.addFlashAttribute("success", "OrderLine updated.");
            return "redirect:/orderlines";
        } catch (Exception e) {
            model.addAttribute("orderline", orderline);
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("menuItems", menuItemService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orderlines/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", "OrderLine deleted.");
        return "redirect:/orderlines";
    }
}
