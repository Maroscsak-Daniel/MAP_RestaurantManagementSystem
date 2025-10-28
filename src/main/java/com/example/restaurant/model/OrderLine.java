package com.example.restaurant.model;

public class OrderLine {

    private String id;
    private String menuItemId;
    private double quantity;
    private String allergens;

    // Constructors
    public OrderLine(String id, String menuItemId, double quantity,  String allergens) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.allergens = allergens;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public OrderLine() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // Optional helper
    @Override
    public String toString() {
        return "OrderLine{" +
                "id='" + id + '\'' +
                ", menuItemId='" + menuItemId + '\'' +
                ", allergens='" + allergens + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
