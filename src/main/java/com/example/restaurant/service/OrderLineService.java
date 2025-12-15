package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderLine;
import com.example.restaurant.model.MenuItem;
import com.example.restaurant.repository.OrderLineRepository;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.repository.BillRepository;
import org.springframework.stereotype.Service;

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

    public OrderLine create(OrderLine line) {

        if (line.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }

        Order order = orderRepo.findById(line.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order does not exist: " + line.getOrderId()));

        if (billRepo.existsByOrder_Id(order.getId())) {
            throw new IllegalStateException("Cannot modify order lines because the order already has a bill.");
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

    public OrderLine update(Long id, OrderLine data) {
        OrderLine existing = getById(id);

        Long orderId = data.getOrderId();
        if (orderId == null)
            throw new IllegalArgumentException("Order ID cannot be null.");

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order does not exist: " + orderId));

        if (billRepo.existsByOrder_Id(orderId))
            throw new IllegalStateException("Cannot modify this order line because the order already has a bill.");

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

    public void delete(Long id) {
        OrderLine line = getById(id);
        Long orderId = line.getOrder().getId();

        if (billRepo.existsByOrder_Id(orderId)) {
            throw new IllegalStateException(
                    "Cannot delete this order line because the order already has a bill."
            );
        }

        repo.delete(line);
    }
    public List<OrderLine> getByOrder(Long orderId) {
        return repo.findByOrder_Id(orderId);
    }
}
