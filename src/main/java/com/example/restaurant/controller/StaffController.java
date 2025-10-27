package com.example.restaurant.controller;

import com.example.restaurant.model.Staff;
import com.example.restaurant.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/all")
    public List<Staff> getAll() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public Staff getById(@PathVariable String id) {
        return staffService.getStaffById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Staff staff) {
        staffService.addStaff(staff);
        return "Staff member added successfully!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        staffService.deleteStaff(id);
        return "Staff member deleted successfully!";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        staffService.clearAll();
        return "All staff members cleared.";
    }
}
