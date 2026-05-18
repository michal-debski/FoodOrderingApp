package com.example.shoppingcartservice.api.response;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartDTO(
        String customerEmail,
        String restaurantId,
        List<CartItemDTO> cartItemDTOList,
        BigDecimal totalPrice
) {
}
