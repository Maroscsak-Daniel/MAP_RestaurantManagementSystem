package com.example.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_lines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private String allergens;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Transient
    private Long orderId;

    @Transient
    private Long menuItemId;

    // --------------------------------------------------------
    // Constructors
    // --------------------------------------------------------

    // Required by JPA
    public OrderLine() {}

    // Constructor without ID (useful for create)
    public OrderLine(Integer quantity, String allergens, Long orderId, Long menuItemId) {
        this.quantity = quantity;
        this.allergens = allergens;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
    }

    public OrderLine(Order order, MenuItem menuItem, Integer quantity, String allergens) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.allergens = allergens;

        // Set transient IDs so the service understands them
        this.orderId = (order != null ? order.getId() : null);
        this.menuItemId = (menuItem != null ? menuItem.getId() : null);
    }

    // Full constructor including ID (rarely needed)
    public OrderLine(Long id, Integer quantity, String allergens,
                     Order order, MenuItem menuItem,
                     Long orderId, Long menuItemId) {
        this.id = id;
        this.quantity = quantity;
        this.allergens = allergens;
        this.order = order;
        this.menuItem = menuItem;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
    }

    // --------------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }
}
