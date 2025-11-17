package com.example.restaurant.model;

import java.util.ArrayList;

public class Table {
    private String id;
    private int number;
    private String occupiedStatus;
    private ArrayList<String> orderIds;   // store ONLY order IDs

    public Table() {
        // required for JSON deserialization
    }

    public Table(String id, int number, String occupiedStatus, ArrayList<String> orderIds) {
        this.id = id;
        this.number = number;
        this.occupiedStatus = occupiedStatus;
        this.orderIds = orderIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOccupiedStatus() {
        return occupiedStatus;
    }

    public void setOccupiedStatus(String occupiedStatus) {
        this.occupiedStatus = occupiedStatus;
    }

    public ArrayList<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(ArrayList<String> orderIds) {
        this.orderIds = orderIds;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", occupiedStatus='" + occupiedStatus + '\'' +
                ", orderIds=" + orderIds +
                '}';
    }
}
