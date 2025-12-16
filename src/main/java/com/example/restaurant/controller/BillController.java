package com.example.restaurant.controller;

import com.example.restaurant.model.Bill;
import com.example.restaurant.model.PaymentStatus;
import com.example.restaurant.service.BillService;
import com.example.restaurant.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;
    private final OrderService orderService;

    public BillController(BillService billService, OrderService orderService) {
        this.billService = billService;
        this.orderService = orderService;
    }

    // LIST (with filtering & paging)
    @GetMapping
    public String listBills(@RequestParam(required = false) String status,
                            @RequestParam(required = false) Double min,
                            @RequestParam(required = false) Double max,
                            @RequestParam(required = false, name = "sort") String sortBy,
                            @RequestParam(required = false, name = "dir") String dir,
                            Pageable pageable,
                            Model model) {

        Page<Bill> page = billService.getAll(status, min, max, sortBy, dir, pageable);

        model.addAttribute("page", page);
        model.addAttribute("bills", page.getContent());

        // available statuses for filter dropdown
        List<String> statuses = Arrays.stream(PaymentStatus.values()).map(Enum::name).collect(Collectors.toList());
        model.addAttribute("statuses", statuses);

        // keep current filter/sort values so the form keeps them after submit
        model.addAttribute("currentStatus", status == null ? "" : status);
        model.addAttribute("currentMin", min == null ? "" : min);
        model.addAttribute("currentMax", max == null ? "" : max);
        model.addAttribute("currentSort", sortBy == null ? "id" : sortBy);
        model.addAttribute("currentDir", dir == null ? "asc" : dir);

        return "bills/index";
    }

    // DETAILS
    @GetMapping("/{id}")
    public String billDetails(@PathVariable Long id, Model model) {
        Bill bill = billService.getById(id);
        model.addAttribute("bill", bill);
        return "bills/details";
    }

    // NEW form
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("bill", new Bill());
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
        return "bills/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Bill bill,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        // If validation errors from annotations, re-display form
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
            return "bills/form";
        }

        // Ensure order id is provided (th:field creates nested Order even when none selected)
        if (bill.getOrder() == null || bill.getOrder().getId() == null) {
            bindingResult.rejectValue("order", "invalid.order", "Please select an order.");
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
            return "bills/form";
        }

        try {
            billService.create(bill);
            redirectAttributes.addFlashAttribute("success", "Bill created successfully.");
            return "redirect:/bills";
        } catch (DataIntegrityViolationException | IllegalStateException | IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("PAID")) {
                bindingResult.rejectValue("paymentStatus", "invalid.paymentStatus", msg);
            } else if (msg != null && (msg.contains("one bill") || msg.contains("already has a bill") || msg.contains("associated with an existing order") || msg.contains("Order does not exist") || msg.contains("Bill must be associated"))) {
                bindingResult.rejectValue("order", "invalid.order", msg);
            } else if (msg != null && msg.toLowerCase().contains("total price")) {
                bindingResult.rejectValue("totalPrice", "invalid.totalPrice", msg);
            } else {
                bindingResult.reject("globalError", msg != null ? msg : "Validation failed.");
            }

            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
            return "bills/form";
        }
    }

    // EDIT form
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("bill", billService.getById(id));
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
        return "bills/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Bill bill,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
            return "bills/form";
        }

        // Ensure order id is present on update as well
        if (bill.getOrder() == null || bill.getOrder().getId() == null) {
            bindingResult.rejectValue("order", "invalid.order", "Please select an order.");
            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
            return "bills/form";
        }

        try {
            billService.update(id, bill);
            redirectAttributes.addFlashAttribute("success", "Bill updated successfully.");
            return "redirect:/bills";
        } catch (DataIntegrityViolationException | IllegalStateException | IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("PAID")) {
                bindingResult.rejectValue("paymentStatus", "invalid.paymentStatus", msg);
            } else if (msg != null && (msg.contains("one bill") || msg.contains("already has a bill") || msg.contains("associated with an existing order") || msg.contains("Order does not exist") || msg.contains("Bill must be associated"))) {
                bindingResult.rejectValue("order", "invalid.order", msg);
            } else if (msg != null && msg.toLowerCase().contains("total price")) {
                bindingResult.rejectValue("totalPrice", "invalid.totalPrice", msg);
            } else {
                bindingResult.reject("globalError", msg != null ? msg : "Validation failed.");
            }

            model.addAttribute("orders", orderService.getAll());
            model.addAttribute("paymentStatuses", Arrays.asList(PaymentStatus.values()));
            return "bills/form";
        }
    }

    // DELETE (ONLY allowed if order is deleted)
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            billService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Bill deleted.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            // generic failure - redirect silently
            redirectAttributes.addFlashAttribute("error", "Failed to delete bill.");
        }
        return "redirect:/bills";
    }
}
