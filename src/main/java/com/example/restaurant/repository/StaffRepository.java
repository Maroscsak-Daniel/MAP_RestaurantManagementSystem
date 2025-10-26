package com.example.restaurant.repository;

import com.example.restaurant.model.Staff;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffRepository {

    private final List<Staff> staffList = new ArrayList<>();

    public void save(Staff staff) {
        delete(staff.getId());
        staffList.add(staff);
    }

    public List<Staff> findAll() {
        return new ArrayList<>(staffList);
    }

    public Staff findById(String id) {
        for (Staff s : staffList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void delete(String id) {
        staffList.removeIf(s -> s.getId().equals(id));
    }

    public void clear() {
        staffList.clear();
    }
}
