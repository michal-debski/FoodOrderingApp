package com.example.shoppingcartservice.infrastructure.repository.mapper;

import com.example.shoppingcartservice.domain.CartItem;
import com.example.shoppingcartservice.domain.ShoppingCart;
import com.example.shoppingcartservice.infrastructure.entity.ShoppingCartEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShoppingCartEntityMapper {
    public ShoppingCart mapToDomain(ShoppingCartEntity entity) {
        List<CartItem> cartItems = (entity.getItems() == null || entity.getItems().isEmpty())
                ? List.of()
                : entity.getItems().stream()
                .map(item -> CartItem.builder()
                        .mealId(item.getMealId())
                        .unitPrice(item.getUnitPrice())
                        .quantity(item.getQuantity())
                        .build()
                )
                .toList();
        return new ShoppingCart(
                entity.getUserEmail(),
                entity.getRestaurantId(),
                cartItems,
                entity.getTotalPrice());
    }

    public ShoppingCartEntity mapToEntity(ShoppingCart shoppingCart) {
        List<ShoppingCartEntity.CartItemEntity> itemEntities = (shoppingCart.cartItemDTOList() == null)
                ? List.of()
                : shoppingCart.cartItemDTOList().stream()
                .map(cartItem -> new ShoppingCartEntity.CartItemEntity(
                        cartItem.getMealId(),
                        cartItem.getQuantity(),
                        cartItem.getUnitPrice())
                )
                .toList();
        return new ShoppingCartEntity(
                shoppingCart.userEmail(),
                shoppingCart.restaurantId(),
                itemEntities,
                shoppingCart.totalPrice()
        );
    }
}
