package com.example.restaurant.service;

import com.example.restaurant.model.Order;
import com.example.restaurant.repository.BillRepository;
import com.example.restaurant.repository.OrderAssignmentRepository;
import com.example.restaurant.repository.OrderLineRepository;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order data) {
        Order existing = getById(id);

        existing.setStatus(data.getStatus());
        existing.setPaymentMethod(data.getPaymentMethod());
        existing.setCustomer(data.getCustomer());
        existing.setTable(data.getTable());

        return orderRepository.save(existing);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id) {

        long lines = orderLineRepository.countByOrder_Id(id);
        long assigns = assignmentRepository.countByOrder_Id(id);
        boolean hasBill = billRepository.findByOrder_Id(id) != null;

        if (lines > 0 || assigns > 0 || hasBill) {
            throw new IllegalStateException(
                    "Order cannot be deleted because it still has: " +
                            (lines > 0 ? lines + " order lines; " : "") +
                            (assigns > 0 ? assigns + " assignments; " : "") +
                            (hasBill ? "a bill; " : "")
            );
        }

        orderRepository.deleteById(id);
    }
}
