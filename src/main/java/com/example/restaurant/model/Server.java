package com.example.restaurant.model;

public class Server extends Staff {

    private String designation;

    // Constructor
    public Server(String id, String name, String designation) {
        super(id, name);
        this.designation = designation;
    }

    public Server() {
    }

    // Getters and Setters
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public void displayInfo() {
        System.out.println("Server - ID: " + getId() + ", Name: " + getName() + ", Designation: " + designation);
    }
}
