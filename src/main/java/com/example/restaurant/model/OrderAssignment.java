package com.example.restaurant.model;

public class OrderAssignment {
    private String id;
    private String orderId;
    private String staffId;

    public OrderAssignment() {
    }

    public OrderAssignment(String id, String orderId, String staffId) {
        this.id = id;
        this.orderId = orderId;
        this.staffId = staffId;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "OrderAssignment{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", staffId='" + staffId + '\'' +
                '}';
    }
}
