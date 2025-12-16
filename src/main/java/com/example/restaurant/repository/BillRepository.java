package com.example.restaurant.repository;

import com.example.restaurant.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {

    boolean existsByOrder_Id(Long orderId);

    Bill findByOrder_Id(Long orderId);
}
