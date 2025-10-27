package com.example.restaurant.model;

public class Bill {
    private String Id;
    private String orderId;
    double totalAmount;

    public Bill(String id, String orderId, double totalAmount) {
        Id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
