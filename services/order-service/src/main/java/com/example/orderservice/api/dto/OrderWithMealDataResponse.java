package com.example.orderservice.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderWithMealDataResponse(
        String orderNumber,
        BigDecimal totalPrice,
        String status,
        String customerEmail,
        String orderDate,
        List<OrderItemWithMealResponse> orderItems,
        boolean isCancellable
) {
}
