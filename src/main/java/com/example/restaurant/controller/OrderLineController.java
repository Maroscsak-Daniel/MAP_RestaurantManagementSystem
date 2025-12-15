package com.example.restaurant.controller;

import com.example.restaurant.model.OrderLine;
import com.example.restaurant.service.OrderLineService;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderlines")
public class OrderLineController {

    private final OrderLineService orderLineService;
    private final OrderService orderService;
    private final MenuItemService menuItemService;

    public OrderLineController(OrderLineService ols,
                               OrderService os,
                               MenuItemService mis) {
        this.orderLineService = ols;
        this.orderService = os;
        this.menuItemService = mis;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orderlines", orderLineService.getAll());
        return "orderlines/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("orderline", new OrderLine());
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("menuItems", menuItemService.getAll());
        return "orderlines/form";
    }

    @PostMapping
    public String create(@ModelAttribute OrderLine orderLine, Model model) {
        try {
            orderLineService.create(orderLine);
            return "redirect:/orderlines";
        } catch (Exception e) {
            model.addAttribute("orderline", orderLine);
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("menuItems", menuItemService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orderlines/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("orderline", orderLineService.getById(id));
        return "orderlines/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        OrderLine ol = orderLineService.getById(id);

        ol.setOrderId(ol.getOrder().getId());
        if (ol.getMenuItem() != null)
            ol.setMenuItemId(ol.getMenuItem().getId());

        model.addAttribute("orderline", ol);
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("menuItems", menuItemService.getAll());
        return "orderlines/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute OrderLine orderLine,
                         Model model) {
        try {
            orderLineService.update(id, orderLine);
            return "redirect:/orderlines";
        } catch (Exception e) {
            model.addAttribute("orderline", orderLine);
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("menuItems", menuItemService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orderlines/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        try {
            orderLineService.delete(id);
            return "redirect:/orderlines";
        } catch (Exception e) {
            model.addAttribute("orderlines", orderLineService.getAll());
            model.addAttribute("error", e.getMessage());
            return "orderlines/index";
        }
    }
}
