package com.example.restaurant.model;

public class Bill {
    private String id;
    private String orderId;
    private double totalAmount;

    public Bill() {
        // required for JSON deserialization
    }

    public Bill(String id, String orderId, double totalAmount) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
