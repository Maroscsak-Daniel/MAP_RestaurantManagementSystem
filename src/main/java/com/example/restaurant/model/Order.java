package com.example.restaurant.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable restaurantTable;

    private String status;

    // KEEP THEM AS JSON-backed lists, NOT JPA RELATIONS
    @Transient
    private List<OrderLine> orderLines = new ArrayList<>();

    @Transient
    private List<OrderAssignment> assignments = new ArrayList<>();

    private String paymentMethod;

    public Order() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public RestaurantTable getTable() { return restaurantTable; }
    public void setTable(RestaurantTable restaurantTable) { this.restaurantTable = restaurantTable; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<OrderLine> getOrderLines() { return orderLines; }
    public void setOrderLines(List<OrderLine> orderLines) { this.orderLines = orderLines; }

    public List<OrderAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<OrderAssignment> assignments) { this.assignments = assignments; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

