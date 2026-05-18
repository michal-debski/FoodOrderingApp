package com.example.shoppingcartservice.api.response;

public record MealItemRequest(
        String mealId,
        String restaurantId
) {
}
