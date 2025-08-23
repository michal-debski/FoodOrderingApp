package com.example.mealservice.api.dto;

import com.example.mealservice.domain.Category;

import java.math.BigDecimal;

public record MealUpdateRequest(
        Integer mealId,
        String name,
        Category category,
        String description,
        BigDecimal price
) {


}
