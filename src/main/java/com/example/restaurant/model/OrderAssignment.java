package com.example.restaurant.model;

public class OrderAssignment {
    private String Id;
    private String OrderId;
    private String staffId;

    public OrderAssignment(String id, String orderId, String staffId) {
        Id = id;
        OrderId = orderId;
        this.staffId = staffId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
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
                "Id='" + Id + '\'' +
                ", OrderId='" + OrderId + '\'' +
                ", staffId='" + staffId + '\'' +
                '}';
    }
}
