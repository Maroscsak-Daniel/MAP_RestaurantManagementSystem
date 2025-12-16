package com.example.restaurant.service;

import com.example.restaurant.model.OrderAssignment;
import com.example.restaurant.repository.OrderAssignmentRepository;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.restaurant.model.PaymentStatus;

import java.util.List;

@Service
public class OrderAssignmentService {
    private final OrderAssignmentRepository repo;
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;

    public OrderAssignmentService(
            OrderAssignmentRepository repo,
            OrderRepository orderRepository
            , BillRepository billRepository
    ) {
        this.repo = repo;
        this.orderRepository = orderRepository;
        this.billRepository = billRepository;
    }

    public List<OrderAssignment> getAll() {
        return repo.findAll();
    }

    // Paged & filtered retrieval for index with optional orderId and staff filter
    public org.springframework.data.domain.Page<com.example.restaurant.model.OrderAssignment> getAllPaged(Long orderId, String staffFilter, String sortBy, String dir, org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        org.springframework.data.domain.Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (orderId != null && staffFilter != null && !staffFilter.isEmpty()) {
            return repo.findByOrder_IdAndStaffIdContainingIgnoreCase(orderId, staffFilter, p);
        } else if (orderId != null) {
            return repo.findByOrder_Id(orderId, p);
        } else if (staffFilter != null && !staffFilter.isEmpty()) {
            return repo.findByStaffIdContainingIgnoreCase(staffFilter, p);
        } else {
            return repo.findAll(p);
        }
    }

    // Helper to provide orders for the assignment form (lightweight)
    public List<com.example.restaurant.model.Order> getAllOrdersForSelection() {
        return orderRepository.findAll();
    }

    public OrderAssignment getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + id));
    }

    public List<OrderAssignment> getByOrder(Long orderId) {
        return repo.findByOrder_Id(orderId);
    }

    @Transactional
    public OrderAssignment create(OrderAssignment a) {
        // prevent creating assignments if the order already has a bill
        if (a.getOrder() == null || a.getOrder().getId() == null) {
            throw new IllegalArgumentException("Order must be set for assignment.");
        }

        Long orderId = a.getOrder().getId();
        var bill = billRepository.findByOrder_Id(orderId);
        if (bill != null && bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot modify assignments because the order has a PAID bill.");
        }

        return repo.save(a);
    }

    public void update(Long id, OrderAssignment data) {

        OrderAssignment existing = getById(id);

        // Only update staffId (chef/server). Do NOT change the associated order on edit.
        // Validate existing order and business rules using existing order id.
        if (existing.getOrder() == null || existing.getOrder().getId() == null) {
            throw new IllegalStateException("Existing assignment has no associated order.");
        }

        Long existingOrderId = existing.getOrder().getId();
        var bill = billRepository.findByOrder_Id(existingOrderId);
        if (bill != null && bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot modify assignment because the order has a PAID bill.");
        }

        // Update staffId only
        existing.setStaffId(data.getStaffId());
        repo.save(existing);
    }

    public void delete(Long id) {
        deleteInternal(id);
    }

    @Transactional
    public void deleteInternal(Long id) {
        OrderAssignment existing = getById(id);
        // Allow deletion regardless of order status (restore previous behavior)
        repo.deleteById(id);
    }

}
