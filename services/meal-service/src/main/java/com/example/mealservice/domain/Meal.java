package com.example.mealservice.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
public record Meal(
        String mealId,
        String name,
        Category category,
        String description,
        BigDecimal price,
        String restaurantId
) {


}
