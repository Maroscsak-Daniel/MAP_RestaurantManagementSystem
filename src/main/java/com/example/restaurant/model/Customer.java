package com.example.restaurant.model;

import java.util.ArrayList;

public class Customer {

    private String id;
    private String name;
    private ArrayList<String> orderIds;   // IDs only

    public Customer() {
    }

    public Customer(String id, String name, ArrayList<String> orderIds) {
        this.id = id;
        this.name = name;
        this.orderIds = orderIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(ArrayList<String> orderIds) {
        this.orderIds = orderIds;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", orderIds=" + orderIds +
                '}';
    }
}
