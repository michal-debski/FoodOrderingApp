package com.example.mealservice.api.dto;

import java.math.BigDecimal;

public record MealDataResponse(
        String mealId,
        String name,
        BigDecimal price
) {
}
