package com.example.mealservice.domain;

import lombok.*;

import java.util.Arrays;

@Getter
public enum Category {

    APPETIZER("Appetizer"),
    MAIN_COURSE("Main Course"),
    DESSERT("Dessert"),
    DRINK("Drink"),;

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static Category fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(c -> c.name.equalsIgnoreCase(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + displayName));
    }
}
