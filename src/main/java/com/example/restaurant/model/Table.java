package com.example.restaurant.model;

import java.util.ArrayList;

public class Table {
    private String Id;
    private int number;
    private String occupiedStatus;
    private ArrayList<Order> orders;

    public Table(String id, int number, String occupiedStatus, ArrayList<Order> orders) {
        Id = id;
        this.number = number;
        this.occupiedStatus = occupiedStatus;
        this.orders = orders;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
