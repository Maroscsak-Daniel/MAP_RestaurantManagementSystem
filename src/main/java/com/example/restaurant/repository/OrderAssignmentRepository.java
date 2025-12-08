package com.example.restaurant.repository;

import com.example.restaurant.model.OrderAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {

    long countByOrder_Id(Long orderId);
}
