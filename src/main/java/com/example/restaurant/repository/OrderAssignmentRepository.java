package com.example.restaurant.repository;

import com.example.restaurant.model.OrderAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {

    long countByOrder_Id(Long orderId);

    // Added: fetch assignments for a specific order
    List<OrderAssignment> findByOrder_Id(Long orderId);

    // Paging / filtering methods
    Page<OrderAssignment> findAll(Pageable pageable);
    Page<OrderAssignment> findByOrder_Id(Long orderId, Pageable pageable);
    Page<OrderAssignment> findByStaffIdContainingIgnoreCase(String staffId, Pageable pageable);
    Page<OrderAssignment> findByOrder_IdAndStaffIdContainingIgnoreCase(Long orderId, String staffId, Pageable pageable);
}
