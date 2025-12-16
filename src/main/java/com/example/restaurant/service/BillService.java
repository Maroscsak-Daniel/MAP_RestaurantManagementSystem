package com.example.restaurant.service;

import com.example.restaurant.model.Bill;
import com.example.restaurant.model.PaymentStatus;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.repository.BillRepository;
import com.example.restaurant.repository.OrderAssignmentRepository;
import com.example.restaurant.repository.OrderLineRepository;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    private final BillRepository repo;
    private final OrderLineRepository orderLineRepository;
    private final OrderAssignmentRepository assignmentRepository;
    private final OrderRepository orderRepository;

    public BillService(BillRepository repo,
                       OrderLineRepository orderLineRepository,
                       OrderAssignmentRepository assignmentRepository,
                       OrderRepository orderRepository) {
        this.repo = repo;
        this.orderLineRepository = orderLineRepository;
        this.assignmentRepository = assignmentRepository;
        this.orderRepository = orderRepository;
    }

    public List<Bill> getAll() {
        return repo.findAll();
    }

    // New: server-side filter + paging
    public Page<Bill> getAll(String status, Double min, Double max, String sortBy, String dir, Pageable pageable) {
        Specification<Bill> spec = buildSpecification(status, min, max);
        // if sortBy provided, apply it (override pageable sort)
        if (sortBy != null && !sortBy.isEmpty()) {
            org.springframework.data.domain.Sort s = "desc".equalsIgnoreCase(dir) ? org.springframework.data.domain.Sort.by(sortBy).descending() : org.springframework.data.domain.Sort.by(sortBy).ascending();
            int page = pageable == null ? 0 : pageable.getPageNumber();
            int size = pageable == null ? 10 : pageable.getPageSize();
            pageable = PageRequest.of(page, size, s);
        }
        return repo.findAll(spec, pageable);
    }

    private Specification<Bill> buildSpecification(String status, Double min, Double max) {
        Specification<Bill> spec = Specification.where(null);

        if (status != null && !status.isEmpty()) {
            try {
                PaymentStatus ps = PaymentStatus.valueOf(status.toUpperCase());
                spec = spec.and((root, query, cb) -> cb.equal(root.get("paymentStatus"), ps));
            } catch (IllegalArgumentException e) {
                // ignore invalid
            }
        }

        if (min != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("totalPrice"), min));
        }

        if (max != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("totalPrice"), max));
        }

        return spec;
    }

    public Bill getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found: " + id));
    }

    @Transactional
    public Bill create(Bill bill) {
        // Validate order id provided
        if (bill.getOrder() == null || bill.getOrder().getId() == null) {
            throw new IllegalArgumentException("Bill must be associated with an existing order.");
        }

        Long orderId = bill.getOrder().getId();

        // Load order from DB to ensure we have a fresh entity
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order does not exist: " + orderId));

        // Prevent duplicate bill for the same order
        if (repo.existsByOrder_Id(orderId)) {
            throw new IllegalStateException("An order can have only one bill.");
        }

        if (bill.getTotalPrice() < 0) {
            throw new IllegalArgumentException("Total price must be >= 0.");
        }

        // default payment status to UNPAID when not provided
        if (bill.getPaymentStatus() == null) {
            bill.setPaymentStatus(PaymentStatus.UNPAID);
        }

        // If marking as PAID, ensure order is completed
        if (bill.getPaymentStatus() == PaymentStatus.PAID) {
            if (order.getStatus() != OrderStatus.COMPLETED) {
                throw new IllegalStateException("Cannot mark bill as PAID when order is not completed.");
            }
        }

        // attach managed order entity
        bill.setOrder(order);

        return repo.save(bill);
    }

    @Transactional
    public Bill update(Long id, Bill data) {
        Bill existing = getById(id);

        // If changing associated order, ensure new order exists and has no other bill
        if (data.getOrder() != null && data.getOrder().getId() != null) {
            Long newOrderId = data.getOrder().getId();
            Long currentOrderId = existing.getOrder() != null ? existing.getOrder().getId() : null;
            if (!newOrderId.equals(currentOrderId) && repo.existsByOrder_Id(newOrderId)) {
                throw new IllegalStateException("The target order already has a bill.");
            }

            var newOrder = orderRepository.findById(newOrderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order does not exist: " + newOrderId));
            existing.setOrder(newOrder);
        }

        if (data.getTotalPrice() < 0) {
            throw new IllegalArgumentException("Total price must be >= 0.");
        }
        existing.setTotalPrice(data.getTotalPrice());

        // Do not overwrite paymentStatus if not provided in form
        if (data.getPaymentStatus() != null) {
            // If setting to PAID, ensure order is completed
            if (data.getPaymentStatus() == PaymentStatus.PAID) {
                if (existing.getOrder() == null || existing.getOrder().getStatus() != OrderStatus.COMPLETED) {
                    throw new IllegalStateException("Cannot mark bill as PAID when order is not completed.");
                }
            }
            existing.setPaymentStatus(data.getPaymentStatus());
        }

        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Bill bill = getById(id);

        // Business checks before delete
        // 1) Do not delete a PAID bill
        if (bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot delete a paid bill.");
        }

        // 2) If the associated order still has lines or assignments, refuse deletion
        if (bill.getOrder() != null) {
            Long orderId = bill.getOrder().getId();
            // if order is not CANCELLED, block deletion when order has lines/assignments
            if (bill.getOrder().getStatus() != com.example.restaurant.model.OrderStatus.CANCELLED) {
                long lines = orderLineRepository.countByOrder_Id(orderId);
                long assigns = assignmentRepository.countByOrder_Id(orderId);
                if (lines > 0 || assigns > 0) {
                    throw new IllegalStateException("Cannot delete bill because its order still has related data.");
                }
            }
        }

        repo.deleteById(id);
    }
}
