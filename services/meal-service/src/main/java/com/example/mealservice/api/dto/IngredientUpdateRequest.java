package com.example.mealservice.api.dto;

import lombok.Builder;

@Builder
public record IngredientUpdateRequest(
        String name,
        Integer quantity,
        String unitName
) {
}
