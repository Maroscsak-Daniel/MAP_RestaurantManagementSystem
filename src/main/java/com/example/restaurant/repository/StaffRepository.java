package com.example.restaurant.repository;

import com.example.restaurant.model.Staff;

@org.springframework.stereotype.Repository
public class StaffRepository extends Repository<Staff> {

    @Override
    protected String getId(Staff staff) {
        return staff.getId();
    }

}
