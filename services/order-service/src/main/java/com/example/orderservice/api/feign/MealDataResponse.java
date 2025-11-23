package com.example.orderservice.api.feign;

import java.math.BigDecimal;

public record MealDataResponse(
        String mealId,
        String name,
        BigDecimal price
) {}
