package com.example.restaurant.repository;

import com.example.restaurant.model.Staff;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffRepository implements AbstractRepository<Staff>{

    private final List<Staff> staffList = new ArrayList<>();

    @Override
    public Staff save(Staff staff) {
        delete(staff.getId());
        staffList.add(staff);
        return staff;
    }

    @Override
    public List<Staff> findAll() {
        return new ArrayList<>(staffList);
    }

    @Override
    public Staff findById(String id) {
        for (Staff s : staffList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Staff delete(String id) {
        staffList.removeIf(s -> s.getId().equals(id));
        return findById(id);
    }

    public void clear() {
        staffList.clear();
    }
}
