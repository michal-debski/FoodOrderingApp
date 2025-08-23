package com.example.orderservice.api.dto;

import lombok.Builder;

import java.math.BigDecimal;


@Builder

public record OrderItemDTO(
        Integer quantity,
        String mealId,
        BigDecimal unitPrice,
        String orderNumber
) {


}
