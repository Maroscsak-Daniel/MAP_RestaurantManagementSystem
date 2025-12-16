package com.example.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_assignments")
public class OrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderAssignment() {}

    public OrderAssignment(String staffId) {
        this.staffId = staffId;
    }

    // Getters & setters

    public Long getId() { return id; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
