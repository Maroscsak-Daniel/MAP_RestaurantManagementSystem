package com.example.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each bill belongs to one order
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @NotNull
    private Order order;

    @PositiveOrZero
    private double totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PAID / UNPAID

    public Bill() {}

    public Bill(Order order, double totalPrice, PaymentStatus paymentStatus) {
        this.order = order;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() { return id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
}
