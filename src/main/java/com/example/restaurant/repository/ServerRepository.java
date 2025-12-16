package com.example.restaurant.repository;

import com.example.restaurant.model.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    Page<Server> findByShiftContainingIgnoreCase(String shift, Pageable pageable);
    Page<Server> findByExperienceYearsGreaterThanEqual(int minExp, Pageable pageable);
    Page<Server> findByShiftContainingIgnoreCaseAndExperienceYearsGreaterThanEqual(String shift, int minExp, Pageable pageable);
}
