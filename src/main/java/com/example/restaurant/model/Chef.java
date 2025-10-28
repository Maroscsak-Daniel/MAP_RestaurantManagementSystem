package com.example.restaurant.model;

public class Chef extends Staff {

    private String specialization;

    public Chef(String id, String name, String experience, String specialization) {
        super(id, name, experience); // calls Staff constructor
        this.specialization = specialization;
    }

    public Chef() {
    }

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
                ", experience='" + getExperience() + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
