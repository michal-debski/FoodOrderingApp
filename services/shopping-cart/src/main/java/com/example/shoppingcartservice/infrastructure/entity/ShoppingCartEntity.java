package com.example.shoppingcartservice.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@RedisHash(value = "shopping-cart", timeToLive = 10800)
@Getter
@AllArgsConstructor
public class ShoppingCartEntity implements Serializable {

    @Id
    private String userEmail;
    private String restaurantId;
    private List<CartItemEntity> items;
    private BigDecimal totalPrice;


    @Getter
    @AllArgsConstructor
    public static class CartItemEntity implements Serializable {
        private String mealId;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}
