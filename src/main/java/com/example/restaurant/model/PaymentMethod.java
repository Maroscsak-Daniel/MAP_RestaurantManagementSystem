package com.example.restaurant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
    CASH("cash"),
    CARD("card");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        // return a user-friendly representation
        return value;
    }

    @JsonCreator
    public static PaymentMethod fromString(String v) {
        if (v == null) return null;
        for (PaymentMethod pm : PaymentMethod.values()) {
            if (pm.value.equalsIgnoreCase(v) || pm.name().equalsIgnoreCase(v)) return pm;
        }
        throw new IllegalArgumentException("Unknown payment method: " + v);
    }
}

