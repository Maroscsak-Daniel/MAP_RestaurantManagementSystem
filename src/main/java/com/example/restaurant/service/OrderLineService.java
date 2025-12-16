package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderLine;
import com.example.restaurant.model.MenuItem;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.model.PaymentStatus;
import com.example.restaurant.repository.OrderLineRepository;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.repository.BillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository repo;
    private final OrderRepository orderRepo;
    private final MenuItemRepository menuItemRepo;
    private final BillRepository billRepo;

    public OrderLineService(OrderLineRepository repo,
                            OrderRepository orderRepo,
                            MenuItemRepository menuItemRepo,
                            BillRepository billRepo) {
        this.repo = repo;
        this.orderRepo = orderRepo;
        this.menuItemRepo = menuItemRepo;
        this.billRepo = billRepo;
    }

    public List<OrderLine> getAll() {
        return repo.findAll();
    }

    public OrderLine getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OrderLine not found: " + id));
    }

    @Transactional
    public OrderLine create(OrderLine line) {
        if (line.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }

        Order order = orderRepo.findById(line.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order does not exist: " + line.getOrderId()));

        // Do not allow adding order lines to a COMPLETED order
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot add order line to a COMPLETED order.");
        }

        // Only prevent modification if there's a PAID bill for the order
        var bill = billRepo.findByOrder_Id(order.getId());
        if (bill != null && bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot modify order lines because the order has a PAID bill.");
        }

        MenuItem menu = null;
        if (line.getMenuItemId() != null) {
            menu = menuItemRepo.findById(line.getMenuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("MenuItem does not exist: " + line.getMenuItemId()));
        }

        line.setOrder(order);
        line.setMenuItem(menu);

        return repo.save(line);
    }

    @Transactional
    public OrderLine update(Long id, OrderLine data) {
        // perform update transactionally
        return updateInternal(id, data);
    }

    protected OrderLine updateInternal(Long id, OrderLine data) {
        OrderLine existing = getById(id);

        // Only allow editing quantity and allergens. Do not change order/menu item.
        Order order = existing.getOrder();
        if (order == null) throw new IllegalStateException("OrderLine has no associated Order.");

        // Allow updates ONLY if the order is COMPLETED or CANCELLED (per request)
        if (!(order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED)) {
            throw new IllegalStateException("Order lines may be edited only when the order is COMPLETED or CANCELLED.");
        }

        // Optionally keep PAID-bill restriction removed (policy: edits allowed when order finished).
        existing.setQuantity(data.getQuantity());
        existing.setAllergens(data.getAllergens());

        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        deleteInternal(id);
    }

    protected void deleteInternal(Long id) {
        OrderLine line = getById(id);
        Long orderId = line.getOrder().getId();

        Order order = line.getOrder();
        if (order == null) throw new IllegalStateException("OrderLine has no associated Order.");

        // Allow delete ONLY when order is COMPLETED or CANCELLED
        if (!(order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED)) {
            throw new IllegalStateException("Order lines may be deleted only when the order is COMPLETED or CANCELLED.");
        }

        repo.delete(line);
    }

    public List<OrderLine> getByOrder(Long orderId) {
        return repo.findByOrder_Id(orderId);
    }

    public Page<OrderLine> getAllPaged(Long orderId, String menuName, String sortBy, String dir, Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (orderId != null && menuName != null && !menuName.isEmpty()) {
            return repo.findByOrder_IdAndMenuItem_NameContainingIgnoreCase(orderId, menuName, p);
        } else if (orderId != null) {
            return repo.findByOrder_Id(orderId, p);
        } else if (menuName != null && !menuName.isEmpty()) {
            return repo.findByMenuItem_NameContainingIgnoreCase(menuName, p);
        } else {
            return repo.findAll(p);
        }
    }
}
