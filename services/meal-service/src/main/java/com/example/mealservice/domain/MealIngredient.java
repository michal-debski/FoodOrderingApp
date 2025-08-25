package com.example.mealservice.domain;

import com.example.mealservice.infrastructure.entity.Unit;

public record MealIngredient(
        String name,
        int quantity,
        Unit unit
) {
}
