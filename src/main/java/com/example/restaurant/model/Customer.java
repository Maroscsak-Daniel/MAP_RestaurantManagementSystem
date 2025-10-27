package com.example.restaurant.model;

import java.util.ArrayList;

public class Customer {
    private String Id;
    private String name;
    private ArrayList<Order> orders;

    public Customer(String id, String name, ArrayList<Order> orders) {
        Id = id;
        this.name = name;
        this.orders = orders;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Id='" + Id + '\'' +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
