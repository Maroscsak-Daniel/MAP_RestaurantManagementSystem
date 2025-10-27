package com.example.restaurant.model;

public class Chef extends Staff {

    private String specialization;

    // Constructor
    public Chef(String id, String name, String specialization) {
        super(id, name); // calls Staff constructor
        this.specialization = specialization;
    }

    public Chef() {
    }

    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Chef{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
