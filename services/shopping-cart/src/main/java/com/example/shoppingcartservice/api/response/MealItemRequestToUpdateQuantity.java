package com.example.shoppingcartservice.api.response;

public record MealItemRequestToUpdateQuantity(
        String mealId,
        Integer quantity
) {
}
