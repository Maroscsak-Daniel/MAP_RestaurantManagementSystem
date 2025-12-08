package com.example.restaurant.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;
    private String occupiedStatus;

    @OneToMany(mappedBy = "restaurantTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public RestaurantTable() {}

    public RestaurantTable(int number, String occupiedStatus) {
        this.number = number;
        this.occupiedStatus = occupiedStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getOccupiedStatus() { return occupiedStatus; }
    public void setOccupiedStatus(String occupiedStatus) { this.occupiedStatus = occupiedStatus; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}

