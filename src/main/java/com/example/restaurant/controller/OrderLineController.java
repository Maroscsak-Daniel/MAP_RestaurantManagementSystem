package com.example.restaurant.controller;

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
    public String list(@RequestParam(required = false, name = "orderId") String orderIdParam,
                       @RequestParam(required = false) String menuName,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       Pageable pageable,
                       Model model) {

        Long orderId = null;
        String filterError = null;
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                orderId = Long.parseLong(orderIdParam);
                if (orderId < 1) {
                    filterError = "Order ID must be a positive integer.";
                    orderId = null;
                }
            } catch (NumberFormatException e) {
                filterError = "Please enter a valid integer for Order ID.";
            }
        }

        var page = service.getAllPaged(orderId, menuName, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("orderlines", page.getContent());

        // build map of editable/deletable flags per orderline: editable only when associated order is COMPLETED or CANCELLED
        java.util.Map<Long, Boolean> lineEditable = new java.util.HashMap<>();
        for (var ol : page.getContent()) {
            boolean allowed = false;
            if (ol.getOrder() != null && ol.getOrder().getStatus() != null) {
                var st = ol.getOrder().getStatus();
                allowed = st.name().equals("COMPLETED") || st.name().equals("CANCELLED");
            }
            lineEditable.put(ol.getId(), allowed);
        }
        model.addAttribute("lineEditableMap", lineEditable);

        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);
        model.addAttribute("orderId", orderIdParam == null ? "" : orderIdParam);
        model.addAttribute("menuName", menuName == null ? "" : menuName);

        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("menuItems", menuItemService.getAll());
        if (filterError != null) model.addAttribute("filterError", filterError);

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
            OrderLine created = service.create(orderline);
            redirectAttributes.addFlashAttribute("success", "OrderLine created.");
            // After creating an orderline, redirect to the order details so the line is visible there
            return "redirect:/orders/" + created.getOrder().getId();
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

        // determine if edit should be disabled (order CANCELLED or COMPLETED)
        var ol = service.getById(id);
        var order = ol.getOrder();
        boolean editable = false;
        if (order != null && order.getStatus() != null) {
            editable = order.getStatus().name().equals("COMPLETED") || order.getStatus().name().equals("CANCELLED");
        }
        // Save button should be disabled when NOT editable
        model.addAttribute("editDisabled", !editable);
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
            // After edit, return to orderlines list (do not redirect to order details per request)
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
        try {
            var ol = service.getById(id);
            Long orderId = ol.getOrder() != null ? ol.getOrder().getId() : null;
            service.delete(id);
            redirectAttributes.addFlashAttribute("success", "OrderLine deleted.");
            return "redirect:/orderlines";
        } catch (Exception e) {
            // if delete failed due to business rule, redirect back with error
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/orderlines";
        }
    }

}
