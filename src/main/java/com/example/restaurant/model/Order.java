package com.example.restaurant.model;

import java.util.ArrayList;

public class Order {

    private String id;
    private String customerId;
    private String tableId;
    private String status;

    private ArrayList<String> orderLineIds;      // ✔ only IDs
    private ArrayList<String> assignmentIds;     // ✔ only IDs

    private String paymentMethod;

    public Order() {
    }

    public Order(String id, String customerId, String tableId, String status,
                 ArrayList<String> orderLineIds,
                 ArrayList<String> assignmentIds,
                 String paymentMethod) {
        this.id = id;
        this.customerId = customerId;
        this.tableId = tableId;
        this.status = status;
        this.orderLineIds = orderLineIds;
        this.assignmentIds = assignmentIds;
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getOrderLineIds() {
        return orderLineIds;
    }

    public void setOrderLineIds(ArrayList<String> orderLineIds) {
        this.orderLineIds = orderLineIds;
    }

    public ArrayList<String> getAssignmentIds() {
        return assignmentIds;
    }

    public void setAssignmentIds(ArrayList<String> assignmentIds) {
        this.assignmentIds = assignmentIds;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", status='" + status + '\'' +
                ", orderLineIds=" + orderLineIds +
                ", assignmentIds=" + assignmentIds +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
