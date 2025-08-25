package com.example.mealservice.api.dto;

import lombok.Builder;

@Builder
public record IngredientForMealDTO(
        String name,
        int quantity,
        String unit
) {

}
