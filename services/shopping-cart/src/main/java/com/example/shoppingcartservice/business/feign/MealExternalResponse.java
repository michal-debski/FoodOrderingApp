package com.example.shoppingcartservice.business.feign;

import java.math.BigDecimal;

public record MealExternalResponse(
        String mealId,
        String name,
        BigDecimal price,
        String restaurantId
) {
}
