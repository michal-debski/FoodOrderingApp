package com.example.mealservice.api.dto;

import lombok.Builder;

import java.math.BigDecimal;


@Builder

public record MealDTO(
        String name,
        String category,
        String description,
        BigDecimal price,
        String restaurantId
) {


}
