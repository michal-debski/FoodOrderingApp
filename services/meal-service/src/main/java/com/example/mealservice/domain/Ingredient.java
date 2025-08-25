package com.example.mealservice.domain;

import lombok.Builder;

@Builder
public record Ingredient (
        String ingredientId,
        String name,
        int quantity,
        String unitName,
        String restaurantId
) {

}
