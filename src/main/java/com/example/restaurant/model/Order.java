package com.example.restaurant.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private String Id;
    private String customerId;
    private String tableId;
    private String status;
    private ArrayList<OrderLine> orderLines;
    private ArrayList<OrderAssignment> assignments;

    public Order(String id, String customerId, String tableId, String status, ArrayList<OrderLine> orderLines, ArrayList<OrderAssignment> assignments) {
        Id = id;
        this.customerId = customerId;
        this.tableId = tableId;
        this.status = status;
        this.orderLines = orderLines;
        this.assignments = assignments;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ArrayList<OrderAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<OrderAssignment> assignments) {
        this.assignments = assignments;
    }

//    @Override
//    public String toString() {
//        return "Order{" +
//                "Id='" + Id + '\'' +
//                ", customerId='" + customerId + '\'' +
//                ", tableId='" + tableId + '\'' +
//                ", status='" + status + '\'' +
//                ", orderLines=" + orderLines +
//                ", assignments=" + assignments +
//                '}';
//    }

    @Override
    public String toString() {
        return "Order {\n" +
                "  Id='" + Id + "',\n" +
                "  customerId='" + customerId + "',\n" +
                "  tableId='" + tableId + "',\n" +
                "  status='" + status + "',\n" +
                "  orderLines=" + formatList(orderLines) + ",\n" +
                "  assignments=" + formatList(assignments) + "\n" +
                "}";
    }

    private String formatList(List<?> list) {
        return list.stream()
                .map(item -> "    " + item.toString())
                .collect(Collectors.joining(",\n", "[\n", "\n  ]"));
    }
}
