package com.example.restaurant.repository;

import com.example.restaurant.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE SIZE(c.orders) >= :minOrders")
    Page<Customer> findByMinOrders(@Param("minOrders") int minOrders, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%')) AND SIZE(c.orders) >= :minOrders")
    Page<Customer> findByNameAndMinOrders(@Param("name") String name, @Param("minOrders") int minOrders, Pageable pageable);
}
