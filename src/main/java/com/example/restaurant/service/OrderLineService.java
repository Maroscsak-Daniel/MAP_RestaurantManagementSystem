package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderLine;
import com.example.restaurant.model.MenuItem;
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

        Long orderId = data.getOrderId();
        if (orderId == null)
            throw new IllegalArgumentException("Order ID cannot be null.");

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order does not exist: " + orderId));

        var bill = billRepo.findByOrder_Id(orderId);
        if (bill != null && bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot modify this order line because the order has a PAID bill.");
        }

        MenuItem menu = null;
        if (data.getMenuItemId() != null) {
            menu = menuItemRepo.findById(data.getMenuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("MenuItem does not exist: " + data.getMenuItemId()));
        }

        existing.setQuantity(data.getQuantity());
        existing.setAllergens(data.getAllergens());
        existing.setOrder(order);
        existing.setMenuItem(menu);

        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        deleteInternal(id);
    }

    protected void deleteInternal(Long id) {
        OrderLine line = getById(id);
        Long orderId = line.getOrder().getId();

        var bill = billRepo.findByOrder_Id(orderId);
        if (bill != null && bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException(
                    "Cannot delete this order line because the order has a PAID bill."
            );
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
