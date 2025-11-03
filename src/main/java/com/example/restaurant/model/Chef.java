package com.example.restaurant.model;

public class Chef extends Staff {

    private String specialization;
    private String experience;

    public Chef(String id, String name, String specialization, String experience) {
        super(id, name); // calls Staff constructor
        this.specialization = specialization;
        this.experience = experience;
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
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
