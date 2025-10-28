package com.example.restaurant.repository;

import com.example.restaurant.model.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepository extends IRepository<Staff>{

    @Override
    protected String getId(Staff staff) {
        return staff.getId();
    }

}
