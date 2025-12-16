package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.repository.BillRepository;
import com.example.restaurant.repository.OrderAssignmentRepository;
import com.example.restaurant.repository.OrderLineRepository;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderAssignmentRepository assignmentRepository;
    private final BillRepository billRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderLineRepository orderLineRepository,
                        OrderAssignmentRepository assignmentRepository,
                        BillRepository billRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.assignmentRepository = assignmentRepository;
        this.billRepository = billRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    // New: server-side filtering + paging + sorting similar to BillService
    public Page<Order> getAll(String status, String customerName, String sortBy, String dir, Pageable pageable) {
        Specification<Order> spec = buildSpecification(status, customerName);

        if (sortBy != null && !sortBy.isEmpty()) {
            Sort s = "desc".equalsIgnoreCase(dir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            int page = pageable == null ? 0 : pageable.getPageNumber();
            int size = pageable == null ? 10 : pageable.getPageSize();
            pageable = PageRequest.of(page, size, s);
        }

        return orderRepository.findAll(spec, pageable);
    }

    private Specification<Order> buildSpecification(String status, String customerName) {
        Specification<Order> spec = Specification.where(null);

        if (status != null && !status.isEmpty()) {
            try {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), com.example.restaurant.model.OrderStatus.valueOf(status.toUpperCase())));
            } catch (IllegalArgumentException e) {
                // ignore
            }
        }

        if (customerName != null && !customerName.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.join("customer").get("name")), "%" + customerName.toLowerCase() + "%"));
        }

        return spec;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order data) {
        Order existing = getById(id);

        // Business rule: if order is already COMPLETED, allow only changing status to CANCELLED.
        if (existing.getStatus() == OrderStatus.COMPLETED) {
            if (data.getStatus() == null || data.getStatus() != OrderStatus.CANCELLED) {
                throw new IllegalStateException("Completed orders can only be changed to CANCELLED.");
            }
            existing.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(existing);
        }

        existing.setStatus(data.getStatus());
        existing.setPaymentMethod(data.getPaymentMethod());
        existing.setCustomer(data.getCustomer());
        existing.setTable(data.getTable());

        return orderRepository.save(existing);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void delete(Long id) {

        // Delete related order lines
        var lines = orderLineRepository.findByOrder_Id(id);
        if (lines != null && !lines.isEmpty()) {
            orderLineRepository.deleteAll(lines);
        }

        // Delete related assignments
        var assignsList = assignmentRepository.findAll().stream().filter(a -> a.getOrder() != null && a.getOrder().getId().equals(id)).collect(Collectors.toList());
        if (!assignsList.isEmpty()) {
            assignmentRepository.deleteAll(assignsList);
        }

        // Delete associated bill if any (regardless of status)
        var bill = billRepository.findByOrder_Id(id);
        if (bill != null) {
            billRepository.delete(bill);
        }

        // Finally delete the order itself
        orderRepository.deleteById(id);
    }
}
