package com.example.shoppingcartservice.api.response;

import com.example.shoppingcartservice.domain.CartItem;
import com.example.shoppingcartservice.domain.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {

    public ShoppingCartDTO mapToDTO(ShoppingCart shoppingCart) {
        return new ShoppingCartDTO(
                shoppingCart.userEmail(),
                shoppingCart.restaurantId(),
                shoppingCart.cartItemDTOList().stream()
                        .map(cartItem -> new CartItemDTO(
                                cartItem.getMealId(),
                                cartItem.getMealName(),
                                cartItem.getUnitPrice(),
                                cartItem.getQuantity()
                        )).toList(),
                shoppingCart.totalPrice()
        );
    }

    public ShoppingCart map(ShoppingCartDTO shoppingCartDTO) {
        return new ShoppingCart(
                shoppingCartDTO.customerEmail(),
                shoppingCartDTO.restaurantId(),
                shoppingCartDTO.cartItemDTOList().stream()
                        .map(cartItemDTO -> CartItem.builder()
                                        .mealId(cartItemDTO.mealId())
                                        .unitPrice(cartItemDTO.unitPrice())
                                        .quantity(cartItemDTO.quantity())
                                        .build()
                        ).toList(),
                shoppingCartDTO.totalPrice()
        );
    }
}
