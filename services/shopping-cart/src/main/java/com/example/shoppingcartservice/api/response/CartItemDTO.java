package com.example.shoppingcartservice.api.response;

import java.math.BigDecimal;

public record CartItemDTO(
        String mealId,
        String mealName,
        BigDecimal unitPrice,
        int quantity
) {
}
