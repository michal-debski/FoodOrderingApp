package com.example.mealservice.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record MealUpdateRequest(
        String name,
        String description,
        BigDecimal price,
        List<IngredientForMealDTO> ingredientsForMeal
) {


}
