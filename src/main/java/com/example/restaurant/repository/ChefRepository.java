package com.example.restaurant.repository;

import com.example.restaurant.model.Chef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {

    Page<Chef> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Chef> findByRankContainingIgnoreCase(String rank, Pageable pageable);
    Page<Chef> findByNameContainingIgnoreCaseAndRankContainingIgnoreCase(String name, String rank, Pageable pageable);
}
