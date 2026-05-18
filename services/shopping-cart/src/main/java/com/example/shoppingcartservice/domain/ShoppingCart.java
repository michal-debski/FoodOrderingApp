package com.example.shoppingcartservice.domain;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCart (
        String userEmail,
        String restaurantId,
        List<CartItem> cartItemDTOList,
        BigDecimal totalPrice
){
}
