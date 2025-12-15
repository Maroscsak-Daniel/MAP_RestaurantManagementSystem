package com.example.restaurant.service;

import com.example.restaurant.model.Bill;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderStatus;
import com.example.restaurant.model.PaymentStatus;
import com.example.restaurant.repository.BillRepository;
import com.example.restaurant.repository.OrderAssignmentRepository;
import com.example.restaurant.repository.OrderLineRepository;
import com.example.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillServiceTest {

    @Mock
    private BillRepository billRepository;
    @Mock
    private OrderLineRepository orderLineRepository;
    @Mock
    private OrderAssignmentRepository assignmentRepository;
    @Mock
    private OrderRepository orderRepository;

    private BillService billService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        billService = new BillService(billRepository, orderLineRepository, assignmentRepository, orderRepository);
    }

    @Test
    void create_shouldThrowWhenDuplicateBillExists() {
        Order order = new Order();
        order.setId(1L);

        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setPaymentStatus(PaymentStatus.UNPAID);
        bill.setTotalPrice(10.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(billRepository.existsByOrder_Id(1L)).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> billService.create(bill));
        assertTrue(ex.getMessage().contains("one bill"));
    }

    @Test
    void create_markPaid_requiresOrderCompleted() {
        Order order = new Order();
        order.setId(2L);
        order.setStatus(OrderStatus.PENDING); // not completed

        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setTotalPrice(20.0);

        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        when(billRepository.existsByOrder_Id(2L)).thenReturn(false);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> billService.create(bill));
        assertTrue(ex.getMessage().contains("Cannot mark bill as PAID"));
    }

    @Test
    void delete_shouldThrowWhenBillIsPaid() {
        Order order = new Order();
        order.setId(3L);
        order.setStatus(OrderStatus.COMPLETED);

        Bill bill = new Bill();
        // no setId() call - model does not provide setter for id
        bill.setOrder(order);
        bill.setPaymentStatus(PaymentStatus.PAID);

        when(billRepository.findById(5L)).thenReturn(Optional.of(bill));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> billService.delete(5L));
        assertTrue(ex.getMessage().contains("Cannot delete a paid bill"));
    }
}
