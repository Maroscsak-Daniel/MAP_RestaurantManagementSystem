package com.example.restaurant.controller;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.service.ChefService;
import com.example.restaurant.service.OrderAssignmentService;
import com.example.restaurant.service.ServerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/assignments")
public class OrderAssignmentController {

    private final OrderAssignmentService service;
    private final ChefService chefService;
    private final ServerService serverService;

    public OrderAssignmentController(OrderAssignmentService service,
                                     ChefService chefService,
                                     ServerService serverService) {
        this.service = service;
        this.chefService = chefService;
        this.serverService = serverService;
    }

    // helper to build staff options (e.g. "S3: Server 3", "C5: Chef 5")
    private List<String> buildStaffOptions() {
        List<String> opts = new ArrayList<>();
        chefService.getAll().forEach(c -> opts.add("C" + c.getId() + " - " + c.getName()));
        serverService.getAll().forEach(s -> opts.add("S" + s.getId() + " - " + s.getName()));
        return opts;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Long orderId,
                       @RequestParam(required = false) String staff,
                       @RequestParam(required = false, name = "sort") String sortBy,
                       @RequestParam(required = false, name = "dir") String dir,
                       org.springframework.data.domain.Pageable pageable,
                       Model model) {

        var page = service.getAllPaged(orderId, staff, sortBy == null ? "id" : sortBy, dir == null ? "asc" : dir, pageable);
        model.addAttribute("page", page);
        model.addAttribute("assignments", page.getContent());

        model.addAttribute("currentOrder", orderId == null ? "" : orderId);
        model.addAttribute("currentStaff", staff == null ? "" : staff);
        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);

        // provide orders for filter select
        model.addAttribute("orders", service.getAllOrdersForSelection());

        return "assignments/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment());
        model.addAttribute("orders", service.getAllOrdersForSelection());
        model.addAttribute("chefs", chefService.getAll());
        model.addAttribute("servers", serverService.getAll());
        return "assignments/form";
    }

    @PostMapping
    public String create(@ModelAttribute OrderAssignment assignment,
                         @RequestParam(required = false) Long chefId,
                         @RequestParam(required = false) Long serverId,
                         Model model,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            // determine staffId
            if (chefId != null && chefId > 0) {
                assignment.setStaffId("C" + chefId);
            } else if (serverId != null && serverId > 0) {
                assignment.setStaffId("S" + serverId);
            } else {
                throw new IllegalArgumentException("Please select a Chef or a Server.");
            }

            service.create(assignment);
            redirectAttributes.addFlashAttribute("success", "Assignment created successfully.");
            return "redirect:/assignments";
        } catch (Exception e) {
            model.addAttribute("assignment", assignment);
            model.addAttribute("orders", service.getAllOrdersForSelection());
            model.addAttribute("chefs", chefService.getAll());
            model.addAttribute("servers", serverService.getAll());
            // provide selected values back to template
            if (assignment.getStaffId() != null) {
                if (assignment.getStaffId().startsWith("C")) model.addAttribute("selectedChef", Long.valueOf(assignment.getStaffId().substring(1)));
                if (assignment.getStaffId().startsWith("S")) model.addAttribute("selectedServer", Long.valueOf(assignment.getStaffId().substring(1)));
            }
            model.addAttribute("error", e.getMessage());
            return "assignments/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.getById(id));
        return "assignments/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.getById(id));
        model.addAttribute("orders", service.getAllOrdersForSelection());
        model.addAttribute("chefs", chefService.getAll());
        model.addAttribute("servers", serverService.getAll());
        // preselect chef/server based on existing staffId
        var existing = service.getById(id);
        if (existing.getStaffId() != null) {
            if (existing.getStaffId().startsWith("C")) model.addAttribute("selectedChef", Long.valueOf(existing.getStaffId().substring(1)));
            if (existing.getStaffId().startsWith("S")) model.addAttribute("selectedServer", Long.valueOf(existing.getStaffId().substring(1)));
        }
        return "assignments/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("assignment") OrderAssignment assignment,
            RedirectAttributes redirectAttributes,
            @RequestParam(required = false) Long chefId,
            @RequestParam(required = false) Long serverId,
            Model model
    ) {
        try {
            // set staffId from selection
            if (chefId != null && chefId > 0) {
                assignment.setStaffId("C" + chefId);
            } else if (serverId != null && serverId > 0) {
                assignment.setStaffId("S" + serverId);
            }

            service.update(id, assignment);
            redirectAttributes.addFlashAttribute("success", "Assignment updated.");
            return "redirect:/assignments";
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("assignment", assignment);
            model.addAttribute("orders", service.getAllOrdersForSelection());
            model.addAttribute("chefs", chefService.getAll());
            model.addAttribute("servers", serverService.getAll());
            if (assignment.getStaffId() != null) {
                if (assignment.getStaffId().startsWith("C")) model.addAttribute("selectedChef", Long.valueOf(assignment.getStaffId().substring(1)));
                if (assignment.getStaffId().startsWith("S")) model.addAttribute("selectedServer", Long.valueOf(assignment.getStaffId().substring(1)));
            }
            model.addAttribute("error", ex.getMessage());
            return "assignments/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        try {
            service.delete(id);
            return "redirect:/assignments";

        } catch (Exception e) {
            // prepare index view with error message
            var page = service.getAllPaged(null, null, "id", "asc", PageRequest.of(0, 20));
            model.addAttribute("page", page);
            model.addAttribute("assignments", page.getContent());
            model.addAttribute("orders", service.getAllOrdersForSelection());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("currentOrder", "");
            model.addAttribute("currentStaff", "");
            model.addAttribute("currentSort", "id");
            model.addAttribute("currentDir", "asc");
            return "assignments/index";
        }
    }
}
