package com.example.restaurant.repository;

import com.example.restaurant.model.RestaurantTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    Page<RestaurantTable> findByNumber(Integer number, Pageable pageable);
    Page<RestaurantTable> findByOccupiedStatusContainingIgnoreCase(String status, Pageable pageable);
    Page<RestaurantTable> findByNumberAndOccupiedStatusContainingIgnoreCase(Integer number, String status, Pageable pageable);
    boolean existsByNumber(Integer number);
}
