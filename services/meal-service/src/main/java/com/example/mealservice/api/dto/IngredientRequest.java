package com.example.mealservice.api.dto;

import lombok.Builder;

@Builder
public record IngredientRequest(
        String name,
        String unitName
) {
}
