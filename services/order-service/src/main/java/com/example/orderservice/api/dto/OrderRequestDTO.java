package com.example.orderservice.api.dto;

import lombok.Builder;

import java.util.List;


@Builder
public record OrderRequestDTO(
        List<OrderItemDTO> orderItems
) {
}
