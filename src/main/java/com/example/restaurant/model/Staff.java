package com.example.restaurant.model;

public abstract class Staff {

    private String id;
    private String name;

    // Constructor
    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Default constructor (optional, for frameworks)
    public Staff() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Optional: a method common to all staff members
    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name);
    }
}
