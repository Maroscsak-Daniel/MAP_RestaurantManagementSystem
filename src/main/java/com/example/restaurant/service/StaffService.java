package com.example.restaurant.service;

import com.example.restaurant.model.Staff;
import com.example.restaurant.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void addStaff(Staff staff) {
        staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id) {
        return staffRepository.findById(id);
    }

    public void deleteStaff(String id) {
        staffRepository.delete(id);
    }

    public void clearAll() {
        staffRepository.clear();
    }
}
