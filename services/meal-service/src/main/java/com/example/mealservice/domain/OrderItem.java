package com.example.mealservice.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class OrderItem {
    Integer quantity;
    String mealId;
    BigDecimal unitPrice;

    public OrderItem(Integer quantity, String mealId, BigDecimal unitPrice) {
        this.quantity = quantity;
        this.mealId = mealId;
        this.unitPrice = unitPrice;
    }

    public OrderItem() {
    }
}
