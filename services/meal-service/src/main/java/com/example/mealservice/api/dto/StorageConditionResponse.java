package com.example.mealservice.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record StorageConditionResponse(
        List<IngredientDTO> ingredients
) {
}
