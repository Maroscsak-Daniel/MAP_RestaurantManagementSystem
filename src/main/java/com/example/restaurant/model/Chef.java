package com.example.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "chefs")
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "chef_rank")
    private String rank; // "Junior", "Senior", "Head Chef", etc.

    public Chef() {}

    public Chef(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }

    // GETTERS / SETTERS

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
}
