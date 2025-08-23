package com.example.mealservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderItem {
    Integer quantity;
    String mealId;
    BigDecimal unitPrice;
    String orderNumber;

}
