package com.example.mealservice.domain;

import java.util.List;

public record IngredientRemovalFromStorageMessage(
        String orderNumber,
        List<OrderItem> orderItems
) {
}
