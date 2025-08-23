package com.example.orderservice.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class OrderItem {
    Integer quantity;
    String mealId;
    BigDecimal unitPrice;
    String orderNumber;

    public OrderItem(Integer quantity, String mealId, BigDecimal unitPrice, String orderNumber) {
        this.quantity = quantity;
        this.mealId = mealId;
        this.unitPrice = unitPrice;
        this.orderNumber = orderNumber;
    }

    public OrderItem() {
    }
}
