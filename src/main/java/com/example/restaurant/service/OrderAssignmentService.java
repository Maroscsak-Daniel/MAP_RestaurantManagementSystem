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

    public OrderAssignment create(OrderAssignment a) {
        return createInternal(a);
    }

    @Transactional
    protected OrderAssignment createInternal(OrderAssignment a) {
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

        // Validate order exists
        Long newOrderId = data.getOrder().getId();
        if (!orderRepository.existsById(newOrderId)) {
            throw new IllegalArgumentException("Order ID " + newOrderId + " does not exist.");
        }

        var bill = billRepository.findByOrder_Id(newOrderId);
        if (bill != null && bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot modify assignment because the order has a PAID bill.");
        }

        // Do the update
        existing.setOrder(orderRepository.findById(newOrderId).orElseThrow());
        existing.setStaffId(data.getStaffId());

        repo.save(existing);
    }

    public void delete(Long id) {
        deleteInternal(id);
    }

    @Transactional
    protected void deleteInternal(Long id) {
        OrderAssignment existing = getById(id);
        if (existing.getOrder() != null) {
            Long orderId = existing.getOrder().getId();
            // Block deletion if the related order is COMPLETED
            com.example.restaurant.model.Order ord = orderRepository.findById(orderId).orElse(null);
            if (ord != null && ord.getStatus() == com.example.restaurant.model.OrderStatus.COMPLETED) {
                throw new IllegalStateException("Cannot delete assignment because the order is COMPLETED.");
            }
        }

        repo.deleteById(id);
    }

}
