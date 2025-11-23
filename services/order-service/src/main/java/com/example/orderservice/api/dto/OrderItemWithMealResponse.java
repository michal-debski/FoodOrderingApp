package com.example.orderservice.api.dto;

import java.math.BigDecimal;

public record OrderItemWithMealResponse(
        String mealId,
        Integer quantity,
        String mealName,
        BigDecimal unitPrice
) {
}
