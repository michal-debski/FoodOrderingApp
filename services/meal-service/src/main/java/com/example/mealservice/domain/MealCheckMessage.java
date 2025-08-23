package com.example.mealservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MealCheckMessage {
    private String orderNumber;
    private List<OrderItem> orderItems;

    public MealCheckMessage() {
    }

    public MealCheckMessage(String orderNumber, List<OrderItem> orderItems) {
        this.orderNumber = orderNumber;
        this.orderItems = orderItems;
    }

}
