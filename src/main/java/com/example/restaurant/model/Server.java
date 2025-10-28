package com.example.restaurant.model;

public class Server extends Staff {

    private String designation;

    // Constructor
    public Server(String id, String name, String experience,  String designation) {
        super(id, name, experience);
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
    public String toString() {
        return "Server{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", experience='" + getExperience() + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }

}
