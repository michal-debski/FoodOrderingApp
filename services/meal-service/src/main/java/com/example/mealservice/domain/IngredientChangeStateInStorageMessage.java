package com.example.mealservice.domain;

import java.util.List;

public record IngredientChangeStateInStorageMessage(
        String orderNumber,
        List<OrderItem> orderItems
) {
}
