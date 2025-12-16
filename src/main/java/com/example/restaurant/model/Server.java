package com.example.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servers")
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String shift; // Morning / Evening / Night

    private int experienceYears;

    public Server() {}

    public Server(String name, String shift, int experienceYears) {
        this.name = name;
        this.shift = shift;
        this.experienceYears = experienceYears;
    }

    // GETTERS / SETTERS

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
}
