package com.example.restaurant.repository;

import com.example.restaurant.model.OrderLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    List<OrderLine> findByOrder_Id(Long orderId);

    long countByOrder_Id(Long orderId);

    long countByMenuItem_Id(Long menuItemId);

    Page<OrderLine> findByOrder_Id(Long orderId, Pageable pageable);
    Page<OrderLine> findByMenuItem_NameContainingIgnoreCase(String name, Pageable pageable);
    Page<OrderLine> findByOrder_IdAndMenuItem_NameContainingIgnoreCase(Long orderId, String name, Pageable pageable);

}
