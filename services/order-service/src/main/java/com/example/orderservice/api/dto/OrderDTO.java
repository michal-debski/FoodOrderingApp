package com.example.orderservice.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderDTO(
        String orderNumber,
        BigDecimal totalPrice,
        String status,
        String customerEmail,
        String orderDate,
        List<OrderItemDTO> orderItems,
        boolean isCancellable
) {


}
