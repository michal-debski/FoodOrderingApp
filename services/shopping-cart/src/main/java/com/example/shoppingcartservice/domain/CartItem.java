package com.example.shoppingcartservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
public class CartItem {
    String mealId;
    String mealName;
    BigDecimal unitPrice;
    int quantity;
}
