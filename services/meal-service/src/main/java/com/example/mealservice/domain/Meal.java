package com.example.mealservice.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record Meal(
        String mealId,
        String name,
        Category category,
        String description,
        BigDecimal price,
        List<MealIngredient> ingredients,
        String restaurantId
) {


}
