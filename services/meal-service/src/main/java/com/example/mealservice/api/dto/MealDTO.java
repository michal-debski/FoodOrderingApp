package com.example.mealservice.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;


@Builder
public record MealDTO(
        String mealId,
        String name,
        String category,
        String description,
        BigDecimal price,
        List<IngredientForMealDTO> ingredientsForMeal,
        String restaurantId
) {


}
