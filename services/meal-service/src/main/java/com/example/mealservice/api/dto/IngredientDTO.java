package com.example.mealservice.api.dto;

import lombok.Builder;

@Builder
public record IngredientDTO (
        String name,
        int quantity,
        String unitName
) {
}
